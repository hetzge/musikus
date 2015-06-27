package application;
	
import java.awt.geom.GeneralPath;

import application.entity.AccordEntity;
import application.entity.AccordProgressionEntity;
import application.entity.AkzentPatternEntity;
import application.entity.EntityType;
import application.entity.GeneratorEntity;
import application.entity.MelodieEntity;
import application.entity.PatternEntity;
import application.entity.ScaleEntity;
import application.entity.SongEntity;
import application.entity.SongPartEntity;
import application.entity.ToneEntity;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(new application.Application() ,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
