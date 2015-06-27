package musikus.base;

public final class Constant {

	public static final String[] TONE_NAMES = new String[] { "C", "C#", "D", "D#", "E", "F", "G", "G#", "A", "A#", "B" };
	public static final int NUM_OF_ROWS = 150;
	public static final int BEATS_PER_BAR = 4;
	public static final int BAR_LENGTH = 400;
	public static final int BEAT_LENGTH = BAR_LENGTH / BEATS_PER_BAR;
	public static final int STAGE_INFO_WIDTH = 100;
	public static final int STAGE_WIDTH = 5000;
	public static final int C_ROW_INDEX = 25;
	public static final int TONE_Y_OFFSET = 1;
	public static final int RHYTM_BOX_HEIGHT = 100;
	public static final int BEGIN_AND_END_ANCHOR_OFFSET = 20;
	public static final int C_OFFSET = 60;
	
	private Constant() {
	}

}
