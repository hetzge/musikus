package application;

import java.lang.reflect.InvocationTargetException;

import javolution.util.FastTable;
import application.entity.Entity;
import application.entity.ToneEntity;

public abstract class TonesEntity extends Entity implements Toneable {

	private final FastTable<ToneEntity> tones = new FastTable<>();;

	public TonesEntity(){
	}
	
	protected TonesEntity(FastTable<ToneEntity> tones){
		this.tones.addAll(tones);
	}
	
	public void addTones(ToneEntity... tones) {
		for (ToneEntity toneEntity : tones) {
			this.tones.add(toneEntity);
		}
	}

	public void setDuration(int duration) {
		for (ToneEntity toneEntity : tones) {
			toneEntity.setDuration(duration);
		}
	}

	public void setPosition(int position) {
		for (ToneEntity toneEntity : tones) {
			toneEntity.setPosition(position);
		}
	}
	
	public int calculateMaxLength(){
		int maxLength = 0;
		for (ToneEntity toneEntity : tones) {
			if(toneEntity.getPosition() + toneEntity.getDuration() > maxLength){
				maxLength = toneEntity.getPosition() + toneEntity.getDuration();
			}
		}
		return maxLength;
	}

	@Override
	public FastTable<ToneEntity> getTones() {
		FastTable<ToneEntity> result = new FastTable<ToneEntity>();
		for (ToneEntity toneEntity : tones) {
			result.add(toneEntity);
		}
		return result;
	}
	
	public TonesEntity copy() {
		try {
			return getClass().getConstructor(FastTable.class).newInstance(tones);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("shit happens");
		}
	}
}
