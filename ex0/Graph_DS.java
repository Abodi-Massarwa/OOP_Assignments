package ex0;

import java.util.Collection;
import java.util.HashSet;

public class Graph_DS implements graph {
	
	
	private HashSet<node_data> m_vertices = new HashSet<node_data>();
	private int m_modeCount = 0;
	
	private void innerStateHasChanged() {
		++m_modeCount;
	}

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
		m_vertices.add(n);
		
		innerStateHasChanged();
	}

	@Override
	public void connect(int node1, int node2) {
		node_data node_data1 = NodeData.getNodeByKey(node1);
		node_data node_data2 = NodeData.getNodeByKey(node2);
		
		if(node_data1 == null || node_data2 == null || node_data1 == node_data2)
			return;
		
		node_data1.addNi(node_data2);
		node_data2.addNi(node_data1);
		
		innerStateHasChanged();
	}

	@Override
	public Collection<node_data> getV() {
		return m_vertices;
	}

	@Override
	public Collection<node_data> getV(int node_id) {
		node_data node = NodeData.getNodeByKey(node_id);
		return m_vertices.contains(node)? node.getNi(): null;
	}

	@Override
	public node_data removeNode(int key) {
		node_data node = NodeData.getNodeByKey(key);
		m_vertices.remove(node);
		
		for(node_data vertex: m_vertices) {
			vertex.removeNode(node);
		}
		
		innerStateHasChanged();
		
		return node;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		node_data node_data1 = NodeData.getNodeByKey(node1);
		node_data node_data2 = NodeData.getNodeByKey(node2);
		
		if(node_data1 == null || node_data2 == null || node_data1 == node_data2)
			return;
		
		node_data1.removeNode(node_data2);
		node_data2.removeNode(node_data1);
		
		innerStateHasChanged();
	}

	@Override
	public int nodeSize() {
		return m_vertices.size();
	}

	@Override
	public int edgeSize() {
		int count = 0;
		
		for(node_data vertex: m_vertices) {
			count += vertex.getNi().size();
		}
		
		return count/2;
	}

	@Override
	public int getMC() {
		return m_modeCount;
	}

}
