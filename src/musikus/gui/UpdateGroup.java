package musikus.gui;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;

public abstract class UpdateGroup extends Group {

	public final Property<?> property;

	public UpdateGroup(Property<?> property) {
		this.property = property;
		property.addListener(this::onChange);
	}
	
	private void onChange(ObservableValue<? extends Object> observeable, Object old, Object newOne){
		beforeChange();
		getChildren().clear();
		onChange();
	}
	
	protected void beforeChange(){
		// to override
	}
	
	abstract void onChange();

}
