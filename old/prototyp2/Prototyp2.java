package prototyp2;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javolution.util.FastTable;

public class Prototyp2 extends BorderPane {

	/*
	 * Groups
	 * Rerenderables (Wrapper)
	 */
	
	/*
	 * TODO Relations
	 * TODO Drag n Drop Relations
	 * TODO Mauszeiger
	 * TODO Mouseovers
	 */
	
	private static final String[] TONE_NAMES = new String[] { "C", "C#", "D", "D#", "E", "F", "G", "G#", "A", "A#", "B" };

	private static final int ROW_HEIGHT = 25;
	private static final int NUM_OF_ROWS = 150;
	private static final int BEATS_PER_BAR = 4;
	private static final int BAR_LENGTH = 400;
	private static final int BEAT_LENGTH = BAR_LENGTH / BEATS_PER_BAR;
	private static final int STAGE_INFO_WIDTH = 100;
	private static final int STAGE_WIDTH = 5000;
	private static final int STAGE_HEIGHT = NUM_OF_ROWS * ROW_HEIGHT;
	private static final int C_ROW_INDEX = 25;
	private static final int C_ROW_Y_POSITION = C_ROW_INDEX * ROW_HEIGHT;
	private static final int TONE_Y_OFFSET = 5;
	private static final int RHYTM_BOX_HEIGHT = 100;
	private static final int BEGIN_AND_END_ANCHOR_OFFSET = 20;

	private final ScrollPane mainStageScrollPane;
	private final Pane mainStage;
	private final VBox vBox;
	private final HBox hBox;
	private final ScrollPane rhythmStageScrollPane;
	private final Pane rhythmStage;
	private final ScrollPane stageInfoScrollPane;
	private final Pane stageInfo;

	public Prototyp2() {

		vBox = new VBox();
		setCenter(vBox);
		
		hBox = new HBox();
		vBox.getChildren().add(hBox);

		// left info
		stageInfoScrollPane = new ScrollPane();
		stageInfoScrollPane.setPrefSize(0, STAGE_HEIGHT);
		stageInfoScrollPane.setMinWidth(STAGE_INFO_WIDTH);
		stageInfoScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		stageInfoScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		hBox.getChildren().add(stageInfoScrollPane);

		stageInfo = new Pane();
		stageInfoScrollPane.setContent(stageInfo);

		for (int i = 0; i < NUM_OF_ROWS; i++) {
			Rectangle rectangle = new Rectangle(0, i * ROW_HEIGHT, STAGE_INFO_WIDTH, ROW_HEIGHT);
			rectangle.setFill(Color.WHITESMOKE);
			rectangle.setStroke(Color.BLACK);
			rectangle.setStrokeWidth(2d);
			stageInfo.getChildren().add(rectangle);
			
			
			String nameOfTone = TONE_NAMES[TONE_NAMES.length - 1 - ((C_ROW_INDEX + i + 4) % TONE_NAMES.length)];
			Label label = new Label(nameOfTone);
			label.setLayoutX(STAGE_INFO_WIDTH - 50);
			label.setLayoutY(i * ROW_HEIGHT);
			stageInfo.getChildren().add(label);
		}

		// main stage
		mainStageScrollPane = new ScrollPane();
		mainStageScrollPane.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);
		mainStageScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		mainStageScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		hBox.getChildren().add(mainStageScrollPane);

		mainStage = new Pane();
		mainStage.setMinSize(STAGE_WIDTH, STAGE_HEIGHT);
		mainStageScrollPane.setContent(mainStage);

		// rows
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			Rectangle rectangle = new Rectangle(0, i * ROW_HEIGHT, STAGE_WIDTH, ROW_HEIGHT - 2);
			rectangle.setArcWidth(5);
			rectangle.setArcHeight(5);
			rectangle.setFill(Color.web("#5F686D"));
			rectangle.setStroke(Color.web("#2D3D47"));
			rectangle.setStrokeWidth(1d);
			rectangle.setOnMouseEntered(event -> rectangle.setFill(Color.GREEN));
			rectangle.setOnMouseExited(event -> rectangle.setFill(Color.web("#5F686D")));
			mainStage.getChildren().add(rectangle);
		}

		// takte
		for (int x = 0; x < STAGE_WIDTH; x += BAR_LENGTH) {
			Line line = new Line(x, 0, x, STAGE_HEIGHT);
			line.setStroke(Color.web("#CFD0D1"));
			line.setStrokeWidth(2d);
			mainStage.getChildren().add(line);
		}

		// beats
		for (int x = 0; x < STAGE_WIDTH; x += BEAT_LENGTH) {
			Line line = new Line(x, 0, x, STAGE_HEIGHT);
			line.setStroke(Color.web("#D3FFFF"));
			line.setStrokeWidth(1d);
			mainStage.getChildren().add(line);
		}

		// tones
		FastTable<Tone> tones = new FastTable<>();
		tones.add(new Tone(0, 0, 1));
		tones.add(new Tone(1, 1, 1));
		tones.add(new Tone(5, 3, 5));

		for (Tone tone : tones) {
			double positionX = tone.positionInBeats * BEAT_LENGTH;
			double positionY = C_ROW_Y_POSITION - tone.pitchRelativToC * ROW_HEIGHT;
			double length = tone.durationInBeats * BEAT_LENGTH;
			
			double height = ROW_HEIGHT - TONE_Y_OFFSET * 2;
			double y = positionY + TONE_Y_OFFSET;
			
			Rectangle rectangle = new Rectangle(positionX, y, length, height);
			rectangle.setFill(Color.web("#433665"));
			rectangle.setEffect(new InnerShadow());
			new DragAndDrop(rectangle);
			mainStage.getChildren().add(rectangle);
			
			
			Polygon beginAnchor = new Polygon(positionX + BEGIN_AND_END_ANCHOR_OFFSET, y, positionX + BEGIN_AND_END_ANCHOR_OFFSET, y + height, positionX, y + height / 2d);
			beginAnchor.setFill(Color.web("#433665"));
			beginAnchor.setEffect(new InnerShadow());
			Polygon endAnchor = new Polygon(positionX + length - BEGIN_AND_END_ANCHOR_OFFSET, y, positionX + length - BEGIN_AND_END_ANCHOR_OFFSET, y + height, positionX + length, y + height / 2d);
			endAnchor.setFill(Color.web("#433665"));
			endAnchor.setEffect(new InnerShadow());
			mainStage.getChildren().add(beginAnchor);
			mainStage.getChildren().add(endAnchor);
			
			Line beginRhytmenAnchor = new Line(positionX, y + height, positionX, y + height + 25);
			beginRhytmenAnchor.setFill(Color.BLACK);
			beginRhytmenAnchor.setStrokeWidth(2d);
			Line endRhytmenAnchor = new Line(positionX + length, y + height, positionX + length, y + height + 25);
			endRhytmenAnchor.setFill(Color.BLACK);
			endRhytmenAnchor.setStrokeWidth(2d);
			mainStage.getChildren().add(beginRhytmenAnchor);
			mainStage.getChildren().add(endRhytmenAnchor);
		}
		
		// relations
		mainStageScrollPane.vvalueProperty().addListener((value, oldValue, newValue) -> stageInfoScrollPane.setVvalue((double) newValue));
		stageInfoScrollPane.vvalueProperty().addListener((value, oldValue, newValue) -> mainStageScrollPane.setVvalue((double) newValue));
		
		// rhythm
		rhythmStageScrollPane = new ScrollPane();
		rhythmStageScrollPane.setPrefSize(STAGE_WIDTH, 0);
		rhythmStageScrollPane.setMinHeight(RHYTM_BOX_HEIGHT);
		rhythmStageScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		rhythmStageScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		rhythmStageScrollPane.setPadding(new Insets(0, 0, 0, STAGE_INFO_WIDTH));

		mainStageScrollPane.hvalueProperty().addListener((value, oldValue, newValue) -> rhythmStageScrollPane.setHvalue((double) newValue));
		rhythmStageScrollPane.hvalueProperty().addListener((value, oldValue, newValue) -> mainStageScrollPane.setHvalue((double) newValue));

		vBox.getChildren().add(rhythmStageScrollPane);

		rhythmStage = new Pane();
		rhythmStageScrollPane.setContent(rhythmStage);

		Rectangle rhytmnBox = new Rectangle(0, 75, STAGE_WIDTH, 25);
		rhythmStage.getChildren().add(rhytmnBox);

		double[] rhytmPattern = new double[] { 0d, 1d, 2d, 3d, 3.5d };
		for (double d : rhytmPattern) {
			double position = d * BEAT_LENGTH;
			Polygon polygon = new Polygon(new double[] { position, 60, position + 20, 80, position - 20, 80 });
			polygon.setFill(Color.web("#433665"));
			polygon.setEffect(new InnerShadow());
			rhythmStage.getChildren().add(polygon);

			Line line = new Line(position, 0, position, RHYTM_BOX_HEIGHT);
			rhythmStage.getChildren().add(line);
		}

	}

	public class Tone {
		public final double durationInBeats; // position objects -> ref to stage
		public final double positionInBeats;
		public final int pitchRelativToC;

		public Tone(int pitchRelativToC, double positionInBeats, double durationInBeats) {
			this.durationInBeats = durationInBeats;
			this.positionInBeats = positionInBeats;
			this.pitchRelativToC = pitchRelativToC;
		}
	}

}
