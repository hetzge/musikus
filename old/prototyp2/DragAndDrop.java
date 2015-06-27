package prototyp2;

import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DragAndDrop {

	private final Node node;

	public DragAndDrop(Node node) {
		this.node = node;

		node.setOnDragDetected(this::DRAG_DETECTED);
		node.setOnDragDone(this::DRAG_DONE);
		node.setOnDragDropped(this::DRAG_DROPPED);
		node.setOnDragEntered(this::DRAG_ENTERED);
		node.setOnDragExited(this::DRAG_EXITED);
		node.setOnDragOver(this::DRAG_OVER);
	}

	private void DRAG_DETECTED(MouseEvent event) {
		Dragboard dragboard = node.startDragAndDrop(TransferMode.ANY);

		ClipboardContent content = new ClipboardContent();
		content.putString("");
		dragboard.setContent(content);

		event.consume();
	}

	private void DRAG_DONE(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            // do something
        	System.out.println("drag done");
        }
        event.consume();
	}

	private void DRAG_DROPPED(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {
			success = true;
		}
		event.setDropCompleted(success);
		event.consume();
	}

	private void DRAG_ENTERED(DragEvent event) {
		if (event.getGestureSource() != node && event.getDragboard().hasString()) {
			// do something
			System.out.println("drag entered");
		}
		event.consume();
	}

	private void DRAG_EXITED(DragEvent event) {
		// do something
		System.out.println("drag exited");
		event.consume();
	}

	private void DRAG_OVER(DragEvent event) {
		if (event.getGestureSource() != node && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		}
		event.consume();
	}

}
