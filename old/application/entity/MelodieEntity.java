package application.entity;

import javolution.util.FastTable;
import application.TonesEntity;

public class MelodieEntity extends TonesEntity  {

	public MelodieEntity(){
	}
	
	protected MelodieEntity(FastTable<ToneEntity> tones) {
		super(tones);
	}
 
}
