package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Graph_DS implements graph {
	
	
	private HashMap<node_data, HashSet<node_data>> m_edges = new HashMap<node_data, HashSet<node_data>>();

	@Override
	public node_data getNode(int key) {
		// TODO Auto-generated method stub
		return NodeData.getNodeByKey(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		return m_edges.get(NodeData.getNodeByKey(node1)).contains(NodeData.getNodeByKey(node2));
	}

	@Override
	public void addNode(node_data n) {
		m_edges.put(n, new HashSet<node_data>());
	}

	@Override
	public void connect(int node1, int node2) {
		// A guard in case the user didn't add the node (Another option is to throw an error)
		m_edges.putIfAbsent(NodeData.getNodeByKey(node1), new HashSet<node_data>());
		
		m_edges.computeIfPresent(NodeData.getNodeByKey(node1), 
				(k,v) -> v.add(NodeData.getNodeByKey(node2))?v:v //Returned v because add returns a boolean value
			);
	}

	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<node_data> getV(int node_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public node_data removeNode(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return 0;
	}

}
