package application.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityType extends Entity {

	private static final Map<Class<? extends Entity>, EntityType> entityTypes = new HashMap<>();

	public final Class<? extends Entity> clazz;
	public final String name;
	public final String description;

	public EntityType(Class<? extends Entity> clazz, String name,
			String description) {
		this.clazz = clazz;
		this.name = name;
		this.description = description;
		
		entityTypes.put(clazz, this);
	}
	
	public static EntityType getEntityType(Class<? extends Entity> clazz){
		return entityTypes.get(clazz);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
