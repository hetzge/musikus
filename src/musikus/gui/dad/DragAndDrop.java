package musikus.gui.dad;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javolution.util.FastTable;
import musikus.base.Main;
import musikus.gui.Gui;
import musikus.gui.GuiService;

public class DragAndDrop {

	private static final String HOVER_CSS_CLASS_NEGATIV = "drag-negativ";
	private static final String HOVER_CSS_CLASS_POSITIV = "drag-positiv";

	private static FastTable<DragAndDropRule> rules; 
	private static FastTable<DragAndDropRule> rules(){
		if(rules == null){
			rules = new FastTable<>(Main.getBean(RuleEndToneAnchorToBeginToneAnchor.class), Main.getBean(RuleToneRectangleToRow.class), Main.getBean(RuleToneRectangleToBeatLine.class));
		}
		return rules;
	}
	
	private final Node node;

	public static DragAndDrop addTo(Node node){
		return new DragAndDrop(node);
	}

	private DragAndDrop(Node node) {
		this.node = node;
		
		node.setOnDragDetected(this::DRAG_DETECTED);
		node.setOnDragDone(this::DRAG_DONE);
		node.setOnDragDropped(this::DRAG_DROPPED);
		node.setOnDragEntered(this::DRAG_ENTERED);
		node.setOnDragExited(this::DRAG_EXITED);
		node.setOnDragOver(this::DRAG_OVER);
	}
	
	private void DRAG_DETECTED(MouseEvent event) {
		Dragboard dragboard = node.startDragAndDrop(TransferMode.COPY_OR_MOVE);

		ClipboardContent content = new ClipboardContent();
		content.putString("");
		dragboard.setContent(content);

		Gui gui = Main.getBean(Gui.class);
		gui.startXProperty.set(event.getX());
		gui.startYProperty.set(event.getY());
		
		GuiService guiService = Main.getBean(GuiService.class);
		guiService.enableDragAndDropLine();
		
		event.consume();
	}

	private void DRAG_DONE(DragEvent event) {
        GuiService guiService = Main.getBean(GuiService.class);
		guiService.disableDragAndDropLine();
        
        event.consume();
	}

	private void DRAG_DROPPED(DragEvent event) {
		boolean possibleDragAndDrop = isDragFromToAllowed(event);
		if(possibleDragAndDrop){
			List<DragAndDropRule> validRules = collectValidRules(event);
			for (DragAndDropRule dragAndDropRule : validRules) {
				dragAndDropRule.onDropped(event.getGestureSource(), event.getGestureTarget(), event);
				
				// TODO auslagern
				Main.getBean(GuiService.class).refreshMainStage();
			}
			event.setDropCompleted(true);
		} else {
			event.setDropCompleted(false);
		}
		
		GuiService guiService = Main.getBean(GuiService.class);
		guiService.disableDragAndDropLine();
		
		event.consume();
	}

	private void DRAG_ENTERED(DragEvent event) {
		if (event.getGestureSource() != node && event.getDragboard().hasString()) {
			node.getStyleClass().add(HOVER_CSS_CLASS_NEGATIV);
		}
		event.consume();
	}

	private void DRAG_EXITED(DragEvent event) {
		node.getStyleClass().clear();
		event.consume();
	}

	private void DRAG_OVER(DragEvent event) {
		boolean notDragToSelf = event.getGestureSource() != node;
		boolean validDragboard = event.getDragboard().hasString();
		boolean dragFromToAllowed = isDragFromToAllowed(event);
		if (notDragToSelf && validDragboard && dragFromToAllowed) {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			node.getStyleClass().clear();
			node.getStyleClass().add(HOVER_CSS_CLASS_POSITIV);
		}
		
		Gui gui = Main.getBean(Gui.class);
		gui.endXProperty.set(event.getX());
		gui.endYProperty.set(event.getY());
		
		event.consume();
	}

	private boolean isDragFromToAllowed(DragEvent event) {
		return !collectValidRules(event).isEmpty();
	}
	
	private List<DragAndDropRule> collectValidRules(final DragEvent event){
		Predicate<? super DragAndDropRule> filter = rule -> rule.canFromTo(extractEventSourceClassOrNull(event), node.getClass());
		List<DragAndDropRule> result = rules().stream().filter(filter).collect(Collectors.toList());
		return result;
	}
	
	private Class<?> extractEventSourceClassOrNull(DragEvent event){
		return event.getGestureSource() != null ? event.getGestureSource().getClass() : null;
	}

}
