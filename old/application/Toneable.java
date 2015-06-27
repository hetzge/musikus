package application;

import application.entity.ToneEntity;
import javolution.util.FastTable;

public interface Toneable {
	public FastTable<ToneEntity> getTones();
}
