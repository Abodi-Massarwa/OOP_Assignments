package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graph_DS implements graph {
	
	
	private Map<Integer, node_data> m_vertices = new TreeMap<Integer, node_data>();
	private int m_modeCount = 0;
	private int m_edges = 0;
	
	private void innerStateHasChanged() {
		++m_modeCount;
	}
	
	/*
	 * public node_data sourceNode() { return m_vertices.firstEntry().getValue(); }
	 */
	
	public node_data getNode(int key) {
		return m_vertices.get(key);
	}


	@Override
	public boolean hasEdge(int node1, int node2) {
		return getNode(node1).hasNi(node2);
	}

	@Override
	public void addNode(node_data n) {
		m_vertices.put(n.getKey(), n);
		
		innerStateHasChanged();
	}

	@Override
	public void connect(int node1, int node2) {
		node_data node_data1 = getNode(node1);
		node_data node_data2 = getNode(node2);
		
		if(node_data1 == null || node_data2 == null || 
				node_data1 == node_data2 || node_data1.hasNi(node_data2.getKey()))
			return;
		
		node_data1.addNi(node_data2);
		node_data2.addNi(node_data1);
		
		++m_edges;
		
		innerStateHasChanged();
	}

	@Override
	public Collection<node_data> getV() {
		return m_vertices.values();
	}

	@Override
	public Collection<node_data> getV(int node_id) {
		return m_vertices.containsKey((Integer) node_id)? m_vertices.get(node_id).getNi(): null;
	}

	@Override
	public node_data removeNode(int key) {
		node_data node = getNode(key);
		if(node == null) return null;
		
		m_vertices.remove(key);
		m_edges -= node.getNi().size();
		
		for(node_data vertex: m_vertices.values()) {
			vertex.removeNode(node);
		}
		
		innerStateHasChanged();
		
		return node;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		node_data node_data1 = getNode(node1);
		node_data node_data2 = getNode(node2);
		
		if(node_data1 == null || node_data2 == null || 
				node_data1 == node_data2 || !(node_data1.hasNi(node_data2.getKey())))
			return;
		
		node_data1.removeNode(node_data2);
		node_data2.removeNode(node_data1);
		
		--m_edges;
		
		innerStateHasChanged();
	}

	@Override
	public int nodeSize() {
		return m_vertices.size();
	}

	@Override
	public int edgeSize() {
		return m_edges;
	}

	@Override
	public int getMC() {
		return m_modeCount;
	}

}