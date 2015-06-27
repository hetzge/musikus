package application;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public final class Utils {

	private Utils() {
	}
	
	public static  Class<?> getClass(Object o){
		if(o.getClass().getName().contains("$")){
			return o.getClass().getSuperclass();
		} 
		return o.getClass();
	}
	
	public static void setupBorderPaneAnchors(Node node){
		AnchorPane.setLeftAnchor(node, 0d);
		AnchorPane.setRightAnchor(node, 0d);
		AnchorPane.setTopAnchor(node, 0d);
		AnchorPane.setBottomAnchor(node, 0d);
	}
	
	
}
