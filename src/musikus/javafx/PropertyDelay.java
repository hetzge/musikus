package musikus.javafx;

import java.time.Duration;

import org.reactfx.EventStreams;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class PropertyDelay<T> extends SimpleObjectProperty<T> {
	
	@SuppressWarnings("unchecked")
	public PropertyDelay(Property<?> property, long delayInMilliseconds) {
		EventStreams.valuesOf(property).successionEnds(Duration.ofMillis(delayInMilliseconds)).subscribe(t -> set((T) t));
	}
	
}
