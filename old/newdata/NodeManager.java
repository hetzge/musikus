package newdata;

import java.util.HashMap;
import java.util.Map;

public class NodeManager {

	public final static NodeManager INSTANCE = new NodeManager();

	private final Map<String, Node<?>> nodesByUUID = new HashMap<>();
	
	public <TYPE> void registerNode(Node<TYPE> node){
		if(node == null) throw new IllegalArgumentException("Parameter node is null");
		nodesByUUID.put(node.id, node);
	}
	
	public <TYPE> Node<TYPE> getNode(String uuid){
		@SuppressWarnings("unchecked")
		Node<TYPE> node = (Node<TYPE>) nodesByUUID.get(uuid);
		if(node == null) throw new IllegalStateException("NodeManger contains no node with UUID " + uuid);
		return node;
	}
	
}
