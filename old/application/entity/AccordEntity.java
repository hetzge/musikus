package application.entity;

import javolution.util.FastTable;
import application.TonesEntity;

public class AccordEntity extends TonesEntity {
	
	public AccordEntity() {
	}

	protected AccordEntity(FastTable<ToneEntity> tones) {
		super(tones);
	}
}
