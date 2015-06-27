package prototyp2;

import musikus.gui.Gui;
import musikus.gui.GuiAPI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(new GuiAPI().gui ,1024,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Musikus");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
