package musikus.gui.dad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.input.DragEvent;
import musikus.data.DataNode;
import musikus.gui.GuiContext;
import musikus.gui.Gui.MainStageScrollPanel.MainStageUpdateGroup.MainStagePanel.Row;
import musikus.gui.Gui.MainStageScrollPanel.MainStageUpdateGroup.MainStagePanel.ToneGroup.Tone.ToneRectangel;

@Component
public class RuleToneRectangleToRow extends DragAndDropRule{

	@Autowired
	private GuiContext guiContext;
	
	public RuleToneRectangleToRow() {
		super(ToneRectangel.class, Row.class);
	}

	@Override
	void onDropped(Object from, Object to, DragEvent event) {
		ToneRectangel toneRectangel = (ToneRectangel) from;
		DataNode dataNode = toneRectangel.getDataNode();
		Row row = (Row) to;
		
		dataNode.setPosition(dataNode.position() + (event.getX()/guiContext.pixelPerBeat() - dataNode.calculateGlobalPosition()));
		dataNode.setTone(dataNode.tone() + (row.getTone() - dataNode.calculateGlobalTone()));
	}

}
