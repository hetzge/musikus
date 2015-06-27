package musikus.data;

public class PositionRelation extends DataRelation {

	protected PositionRelation(DataGroup group, DataNode from, DataNode to) {
		super(group, from, to);
		
		if (from.outgoingPosition != null)
			from.outgoingPosition.to.incomingPositions.remove(from.outgoingPosition);
		from.outgoingPosition = this;
		to.incomingPositions.add(this);
	}

}
