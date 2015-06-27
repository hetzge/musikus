package newdata;

import java.util.function.Function;

public class Processor<TYPE_FROM, TYPE_TO> {

	public final Function<TYPE_FROM, TYPE_TO> function;
	public final Class<TYPE_FROM> classFrom;
	public final Class<TYPE_TO> classTo;

	public Processor(Class<TYPE_FROM> classFrom, Class<TYPE_TO> classTo, Function<TYPE_FROM, TYPE_TO> function) {
		this.function = function;
		this.classFrom = classFrom;
		this.classTo = classTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classFrom == null) ? 0 : classFrom.hashCode());
		result = prime * result + ((classTo == null) ? 0 : classTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processor<?, ?> other = (Processor<?, ?>) obj;
		if (classFrom == null) {
			if (other.classFrom != null)
				return false;
		} else if (!classFrom.equals(other.classFrom))
			return false;
		if (classTo == null) {
			if (other.classTo != null)
				return false;
		} else if (!classTo.equals(other.classTo))
			return false;
		return true;
	}
	
	
	
}
