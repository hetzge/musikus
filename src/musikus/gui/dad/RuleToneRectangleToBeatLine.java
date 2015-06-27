package musikus.gui.dad;

import javafx.scene.input.DragEvent;
import musikus.data.DataNode;
import musikus.gui.GuiContext;
import musikus.gui.GuiService;
import musikus.gui.Gui.MainStageScrollPanel.MainStageUpdateGroup.MainStagePanel.BeatLine;
import musikus.gui.Gui.MainStageScrollPanel.MainStageUpdateGroup.MainStagePanel.ToneGroup.Tone.ToneRectangel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuleToneRectangleToBeatLine extends DragAndDropRule{

	@Autowired
	private GuiContext guiContext;
	
	@Autowired
	private GuiService guiService;
	
	public RuleToneRectangleToBeatLine() {
		super(ToneRectangel.class, BeatLine.class);
	}

	@Override
	void onDropped(Object from, Object to, DragEvent event) {
		ToneRectangel toneRectangel = (ToneRectangel) from;
		DataNode dataNode = toneRectangel.getDataNode();
		BeatLine beatLine = (BeatLine) to;
		
		dataNode.setTone(dataNode.tone() + (int)Math.ceil(guiService.CRowYPositionInPixel() / guiContext.pixelHeightPerTone() - event.getY()/guiContext.pixelHeightPerTone() - dataNode.calculateGlobalTone()));
		dataNode.setPosition(dataNode.position() - (dataNode.calculateGlobalPosition() - beatLine.getPosition()));
	}

}
