package musikus.base;

public final class Utils {

	private Utils() {
	}

	public static int parseInt(String from, int defaultValue) {
		try {
			return Integer.valueOf(from);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

}
