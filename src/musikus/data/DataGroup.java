package musikus.data;

import java.util.Set;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

public class DataGroup extends DataNode {

	public final SetProperty<DataNode> nodesProperty = new SimpleSetProperty<DataNode>(FXCollections.observableSet());
	final String name;
	
	public DataGroup(String name) {
		super();
		this.name = name;
	}
	
	Set<DataNode> nodes() {
		return nodesProperty.get();
	}

	void add(DataNode node) {
		if (node.group != null)
			node.group.nodes().remove(node);

		nodes().add(node);
		node.group = group;
	}
}
