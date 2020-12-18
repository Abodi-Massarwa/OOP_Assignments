package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
	private HashMap<Integer, node_data> m_vertices = new HashMap<Integer, node_data>();
	private HashMap<node_data, HashMap<node_data, edge_data>> m_edges = new HashMap<node_data, HashMap<node_data, edge_data>>();
	private HashMap<node_data, ArrayList<node_data>> m_neighborsOfNode = new HashMap<node_data, ArrayList<node_data>>();
	private int m_edgesNumber = 0;
	private int m_mc = 0;

	@Override
	public node_data getNode(int key) {
		return m_vertices.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return m_edges.get(NodesDataHolder.getNodeByKey(src)).get(NodesDataHolder.getNodeByKey(dest));
	}

	@Override
	public void addNode(node_data n) {
		graphChanged();
		
		m_vertices.put(n.getKey(), n);
		
		m_edges.put(n, new HashMap<node_data, edge_data>());
		m_neighborsOfNode.put(n, new ArrayList<node_data>());
	}

	@Override
	public void connect(int src, int dest, double w) {
		graphChanged();
		
		node_data source = NodesDataHolder.getNodeByKey(src),
				destination = NodesDataHolder.getNodeByKey(dest);
		edge_data edge = new EdgeData(src, dest, w);
		
		++m_edgesNumber;
		m_edges.get(source).put(destination, edge);
		m_neighborsOfNode.get(destination).add(source);
	}

	@Override
	public Collection<node_data> getV() {
		return m_vertices.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return m_edges.get(NodesDataHolder.getNodeByKey(node_id)).values();
	}

	@Override
	public node_data removeNode(int key) {
		graphChanged();
		
		node_data node = NodesDataHolder.getNodeByKey(key);
		m_edgesNumber -= m_edges.get(node).size() + m_neighborsOfNode.get(node).size();
		
		m_vertices.remove(node.getKey());
		m_edges.remove(node);
		
		node_data dst = node;
		
		for (node_data src: m_neighborsOfNode.get(node)) {
			m_edges.get(src).remove(dst);
		}
		m_neighborsOfNode.remove(dst);
		
		
		return node;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		graphChanged();
		
		node_data source = NodesDataHolder.getNodeByKey(src),
				destination = NodesDataHolder.getNodeByKey(dest);
		
		return m_edges.get(source).remove(destination);
	}

	@Override
	public int nodeSize() {
		return m_vertices.size();
	}

	@Override
	public int edgeSize() {
		return m_edgesNumber;
	}
	
	private void graphChanged() {
		++m_mc;;
	}

	@Override
	public int getMC() {
		return m_mc;
	}

}