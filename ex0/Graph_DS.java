package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Graph_DS implements graph {
	
	
	private HashSet<node_data> m_edges = new HashSet<node_data>();

	@Override
	public node_data getNode(int key) {
		// TODO Auto-generated method stub
		return NodeData.getNodeByKey(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		return NodeData.getNodeByKey(node1).hasNi(node2);
	}

	@Override
	public void addNode(node_data n) {
		m_edges.add(n);
	}

	@Override
	public void connect(int node1, int node2) {
		node_data node_data1 = NodeData.getNodeByKey(node1);
		node_data node_data2 = NodeData.getNodeByKey(node2);
		
		if(node_data1 == null || node_data2 == null || node_data1 == node_data2)
			return;
		
		node_data1.addNi(node_data2);
		node_data2.addNi(node_data1);
	}

	@Override
	public Collection<node_data> getV() {
		return m_edges;
	}

	@Override
	public Collection<node_data> getV(int node_id) {
		node_data node = NodeData.getNodeByKey(node_id);
		return m_edges.contains(node)? node.getNi(): null;
	}

	@Override
	public node_data removeNode(int key) {
		node_data node = NodeData.getNodeByKey(key);
		m_edges.remove(node);
		
		for(node_data vertices: m_edges) {
			vertices.removeNode(node);
		}
		
		return node;
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
