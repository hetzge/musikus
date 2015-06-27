package application;

import javolution.util.FastSet;

public class Tag {

	public final static FastSet<String> keys = new FastSet<String>();
	
	public final String key;
	public final String value;

	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
		keys.add(key);
	}
	
	@Override
	public String toString() {
		return "<"+key+ (value != null && !value.isEmpty() ? ":" + value : "") + ">";
	}

}
