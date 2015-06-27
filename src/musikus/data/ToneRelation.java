package musikus.data;

public class ToneRelation extends DataRelation {

	protected ToneRelation(DataGroup group, DataNode from, DataNode to) {
		super(group, from, to);
		
		if (from.outgoingTone != null)
			from.outgoingTone.to.incomingTones.remove(from.outgoingTone);
		from.outgoingTone = this;
		to.incomingTones.add(this);
	}

}
