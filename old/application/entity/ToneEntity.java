package application.entity;

import javolution.util.FastTable;
import application.TonesEntity;

public class ToneEntity extends TonesEntity {
	private int tone;
	private int duration;
	private int position;

	public ToneEntity() {
		super.addTones(this);
	}

	public ToneEntity(int tone, int position, int duration) {
		this.tone = tone;
		this.duration = duration;
		this.position = position;
	}

	protected ToneEntity(FastTable<ToneEntity> tones) {
		super(tones);
	}

	@Override
	public void addTones(ToneEntity... tones) {
		throw new IllegalAccessError("addTones shouldn't used at ToneEntity");
	}

	public int getTone() {
		return tone;
	}

	public void setTone(int tone) {
		this.tone = tone;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public FastTable<ToneEntity> getTones() {
		return new FastTable<ToneEntity>(this);
	}

}
