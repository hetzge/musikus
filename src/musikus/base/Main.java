package musikus.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import musikus.gui.Gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Configuration
@ComponentScan
public class Main extends Application {
	
	private final static ApplicationContext context;

	@Override
	public void start(Stage primaryStage) {
		try {
			Gui gui = getBean(Gui.class);
			
			Scene scene = new Scene(gui ,1024,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Musikus");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static {
		context = new AnnotationConfigApplicationContext("musikus");
	}
	
	public static <T> T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
