package musikus.gui.dad;

import org.springframework.stereotype.Component;

import javafx.scene.input.DragEvent;
import musikus.gui.Gui.MainStageScrollPanel.MainStageUpdateGroup.MainStagePanel.ToneGroup.Tone.BeginToneAnchor;
import musikus.gui.Gui.MainStageScrollPanel.MainStageUpdateGroup.MainStagePanel.ToneGroup.Tone.EndToneAnchor;

@Component
public class RuleEndToneAnchorToBeginToneAnchor extends DragAndDropRule{

	public RuleEndToneAnchorToBeginToneAnchor() {
		super(EndToneAnchor.class, BeginToneAnchor.class);
	}

	@Override
	void onDropped(Object from, Object to, DragEvent event) {
		System.out.println("yeah");
	}

}
