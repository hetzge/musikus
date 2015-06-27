package musikus.javafx;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

public class PropertyChangeGroup extends SimpleBooleanProperty {

	private final Set<Property<?>> registeredProperties = new HashSet<>();

	public PropertyChangeGroup(Property<?>... properties) {
		addProperties(properties);
	}

	public void addProperties(Property<?>... properties) {
		for (Property<?> property : properties) {
			if (!registeredProperties.contains(property)) {
				property.addListener((a, b, c) -> {
					toggle();
				});
				registeredProperties.add(property);
			}
		}
	}
	
	public void toggle(){
		set(!get());
	}

}
