package musikus.gui;

import musikus.base.Utils;
import musikus.data.DataGroup;
import musikus.data.Demo;
import musikus.javafx.PropertyChangeGroup;
import musikus.javafx.PropertyDelay;

import org.springframework.stereotype.Component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Component
public class GuiContext {
	
	final StringProperty beats = new SimpleStringProperty("4");
	public int beats(){ return Utils.parseInt(beats.get(), 4);}
	public void setBeats(int beats){ this.beats.set(beats+""); }
	
	final StringProperty beat = new SimpleStringProperty("4");
	public int beat(){ return Utils.parseInt(beat.get(), 4);}
	public void setBeat(int beat){ this.beat.set(beat+""); }
	
	final StringProperty bpm = new SimpleStringProperty("120");
	public int bpm(){ return Utils.parseInt(bpm.get(), 120); }
	public void setBpm(int bpm){ this.bpm.set(bpm+""); }
	
	final IntegerProperty pixelPerBeat = new SimpleIntegerProperty(50);
	public int pixelPerBeat(){ return pixelPerBeat.get(); }
	public void setPixelPerBeat(int pixelPerBeat){ this.pixelPerBeat.set(pixelPerBeat); };
	
	final IntegerProperty pixelHeightPerTone = new SimpleIntegerProperty(25);
	public int pixelHeightPerTone(){ return pixelHeightPerTone.get(); }
	public void setPixelHeightPerTone(int pixelHeightPerTone){ this.pixelHeightPerTone.set(pixelHeightPerTone); }
	
	final ObjectProperty<DataGroup> dataGroup = new SimpleObjectProperty<DataGroup>(Demo.DEMO);
	public DataGroup dataGroup(){ return dataGroup.get(); }
	public void setDataGroup(DataGroup dataGroup){ this.dataGroup.set(dataGroup); }
	
	final ObjectProperty<DataGroup> selectedDataGroup = new SimpleObjectProperty<DataGroup>(dataGroup());
	public DataGroup selectedDataGroup(){ return selectedDataGroup.get(); }
	public void setSelectedDataGroup(DataGroup dataGroup){ this.selectedDataGroup.set(dataGroup); }
	
	final BooleanProperty dragAndDropActive = new SimpleBooleanProperty(false);
	public boolean dragAndDropActive(){ return dragAndDropActive.get(); };
	public void setDragAndDropActive(boolean dragAndDropActive){ this.dragAndDropActive.set(dragAndDropActive); }
	
	
	// REFRESH GUI
	final PropertyChangeGroup mainStageRefresh = new PropertyChangeGroup(beats, beat, new PropertyDelay<Integer>(pixelPerBeat, 250L), new PropertyDelay<Integer>(pixelHeightPerTone, 250L), dataGroup, selectedDataGroup); 
	
}
