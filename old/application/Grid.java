package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javolution.util.FastTable;
import application.entity.ToneEntity;

public class Grid extends BorderPane {

	private final TonesEntity tonesEntity;
	private Pane gridStagePane;
	private boolean lockXY = false;

	public Grid(TonesEntity tonesEntity) {
		this.tonesEntity = tonesEntity;

		HBox taktBox = createTaktBox();
		setTop(taktBox);

		gridStagePane = new AnchorPane();
		setCenter(gridStagePane);

		renderGridStage();
	}

	private HBox createTaktBox() {
		HBox taktHBox = new HBox();

		ChoiceBox<Integer> takt1ChoiceBox = new ChoiceBox<Integer>();
		takt1ChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		taktHBox.getChildren().add(takt1ChoiceBox);
		takt1ChoiceBox.setValue(4);
		takt1ChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				renderGridStage();
			}
		});

		ChoiceBox<Integer> takt2ChoiceBox = new ChoiceBox<Integer>();
		takt2ChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		taktHBox.getChildren().add(takt2ChoiceBox);
		takt2ChoiceBox.setValue(4);
		takt2ChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				renderGridStage();
			}
		});

		return taktHBox;
	}

	private void renderGridStage() {
		GridStage gridStage = new GridStage();
		gridStagePane.getChildren().clear();
		gridStagePane.getChildren().add(gridStage);
	}

	public class GridStage extends ScrollPane {
		private final Group group;

		public GridStage() {
			Utils.setupBorderPaneAnchors(this);
			setHbarPolicy(ScrollBarPolicy.ALWAYS);
			setVbarPolicy(ScrollBarPolicy.ALWAYS);
			group = new Group();
			setContent(group);

			int maxLength = (int) (tonesEntity.calculateMaxLength() * Constants.DEFAULT_MS_WIDTH);
			addHorizontalLines(maxLength);
			addVerticalLines(maxLength);

			FastTable<ToneEntity> tones = tonesEntity.getTones();
			for (ToneEntity toneEntity : tones) {
				ToneRectangle toneRectangle = new ToneRectangle(toneEntity);
				group.getChildren().add(toneRectangle);
			}
		}

		private void addHorizontalLines(int maxLength) {
			for (int i = 0; i < Constants.DEFAULT_ROW_COUNT; i++) {
				int y = calculateStageBottom() - i * Constants.DEFAULT_ROW_HEIGHT;
				Line line = new Line(0, y, maxLength, y);
				group.getChildren().add(line);
			}
		}

		private void addVerticalLines(int maxLength) {
			int x = 0;
			int i = 1;
			while (x < maxLength) {
				x = (int) (MusicCalculator.oneBeatInMS(Constants.DEFAULT_BPM, Constants.TAKT_2) * Constants.DEFAULT_MS_WIDTH) * i;
				Line line = new Line(x, 0, x, calculateStageBottom());
				if (i % Constants.TAKT_2 == 0) {
					line.setStrokeWidth(3);
				}
				group.getChildren().add(line);
				i++;
			}
		}

		public class ToneRectangle extends Rectangle {
			private final ToneEntity toneEntity;

			public ToneRectangle(ToneEntity toneEntity) {
				super(calculateToneXPosition(toneEntity), calculateToneYPosition(toneEntity), calculateToneLength(toneEntity), Constants.DEFAULT_ROW_HEIGHT);
				setFill(Color.DARKRED);
				this.toneEntity = toneEntity;

				setOnMouseDragged(mouseEvent -> {
					mouseEvent.consume();
				});

				setOnMouseReleased(mouseEvent -> {
					int newTone = (int) ((calculateStageBottom() - mouseEvent.getY()) / Constants.DEFAULT_ROW_HEIGHT);
					boolean toneChanged = lockXY && newTone != toneEntity.getTone();
					
					if (!lockXY || !toneChanged) {
						int dragX = (int) mouseEvent.getX() - calculateMSToLength(toneEntity.getPosition());
						toneEntity.setPosition(toneEntity.getPosition() + calculateLengthToMS(dragX));
						setX(calculateToneXPosition(toneEntity));
					}
	
					if (!lockXY || toneChanged) {
						toneEntity.setTone(newTone);
						setY(calculateToneYPosition(toneEntity));
					}
				});
			}
		}
	}

	private int calculateStageBottom() {
		return Constants.DEFAULT_ROW_HEIGHT * Constants.DEFAULT_ROW_COUNT;
	}

	private int calculateToneYPosition(ToneEntity toneEntity) {
		return calculateStageBottom() - (toneEntity.getTone() * Constants.DEFAULT_ROW_HEIGHT + Constants.DEFAULT_ROW_HEIGHT);
	}

	private int calculateToneXPosition(ToneEntity toneEntity) {
		return (int) (toneEntity.getPosition() * Constants.DEFAULT_MS_WIDTH);
	}

	private int calculateToneLength(ToneEntity toneEntity) {
		return calculateMSToLength(toneEntity.getDuration());
	}

	private int calculateLengthToMS(int pixel) {
		return (int) (pixel / Constants.DEFAULT_MS_WIDTH);
	}

	private int calculateMSToLength(int ms) {
		return (int) (ms * Constants.DEFAULT_MS_WIDTH);
	}
}
