package newdata;

import java.util.function.Function;

public class Relation<TYPE_FROM, TYPE_TO> {

	public final EagerNode<TYPE_FROM> from;
	public final EagerNode<TYPE_TO> to;
	public final Function<TYPE_FROM, TYPE_TO> funtion;

	public Relation(EagerNode<TYPE_FROM> from, EagerNode<TYPE_TO> to, Function<TYPE_FROM, TYPE_TO> funtion) {
		this.from = from;
		this.to = to;
		this.funtion = funtion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		Relation<?, ?> other = (Relation<?, ?>) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

}
