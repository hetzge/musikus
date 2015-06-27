package musikus.data;

import java.util.Set;

public final class Demo {

	public static final DataGroup DEMO = createDemoMelodie();
	
	private Demo() {
	}

	public static DataGroup createDemoMelodie() {

		int i = 0;
		DataNode[] nodes = new DataNode[11];

		DataGroup group = new DataGroup("Melodie 1");
		nodes[i] = new DataNode(group, 0, 1, 0);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 2);
		nodes[i++] = new DataNode(group, 1, 1, 2);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 1);
		nodes[i++] = new DataNode(group, 1, 1, 1);

		for (int j = 1; j < i; j++) {
			new PositionRelation(group, nodes[j], nodes[j - 1]);
			new ToneRelation(group, nodes[j], nodes[j - 1]);
		}
//		new PositionRelation(group, nodes[nodes.length - 1], nodes[0]);
//		new ToneRelation(group, nodes[nodes.length - 1], nodes[0]);

		return group;
	}

	public static DataGroup createDemoMelodie2() {
		DataGroup group = new DataGroup("Melodie 2");
		new DataNode(group, 2, 3, 0.5d);
		new DataNode(group, 2, 3, 2.5d);

		return group;
	}
	
	public static void main(String[] args) {
		DataGroup demoMelodie = createDemoMelodie();
		Set<DataNode> nodes = demoMelodie.nodes();
		for (DataNode dataNode : nodes) {
			System.out.println(dataNode.calculateGlobalPosition());
		}
	}

}
