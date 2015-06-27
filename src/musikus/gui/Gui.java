package musikus.gui;

import static musikus.base.Constant.C_ROW_INDEX;
import static musikus.base.Constant.NUM_OF_ROWS;
import static musikus.base.Constant.RHYTM_BOX_HEIGHT;
import static musikus.base.Constant.STAGE_INFO_WIDTH;
import static musikus.base.Constant.STAGE_WIDTH;
import static musikus.base.Constant.TONE_NAMES;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import musikus.data.DataGroup;
import musikus.data.DataNode;
import musikus.data.Demo;
import musikus.gui.Gui.MenüBar.ScaleGridPanel;
import musikus.gui.dad.DragAndDrop;
import musikus.javafx.PropertyChangeGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Gui extends BorderPane {

	// ################################
	// Drag and Drop binding
	// ################################

	public final DoubleProperty startXProperty = new SimpleDoubleProperty();
	public final DoubleProperty startYProperty = new SimpleDoubleProperty();
	public final DoubleProperty endXProperty = new SimpleDoubleProperty();
	public final DoubleProperty endYProperty = new SimpleDoubleProperty();

	// ################################

	private final GuiService guiService;
	private final GuiContext guiContext;
	private final Player player;

	private double lastMainStageScrollX = 0d;
	private double lastMainStageScrollY = 0d;

	final VBox vBox;
	final HBox hBox;
	final LeftInfoScrollPanel leftInfoScrollPanel;

	final RhytmScrollPanel rhytmScrollPanel;
	final MenüBar menüBar;
	final MainStageScrollPanel mainStageScrollPanel;

	@Autowired
	public Gui(Player player, GuiService guiService, GuiContext guiContext) {
		this.player = player;
		this.guiService = guiService;
		this.guiContext = guiContext;

		vBox = new VBox();
		hBox = new HBox();
		leftInfoScrollPanel = new LeftInfoScrollPanel();
		rhytmScrollPanel = new RhytmScrollPanel();
		mainStageScrollPanel = new MainStageScrollPanel();
		menüBar = new MenüBar();

		setCenter(vBox);
		vBox.getChildren().add(menüBar);
		vBox.getChildren().add(hBox);
		hBox.getChildren().add(leftInfoScrollPanel);
		hBox.getChildren().add(mainStageScrollPanel);
		vBox.getChildren().add(rhytmScrollPanel);

		mainStageScrollPanel.vvalueProperty().addListener((value, oldValue, newValue) -> leftInfoScrollPanel.setVvalue((double) newValue));
		leftInfoScrollPanel.vvalueProperty().addListener((value, oldValue, newValue) -> mainStageScrollPanel.setVvalue((double) newValue));
		mainStageScrollPanel.hvalueProperty().addListener((value, oldValue, newValue) -> rhytmScrollPanel.setHvalue((double) newValue));
		rhytmScrollPanel.hvalueProperty().addListener((value, oldValue, newValue) -> mainStageScrollPanel.setHvalue((double) newValue));
	}

	public class MenüBar extends ToolBar {

		final BPMPanel bpmPanel;
		final BeatsPerBarPanel beatsPerBarPanel;
		final ScaleGridPanel scaleGridPanel;

		MenüBar() {
			PlayAllButton playAllButton = new PlayAllButton();
			getItems().add(playAllButton);

			PauseAllButton pauseAllButton = new PauseAllButton();
			getItems().add(pauseAllButton);

			StopAllButton stopAllButton = new StopAllButton();
			getItems().add(stopAllButton);

			PlayButton playButton = new PlayButton();
			getItems().add(playButton);

			PauseButton pauseButton = new PauseButton();
			getItems().add(pauseButton);

			StopButton stopButton = new StopButton();
			getItems().add(stopButton);

			scaleGridPanel = new ScaleGridPanel();
			getItems().add(scaleGridPanel);
			
			bpmPanel = new BPMPanel();
			getItems().add(bpmPanel);

			beatsPerBarPanel = new BeatsPerBarPanel();
			getItems().add(beatsPerBarPanel);
		}

		public class PlayAllButton extends Button {

			PlayAllButton() {
				super("", new ImageView(new Image(PlayAllButton.class.getResourceAsStream("icons/Play All.png"))));
				setOnMouseClicked(this::onClick);
				visibleProperty().bind(Bindings.notEqual(player.running, new SimpleBooleanProperty(true)));
			}

			private void onClick(MouseEvent event) {
				player.play(guiContext.dataGroup());
			}

		}

		public class PauseAllButton extends Button {

			public PauseAllButton() {
				super("", new ImageView(new Image(PlayAllButton.class.getResourceAsStream("icons/Pause All.png"))));
				setOnMouseClicked(this::onClick);
				visibleProperty().bind(player.running);
			}

			private void onClick(MouseEvent event) {
				player.pause();
			}

		}

		public class StopAllButton extends Button {

			StopAllButton() {
				super("", new ImageView(new Image(PlayAllButton.class.getResourceAsStream("icons/Stop All.png"))));
				setOnMouseClicked(this::onClick);
			}

			private void onClick(MouseEvent event) {
				player.stop();
			}

		}

		public class PlayButton extends Button {

			PlayButton() {
				super("", new ImageView(new Image(PlayAllButton.class.getResourceAsStream("icons/Play.png"))));
				setOnMouseClicked(this::onClick);
			}

			private void onClick(MouseEvent event) {
				player.play(guiContext.selectedDataGroup());
			}

		}

		public class PauseButton extends Button {

			public PauseButton() {
				super("", new ImageView(new Image(PlayAllButton.class.getResourceAsStream("icons/Pause.png"))));
				setOnMouseClicked(this::onClick);
				visibleProperty().bind(player.running);
			}

			private void onClick(MouseEvent event) {
				player.pause();
			}

		}

		public class StopButton extends Button {

			StopButton() {
				super("", new ImageView(new Image(PlayAllButton.class.getResourceAsStream("icons/Stop.png"))));
				setOnMouseClicked(this::onClick);
			}

			private void onClick(MouseEvent event) {
				player.stop();
			}

		}

		public class ScaleGridPanel extends GridPane {
			
			ScaleGridPanel() {
				setAlignment(Pos.CENTER);
				setPadding(new Insets(0, 15, 0, 15));
				setHgap(5d);
				
				Label horizontalLabel = new Label("X");
				add(horizontalLabel, 0, 0);
				
				Slider horizontalSlider = new Slider(5, 100, 50);
				horizontalSlider.setMajorTickUnit(1d);
				horizontalSlider.setBlockIncrement(1d);
				horizontalSlider.valueProperty().bindBidirectional(guiContext.pixelPerBeat);
				add(horizontalSlider, 1, 0);
				
				Label verticalLabel = new Label("Y");
				add(verticalLabel, 2, 0);
				
				Slider verticalSlider = new Slider(5, 100, 50);
				verticalSlider.setMajorTickUnit(1d);
				verticalSlider.setBlockIncrement(1d);
				verticalSlider.valueProperty().bindBidirectional(guiContext.pixelHeightPerTone);
				add(verticalSlider, 3, 0);
			}
			
		}
		
		public class BPMPanel extends GridPane {

			private final TextField bpmTextField;

			BPMPanel() {
				setAlignment(Pos.CENTER);
				setPadding(new Insets(0, 15, 0, 15));
				setHgap(5d);

				Label label = new Label("BPM");
				add(label, 0, 0);

				bpmTextField = new TextField();
				bpmTextField.textProperty().bindBidirectional(guiContext.bpm);
				bpmTextField.setPrefColumnCount(3);
				add(bpmTextField, 1, 0);
			}

		}

		public class BeatsPerBarPanel extends GridPane {

			final TextField beatsTextField;
			final TextField beatTextField;

			public BeatsPerBarPanel() {
				setAlignment(Pos.CENTER);
				setPadding(new Insets(0, 15, 0, 15));
				setHgap(5d);

				Label label = new Label("Takt");
				add(label, 0, 0);

				beatsTextField = new TextField();
				beatsTextField.textProperty().bindBidirectional(guiContext.beats);
				beatsTextField.setPrefColumnCount(2);
				add(beatsTextField, 1, 0);

				Label split = new Label(" / ");
				add(split, 2, 0);

				beatTextField = new TextField();
				beatTextField.textProperty().bindBidirectional(guiContext.beat);
				beatTextField.setPrefColumnCount(2);
				add(beatTextField, 3, 0);
			}

		}
	}

	public class LeftInfoScrollPanel extends ScrollPane {

		final LeftInfoPanel leftInfoPanel;

		LeftInfoScrollPanel() {
			setPrefSize(0, guiService.stageHeightInPixel());
			setMinWidth(STAGE_INFO_WIDTH);
			setHbarPolicy(ScrollBarPolicy.ALWAYS);
			setVbarPolicy(ScrollBarPolicy.ALWAYS);

			leftInfoPanel = new LeftInfoPanel();
			setContent(leftInfoPanel);
		}

		public class LeftInfoPanel extends Pane {

			LeftInfoPanel() {
				for (int i = 0; i < NUM_OF_ROWS; i++) {
					Row row = new Row(i);
					getChildren().add(row);
				}
			}

			public class Row extends Group {

				Row(int i) {
					Rectangle rectangle = new Rectangle(0, i * guiContext.pixelHeightPerTone(), STAGE_INFO_WIDTH, guiContext.pixelHeightPerTone());
					rectangle.setFill(Color.WHITESMOKE);
					rectangle.setStroke(Color.BLACK);
					rectangle.setStrokeWidth(2d);
					getChildren().add(rectangle);

					String nameOfTone = TONE_NAMES[TONE_NAMES.length - 1 - ((C_ROW_INDEX + i + 4) % TONE_NAMES.length)];
					Label label = new Label(nameOfTone);
					label.setLayoutX(STAGE_INFO_WIDTH - 50);
					label.setLayoutY(i * guiContext.pixelHeightPerTone());
					getChildren().add(label);
				}

			}
		}
	}

	public class MainStageScrollPanel extends ScrollPane {

		final MainStageUpdateGroup mainStageUpdateGroup;

		MainStageScrollPanel() {
			setPrefSize(STAGE_WIDTH, guiService.stageHeightInPixel());
			setHbarPolicy(ScrollBarPolicy.ALWAYS);
			setVbarPolicy(ScrollBarPolicy.ALWAYS);

			mainStageUpdateGroup = new MainStageUpdateGroup();
			setContent(mainStageUpdateGroup);
		}

		public class MainStageUpdateGroup extends UpdateGroup {

			MainStagePanel mainStagePanel;

			public MainStageUpdateGroup() {
				super(guiContext.mainStageRefresh);
				onChange();
			}

			@Override
			protected void beforeChange() {
				storeScrollPosition();
			}

			private void storeScrollPosition() {
				lastMainStageScrollX = MainStageScrollPanel.this.getVvalue();
				lastMainStageScrollY = MainStageScrollPanel.this.getHvalue();
			}

			@Override
			void onChange() {
				mainStagePanel = new MainStagePanel();
				getChildren().add(mainStagePanel);
				resetScrollPosition();
			}

			private void resetScrollPosition() {
				MainStageScrollPanel.this.setVvalue(lastMainStageScrollX);
				MainStageScrollPanel.this.setHvalue(lastMainStageScrollY);
			}

			public class MainStagePanel extends Pane {

				ToneGroup toneGroup;
				final Line dragAndDropLine;

				MainStagePanel() {
					setMinSize(STAGE_WIDTH, guiService.stageHeightInPixel());

					for (int i = 0; i < NUM_OF_ROWS; i++) {
						Row row = new Row(i);
						getChildren().add(row);
					}

					for (int i = 0; i < STAGE_WIDTH / guiContext.pixelPerBeat(); i++) {
						BeatLine beatLine = new BeatLine(i);
						getChildren().add(beatLine);
					}

					DataGroup demoMelodie = Demo.DEMO;
					setDataGroup(demoMelodie);

					PlayerPosition playerPosition = new PlayerPosition();
					getChildren().add(playerPosition);

					dragAndDropLine = new Line();
					dragAndDropLine.setMouseTransparent(true);
					dragAndDropLine.setStroke(Color.RED);
					dragAndDropLine.setStrokeWidth(2d);
					dragAndDropLine.visibleProperty().bindBidirectional(guiContext.dragAndDropActive);
					dragAndDropLine.startXProperty().bind(startXProperty);
					dragAndDropLine.startYProperty().bind(startYProperty);
					dragAndDropLine.endXProperty().bind(endXProperty);
					dragAndDropLine.endYProperty().bind(endYProperty);
					getChildren().add(dragAndDropLine);
				}

				void setDataGroup(DataGroup dataGroup) {
					getChildren().remove(toneGroup);
					toneGroup = new ToneGroup(dataGroup);
					getChildren().add(toneGroup);
				}

				public class Row extends Rectangle {

					final int i;
					
					Row(int i) {
						super(0, i * guiContext.pixelHeightPerTone(), STAGE_WIDTH, guiContext.pixelHeightPerTone() - 2);
						this.i = i;
						
						setArcWidth(5);
						setArcHeight(5);
						setFill(Color.web("#5F686D"));
						setStroke(Color.web("#2D3D47"));
						setStrokeWidth(1d);
						setOnMouseEntered(event -> setFill(Color.GREEN));
						setOnMouseExited(event -> setFill(Color.web("#5F686D")));

						DragAndDrop.addTo(this);
					}
					
					public int getTone() {
						return C_ROW_INDEX - i;
					}

				}

				public class BeatLine extends Line {

					final int position;
					final boolean barLine;
					
					BeatLine(int position) {
						super(position * guiContext.pixelPerBeat(), 0, position * guiContext.pixelPerBeat(), guiService.stageHeightInPixel());
						this.position = position;
						this.barLine = position % guiContext.beats() == 0;
						
						setStroke(Color.web("#D3FFFF"));
						setStrokeWidth(barLine ? 4d : 2d);
						DragAndDrop.addTo(this);
					}
					
					public double getPosition(){
						return position;
					}

				}

				public class PlayerPosition extends Line {

					public PlayerPosition() {
						setStartY(0);
						setEndY(guiService.stageHeightInPixel());

						startXProperty().bind(player.position);
						endXProperty().bind(player.position);

						setStroke(Color.RED);
					}

				}

				public class ToneGroup extends UpdateGroup {

					final DataGroup dataGroup;

					ToneGroup(DataGroup dataGroup) {
						super(dataGroup.changeProperty);
						this.dataGroup = dataGroup;
						onChange();
					}

					@Override
					void onChange() {
						for (DataNode dataNode : dataGroup.nodesProperty) {
							if (dataNode instanceof DataGroup) {
								ToneGroup toneGroup = new ToneGroup((DataGroup) dataNode);
								getChildren().add(toneGroup);
							} else {
								Tone tone = new Tone(dataNode);
								getChildren().add(tone);
							}
						}
					}

					public class Tone extends UpdateGroup {

						final DataNode dataNode;
						final GuiDataNode guiDataNode;

						Tone(DataNode dataNode) {
							super(dataNode.changeProperty);
							this.dataNode = dataNode;
							this.guiDataNode = new GuiDataNode(dataNode, guiContext, guiService);
							onChange();
						}

						@Override
						void onChange() {
							ToneRectangel toneRectangel = new ToneRectangel();
							getChildren().add(toneRectangel);

							BeginRhytmAnchor beginRhytmAnchor = new BeginRhytmAnchor();
							getChildren().add(beginRhytmAnchor);

							EndRhytmAnchor endRhytmAnchor = new EndRhytmAnchor();
							getChildren().add(endRhytmAnchor);

							BeginToneAnchor beginToneAnchor = new BeginToneAnchor();
							getChildren().add(beginToneAnchor);

							EndToneAnchor endToneAnchor = new EndToneAnchor();
							getChildren().add(endToneAnchor);
						}
						
						public DataNode getDataNode() {
							return dataNode;
						}

						public GuiDataNode getGuiDataNode() {
							return guiDataNode;
						}

						public class ToneRectangel extends Rectangle {

							ToneRectangel() {
								super(guiDataNode.toneStartX, guiDataNode.toneStartY, guiDataNode.toneWidth, guiDataNode.toneHeight);
								setFill(Color.web("#433665"));
								setEffect(new InnerShadow());
								DragAndDrop.addTo(this);
							}
							
							public DataNode getDataNode() {
								return dataNode;
							}

						}

						public class BeginRhytmAnchor extends Line {

							final Line dependencyLine;

							BeginRhytmAnchor() {
								super(guiDataNode.beginRhythmStartX, guiDataNode.beginRhythmStartY, guiDataNode.beginRhythmEndX, guiDataNode.beginRhythmEndY);

								setFill(Color.BLACK);
								setStrokeWidth(2d);

								DragAndDrop.addTo(this);

								dependencyLine = new Line(getEndX(), getEndY(), getEndX(), getEndY());
								if (dataNode.hasPositionDependency()) {
									
									GuiDataNode guiPositionDependency = new GuiDataNode(dataNode.positionDependency(), guiContext, guiService);
									
									dependencyLine.setEndX(guiPositionDependency.endRhythmStartX);
									dependencyLine.setEndY(guiPositionDependency.endRhythmEndY);
								}

								getChildren().add(dependencyLine);

							}

						}

						public class EndRhytmAnchor extends Line {

							EndRhytmAnchor() {
								super(guiDataNode.endRhythmStartX, guiDataNode.endRhythmStartY, guiDataNode.endRhythmEndX, guiDataNode.endRhythmEndY);

								setFill(Color.BLACK);
								setStrokeWidth(2d);

								DragAndDrop.addTo(this);
							}

						}

						public class BeginToneAnchor extends Polygon {

							final Line dependencyLine;

							BeginToneAnchor() {
								super(guiDataNode.beginToneAX, guiDataNode.beginToneAY, guiDataNode.beginToneBX,
										guiDataNode.beginToneBY, guiDataNode.beginToneCX, guiDataNode.beginToneCY);

								setFill(Color.web("#433665"));
								setEffect(new InnerShadow());

								DragAndDrop.addTo(this);
								
								dependencyLine = new Line(guiDataNode.beginToneCX, guiDataNode.beginToneCY, guiDataNode.beginToneCX, guiDataNode.beginToneCY);
								if(dataNode.hasToneDependency()){
									
									GuiDataNode guiToneDependency = new GuiDataNode(dataNode.toneDependency(), guiContext, guiService);
									
									dependencyLine.setEndX(guiToneDependency.endToneCX);
									dependencyLine.setEndY(guiToneDependency.endToneCY);
								}
								getChildren().add(dependencyLine);
							}

						}

						public class EndToneAnchor extends Polygon {

							EndToneAnchor() {
								super(guiDataNode.endToneAX, guiDataNode.endToneAY, guiDataNode.endToneBX, guiDataNode.endToneBY, guiDataNode.endToneCX, guiDataNode.endToneCY);

								setFill(Color.web("#433665"));
								setEffect(new InnerShadow());

								DragAndDrop.addTo(this);
							}

						}
					}
				}
			}
		}
	}

	public class RhytmScrollPanel extends ScrollPane {

		RhytmScrollPanel() {
			setPrefSize(STAGE_WIDTH, 0);
			setMinHeight(RHYTM_BOX_HEIGHT);
			setHbarPolicy(ScrollBarPolicy.ALWAYS);
			setVbarPolicy(ScrollBarPolicy.NEVER);
			setPadding(new Insets(0, 0, 0, STAGE_INFO_WIDTH));

			RhytmPanelUpdateGroup rhytmPanelUpdateGroup = new RhytmPanelUpdateGroup();
			setContent(rhytmPanelUpdateGroup);
		}

		public class RhytmPanelUpdateGroup extends UpdateGroup {

			RhytmPanel rhytmPanel;

			public RhytmPanelUpdateGroup() {
				super(new PropertyChangeGroup());
				onChange();
			}

			@Override
			void onChange() {
				rhytmPanel = new RhytmPanel();
				getChildren().add(rhytmPanel);
			}

			public class RhytmPanel extends Pane {

				RhytmPanel() {
					Rectangle rhytmnBox = new Rectangle(0, 75, STAGE_WIDTH, 25);
					getChildren().add(rhytmnBox);

					DataGroup dataGroup = Demo.DEMO;
					RhytmGroup rhytmGroup = new RhytmGroup(dataGroup);
					getChildren().add(rhytmGroup);
				}

				public class RhytmGroup extends UpdateGroup {

					final DataGroup dataGroup;

					RhytmGroup(DataGroup dataGroup) {
						super(dataGroup.nodesProperty);
						this.dataGroup = dataGroup;
						onChange();
					}

					@Override
					void onChange() {
						for (DataNode dataNode : dataGroup.nodesProperty) {
							if (dataNode instanceof DataGroup) {
								RhytmGroup rhytmGroup = new RhytmGroup((DataGroup) dataNode);
								getChildren().add(rhytmGroup);
							} else {
								RhytmAnchor rhytmAnchor = new RhytmAnchor(dataNode);
								getChildren().add(rhytmAnchor);
							}
						}
					}

					public class RhytmAnchor extends UpdateGroup {

						final DataNode dataNode;

						RhytmAnchor(DataNode dataNode) {
							super(dataNode.changeProperty);
							this.dataNode = dataNode;
							onChange();
						}

						private double calculatePosition() {
							return dataNode.calculateGlobalPosition() * guiContext.pixelPerBeat();
						}

						@Override
						void onChange() {
							Polygon polygon = new Polygon(new double[] { calculatePosition(), 60, calculatePosition() + 20, 80, calculatePosition() - 20, 80 });
							polygon.setFill(Color.web("#433665"));
							polygon.setEffect(new InnerShadow());
							getChildren().add(polygon);

							Line line = new Line(calculatePosition(), 0, calculatePosition(), RHYTM_BOX_HEIGHT);
							getChildren().add(line);
						}
					}
				}
			}
		}
	}
}
