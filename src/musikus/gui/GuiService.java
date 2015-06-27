package musikus.gui;

import musikus.base.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuiService {
	
	@Autowired
	private Player player;
	
	@Autowired
	private GuiContext guiContext;

	public GuiService() {
	}

	public void refreshMainStage(){
		guiContext.mainStageRefresh.toggle();
	}
	
	public void enableDragAndDropLine() {
		guiContext.setDragAndDropActive(true);
	}

	public void disableDragAndDropLine() {
		guiContext.setDragAndDropActive(false);
	}
	
	public int calculateBarWidthInPixel(){
		return guiContext.beats() * guiContext.pixelPerBeat();
	}
	
	public int stageHeightInPixel(){
		return Constant.NUM_OF_ROWS * guiContext.pixelHeightPerTone();
	}
	
	public int CRowYPositionInPixel(){
		return Constant.C_ROW_INDEX * guiContext.pixelHeightPerTone();
	}
	
}
