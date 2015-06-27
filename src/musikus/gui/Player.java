package musikus.gui;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import musikus.base.MusicCalculator;
import musikus.data.DataGroup;
import musikus.data.DataNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import promidi.MidiIO;
import promidi.MidiOut;
import promidi.Note;
import promidi.Q;
import promidi.Sequencer;
import promidi.Song;
import promidi.Track;

@Component
public class Player {

	@Autowired
	private MusicCalculator musicCalculator;
	
	@Autowired 
	private GuiContext guiContext;
	
	public final DoubleProperty position = new SimpleDoubleProperty();
	public final BooleanProperty running = new SimpleBooleanProperty();
	private Sequencer sequencer;
	private long lastTickPosition = 0;
	private Thread playThread;

	public Player() {
		sequencer = new Sequencer();
	}

	private Track convertToTrack(DataGroup dataGroup) {

		MidiIO midiIO = MidiIO.getInstance();
		MidiOut midiOut = midiIO.getMidiOut(1, 3);

		Track track = new Track("one", midiOut);
		track.setQuantization(Q._1_64);

		for (DataNode dataNode : dataGroup.nodesProperty) {
			int pitch = musicCalculator.toProMidiNote(dataNode.calculateGlobalTone());
			int velocity = 127;
			int length = (int) (musicCalculator.millisecondsPerBeat() * dataNode.duration());
			long ticks = musicCalculator.toProMidiTicks(dataNode.calculateGlobalPosition());

			track.addEvent(new Note(pitch, velocity, length), ticks);
		}

		return track;
	}

	public void play(DataGroup dataGroup) {
		
		Song song = new Song("", guiContext.bpm());
		song.addTrack(convertToTrack(dataGroup));

		if (playThread != null && playThread.isAlive()) {
			playThread.interrupt();
		}
		
		if(sequencer != null){
			sequencer.stop();
		}
		
		sequencer = new Sequencer();
		sequencer.setSong(song);
		sequencer.setLoopStartPoint(0);
		sequencer.setLoopEndPoint(5000);
		sequencer.setLoopCount(1);
		sequencer.setTickPosition(lastTickPosition);
		
		System.out.println("start: " + lastTickPosition);
		
		sequencer.noLoop();
		sequencer.start();

		playThread = new Thread(() -> {
			try {
				Platform.runLater(() -> running.set(true));
				while (sequencer.isRunning() && sequencer.getTickPosition() < 5000) {
					Platform.runLater(() -> position.set(musicCalculator.toPixel(sequencer.getTickPosition())));
					Thread.sleep(10);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pause();
			}
		});
		playThread.start();
	}
	
	public void pause(){
		sequencer.stop();
		lastTickPosition = sequencer.getTickPosition();
		System.out.println("stop: " + lastTickPosition);
		sequencer.setTickPosition(lastTickPosition);
		Platform.runLater(() -> running.set(false));
		Platform.runLater(() -> position.set(musicCalculator.toPixel(lastTickPosition)));
	}
	
	public void stop(){
		lastTickPosition = 0;
		sequencer.setTickPosition(0);
		Platform.runLater(() -> running.set(false));
		Platform.runLater(() -> position.set(0));
		sequencer.stop();
	}

}
