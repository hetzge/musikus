package musikus.data;

public abstract class DataRelation {

	final DataGroup group;
	final DataNode from;
	final DataNode to;

	DataRelation(DataGroup group, DataNode from, DataNode to) {
		if(from == null) throw new IllegalArgumentException("from is null");
		if(to == null) throw new IllegalArgumentException("to is null");
		
		this.from = from;
		this.to = to;

		if (from.group == to.group)
			group = from.group;
		this.group = group;
	}

}
