package musikus.data;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import musikus.base.Constant;

public class DataNode {
	public IntegerProperty changeProperty = new SimpleIntegerProperty();

	DataGroup group;
	DataRelation outgoingTone;
	DataRelation outgoingPosition;
	final Set<DataRelation> incomingTones = new HashSet<>();
	final Set<DataRelation> incomingPositions = new HashSet<>();
	final IntegerProperty toneProperty;
	final DoubleProperty durationProperty;
	final DoubleProperty positionProperty;

	protected DataNode() {
		toneProperty = new SimpleIntegerProperty(Constant.C_ROW_INDEX);
		durationProperty = new SimpleDoubleProperty(1);
		positionProperty = new SimpleDoubleProperty(0);

		changeProperty.bind(toneProperty);
		changeProperty.bind(durationProperty);
		changeProperty.bind(positionProperty);
	}

	public DataNode(DataGroup group, int tone, double duration, double positon) {
		this();

		toneProperty.set(tone);
		durationProperty.set(duration);
		positionProperty.set(positon);
		group.add(this);
	}

	public int tone() {
		return toneProperty.get();
	}

	public double duration() {
		return durationProperty.get();
	}

	public double position() {
		return positionProperty.get();
	}

	public int calculateGroupTone() {
		if (!hasToneDependency())
			return tone();
		
		// TODO WARNUNG: Was ist wenn to ausserhalb der Gruppe ist ?
		return outgoingTone.to.calculateGroupTone() + tone();
	}

	public double calculateGroupPosition() {
		if (!hasPositionDependency())
			return position();
		
		// TODO WARNUNG: Was ist wenn to ausserhalb der Gruppe ist ?
		return outgoingPosition.to.calculateGroupPosition() + position();
	}

	public int calculateGlobalTone() {
		if(group == null) return calculateGroupTone();
		return calculateGroupTone() + group.calculateGroupTone();
	}

	public double calculateGlobalPosition() {
		if(group == null) return calculateGroupPosition();
		return calculateGroupPosition() + group.calculateGlobalPosition();
	}
	
	public boolean hasToneDependency(){
		return outgoingTone != null;
	}
	
	public boolean hasPositionDependency(){
		return outgoingPosition != null;
	}
	
	public DataNode toneDependency(){
		if(!hasToneDependency()) throw new IllegalAccessError("Call toneDependency() only after successful check with hasToneDependency()");
		return outgoingTone.to;
	}
	
	public DataNode positionDependency(){
		if(!hasPositionDependency()) throw new IllegalAccessError("Call positionDependency() only after successful check with hasPositionDependency()");
		return outgoingPosition.to;
	}
	
	public void setTone(int tone){
		toneProperty.set(tone);
	}
	
	public void setPosition(double position){
		positionProperty.set(position);
	}

}
