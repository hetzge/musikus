package application;

import application.entity.Entity;
import application.entity.ToneEntity;
import javolution.util.FastTable;

public  class CompositionEntity extends Entity implements Toneable {

	private final FastTable<TonesEntity> toneCollections = new FastTable<TonesEntity>();
	
	public void add(TonesEntity... toneCollectionEntities){
		for (TonesEntity toneCollectionEntity : toneCollectionEntities) {
			toneCollections.add(toneCollectionEntity);
		}
	}

	@Override
	public FastTable<ToneEntity> getTones() {
		FastTable<ToneEntity> result = new FastTable<ToneEntity>();
		for (TonesEntity toneCollectionEntity : toneCollections) {
			result.addAll(toneCollectionEntity.getTones());
		}
		return result;
	}
	
}
