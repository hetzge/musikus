package application;

import javafx.scene.Node;

public interface Guiable {

	/**
	 * information panel
	 */
	public Node detailsGui();
	
	/**
	 * showable in every size
	 */
	public Node scaleableElement();
	
	/**
	 * icon or preview
	 */
	public Node graphicalRepräsentation();
	
}
