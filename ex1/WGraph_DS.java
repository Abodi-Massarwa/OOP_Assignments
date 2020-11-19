package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WGraph_DS implements weighted_graph{
	
	private Map<Integer, node_info> m_nodes = new HashMap<Integer, node_info>();
	private Map<node_info, Map<node_info, Double>> m_weights = new HashMap<node_info, Map<node_info, Double>>();
	private int m_edges = 0;
	
	private int m_mc = 0;
	
	private void thisHasChanged() {
		++m_mc;
	}

	@Override
	public node_info getNode(int key) {
		return m_nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		node_info nodeInfo1 = getNode(node1), nodeInfo2 = getNode(node2);
		if(nodeInfo1 == null)
			return false;
		Map<node_info, Double> neighborsOfNode1 = m_weights.get(nodeInfo1);
		return neighborsOfNode1 == null? false:
			neighborsOfNode1.get(nodeInfo2) != null;
	}

	@Override
	public double getEdge(int node1, int node2) {
		node_info nodeInfo1 = getNode(node1), nodeInfo2 = getNode(node2);
		if(nodeInfo1 == null || nodeInfo2 == null)
			return -1;
		Double weight = m_weights.get(nodeInfo1).get(nodeInfo2);
		if(weight != null)
			return weight.doubleValue();
		
		return -1;
	}

	@Override
	public void addNode(int key) {
		if(key == NodeInfo.getNextKey()) new NodeInfo();
		 
		
		node_info node = NodeInfo.getNode(key);
		
		if(m_nodes.get(key) != null)
			thisHasChanged();
		
		m_nodes.put(key, node);
		m_weights.putIfAbsent(node, new HashMap<node_info, Double>());
	}

	@Override
	public void connect(int node1, int node2, double w) {
		addNode(node1);
		addNode(node2);
		
		node_info nodeInfo1 = getNode(node1), nodeInfo2 = getNode(node2);
		
		Double weight = m_weights.get(nodeInfo1).get(nodeInfo2);
		
		if(weight != null && weight.doubleValue() == w)
			return;
		
		m_weights.get(nodeInfo1).put(nodeInfo2, w);
		m_weights.get(nodeInfo2).put(nodeInfo1, w);
		
		thisHasChanged();
		
		++m_edges;
	}

	@Override
	public Collection<node_info> getV() {
		return m_nodes.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		node_info node = getNode(node_id);
		return node != null? m_weights.get(node).keySet():null;
	}

	@Override
	public node_info removeNode(int key) {
		node_info nodeToRemove = getNode(key);
		
		if(nodeToRemove != null) {
			Collection<node_info> neighborsOfNode = getV(key);
			m_edges -= neighborsOfNode.size();
			
			m_nodes.remove(key);
			m_weights.remove(nodeToRemove);
			
			for(node_info neighbor: neighborsOfNode) {
				m_weights.get(neighbor).remove(nodeToRemove);
			}
			
			
			thisHasChanged();
		}
		
		return nodeToRemove;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		node_info nodeInfo1 = getNode(node1), nodeInfo2 = getNode(node2);
		
		if(nodeInfo1 != null && nodeInfo2 != null) {
			m_weights.get(nodeInfo1).remove(nodeInfo2);
			m_weights.get(nodeInfo2).remove(nodeInfo1);
			
			--m_edges;
			thisHasChanged();
		}
	}

	@Override
	public int nodeSize() {
		return m_nodes.size();
	}

	@Override
	public int edgeSize() {
		return m_edges;
	}

	@Override
	public int getMC() {
		return m_mc;
	}

}
