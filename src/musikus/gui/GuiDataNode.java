package musikus.gui;

import static musikus.base.Constant.BEGIN_AND_END_ANCHOR_OFFSET;
import static musikus.base.Constant.TONE_Y_OFFSET;
import musikus.data.DataNode;

public class GuiDataNode {

	public final DataNode dataNode;

	public final double toneStartX;
	public final double toneStartY;
	public final double toneWidth;
	public final double toneHeight;

	public final double beginRhythmStartX;
	public final double beginRhythmStartY;
	public final double beginRhythmEndX;
	public final double beginRhythmEndY;

	public final double endRhythmStartX;
	public final double endRhythmStartY;
	public final double endRhythmEndX;
	public final double endRhythmEndY;

	public final double beginToneAX;
	public final double beginToneAY;
	public final double beginToneBX;
	public final double beginToneBY;
	public final double beginToneCX;
	public final double beginToneCY;

	public final double endToneAX;
	public final double endToneAY;
	public final double endToneBX;
	public final double endToneBY;
	public final double endToneCX;
	public final double endToneCY;

	public GuiDataNode(DataNode dataNode, GuiContext guiContext, GuiService guiService) {
		this.dataNode = dataNode;

		toneStartX = dataNode.calculateGlobalPosition() * guiContext.pixelPerBeat();
		toneStartY = guiService.CRowYPositionInPixel() - dataNode.calculateGlobalTone() * guiContext.pixelHeightPerTone() + TONE_Y_OFFSET;
		toneWidth = dataNode.duration() *  guiContext.pixelPerBeat();
		toneHeight = guiContext.pixelHeightPerTone() - TONE_Y_OFFSET * 2;

		beginRhythmStartX = toneStartX;
		beginRhythmStartY = toneStartY + toneHeight;
		beginRhythmEndX = beginRhythmStartX;
		beginRhythmEndY = beginRhythmStartY + 25;
		
		endRhythmStartX = toneStartX + toneWidth;
		endRhythmStartY = toneStartY + toneHeight;
		endRhythmEndX = endRhythmStartX;
		endRhythmEndY = endRhythmStartY + 25;
		
		beginToneAX = toneStartX + BEGIN_AND_END_ANCHOR_OFFSET;
		beginToneAY = toneStartY + TONE_Y_OFFSET;
		beginToneBX = toneStartX + BEGIN_AND_END_ANCHOR_OFFSET;
		beginToneBY = toneStartY + toneHeight - TONE_Y_OFFSET;
		beginToneCX = toneStartX;
		beginToneCY = toneStartY + toneHeight / 2d;
		
		endToneAX = toneStartX + toneWidth - BEGIN_AND_END_ANCHOR_OFFSET;
		endToneAY = toneStartY + TONE_Y_OFFSET;
		endToneBX = toneStartX + toneWidth - BEGIN_AND_END_ANCHOR_OFFSET;
		endToneBY = toneStartY + toneHeight - TONE_Y_OFFSET;
		endToneCX = toneStartX + toneWidth;
		endToneCY = toneStartY + toneHeight / 2d;
	}

}
