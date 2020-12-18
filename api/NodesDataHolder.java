package api;

import java.util.ArrayList;

public class NodesDataHolder {
	private static ArrayList<node_data> s_allNdoes = new ArrayList<node_data>();
	private static int s_keyGenerator = 0;
	
	public static final int WHITE = 0, GRAY = 1, BLACK = 2; 
	
	//  Package modifier (default)
	static int addNode_GetKey(node_data nodeData) {
		s_allNdoes.add(nodeData);
		return s_keyGenerator++;
	}
	
	static node_data getNodeByKey(int key) {
		return s_allNdoes.get(key);
	}
}