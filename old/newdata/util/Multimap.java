package newdata.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Multimap<KEY, VALUE> extends HashMap<KEY, Set<VALUE>> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<VALUE> get(Object key) {
		 if(!containsKey(key))  put((KEY) key, new HashSet<VALUE>());
		 return super.get(key);
	}
	
}
