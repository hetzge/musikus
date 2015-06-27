package application;

public final class MusicCalculator {
	private MusicCalculator() {
	}

	public static int oneBeatInMS(int bpm, int takt2) {
		return 60000 / bpm * 4 / takt2;
	}
}
