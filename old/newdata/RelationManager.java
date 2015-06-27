package newdata;

import java.util.Set;

import newdata.util.Multimap;

public class RelationManager {

	public static final RelationManager INSTANCE = new RelationManager();
	
	private final Multimap<String, Relation<?, ?>> relationsFromUUID = new Multimap<>();
	private final Multimap<String, Relation<?, ?>> relationsToUUID = new Multimap<>();
	
	public void registerRelation(Relation<?, ?> relation){
		if(relation == null) throw new IllegalArgumentException("The parameter relation is null");
		relationsFromUUID.get(relation.from.getNode().id).add(relation);
		relationsToUUID.get(relation.to.getNode().id).add(relation);
	}
	
	public <TYPE_FROM, TYPE_TO> Set<Relation<?,?>> getRelationsFromUUID(String uuid){
		return getRelationsByUUID(relationsFromUUID, uuid);
	}
	
	public <TYPE_FROM, TYPE_TO> Set<Relation<?, ?>> getRelationsToUUID(String uuid){
		return getRelationsByUUID(relationsToUUID, uuid);
	}
	
	private <TYPE_FROM, TYPE_TO> Set<Relation<?, ?>> getRelationsByUUID(Multimap<String, Relation<?, ?>> fromMap, String uuid){
		if(uuid == null || uuid.isEmpty()) throw new IllegalArgumentException("The parameter uuid is null");
		return fromMap.get(uuid);
	}

}
