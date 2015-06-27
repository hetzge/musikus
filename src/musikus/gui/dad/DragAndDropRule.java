package musikus.gui.dad;

import javafx.scene.input.DragEvent;

public abstract class DragAndDropRule {

	public final Class<?> fromClass;
	public final Class<?> toClass;

	public DragAndDropRule(Class<?> fromClass, Class<?> toClass) {
		this.fromClass = fromClass;
		this.toClass = toClass;
	}

	public boolean canFrom(Class<?> clazz){
		if(clazz == null) throw new IllegalStateException("Every drag event needs a source");
		return clazz.isAssignableFrom(fromClass);
	}
	
	public boolean canTo(Class<?> clazz){
		if(clazz == null) throw new IllegalStateException("Can't check null target");
		return clazz.isAssignableFrom(toClass);
	}
	
	public boolean canFromTo(Class<?> from, Class<?> to){
		boolean canFrom = canFrom(from);
		boolean canTo = canTo(to);
		if(canFrom && canTo){
			return true;
		}
		return false;
	}
	
	abstract void onDropped(Object from, Object to, DragEvent event);
	
}
