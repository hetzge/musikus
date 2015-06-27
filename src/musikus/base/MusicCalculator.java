package musikus.base;

import musikus.gui.GuiContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicCalculator {

	@Autowired
	private GuiContext guiContext;

	public int millisecondsPerBeat() {
		return guiContext.bpm() / 60000;
	}

	public int millisecondsPerBar() {
		return millisecondsPerBeat() * guiContext.beats();
	}

	public double pixelPerMillisecond() {
		return millisecondsPerBeat() / guiContext.pixelPerBeat();
	}

	public int millisecondsPerPixel() {
		return guiContext.pixelPerBeat() / millisecondsPerBeat();
	}

	public int toProMidiNote(int tone) {
		return tone + Constant.C_OFFSET;
	}

	public long toProMidiTicks(double lengthInBeats) {
		return (long) (64 / guiContext.beats() * lengthInBeats);
	}

	public double toBeats(long ticks){
		 return ticks * guiContext.beats() / 64; 
	}

	public int toPixel(long ticks) {
		return (int) (toBeats(ticks) * guiContext.pixelPerBeat());
	}

}
