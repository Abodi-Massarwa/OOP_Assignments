package ex0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph_Algo implements graph_algorithms {
	
	graph m_graph = new Graph_DS();

	@Override
	public void init(graph g) {
		for(node_data n: g.getV())
			m_graph.addNode(n);
	}

	@Override
	public graph copy() {
		graph deepCopy = new Graph_DS();
		
		HashMap<node_data, node_data> nodeToNodeCopyMapper = new HashMap<node_data, node_data>();
		for(node_data n: m_graph.getV())
			nodeToNodeCopyMapper.put(n, new NodeData());
		
		for(node_data n: m_graph.getV()) {
			node_data nCopy = nodeToNodeCopyMapper.get(n);
			deepCopy.addNode(nCopy);
			
			for(node_data m: n.getNi())
				nCopy.addNi(nodeToNodeCopyMapper.get(m));
		}
		
		return deepCopy;
	}

	@Override
	public boolean isConnected() {
		int count = 0;
		
		ArrayList<node_data> nodes = new ArrayList<node_data>();
		
		// Making all nodes white (in case they were black before)
		for(node_data n: m_graph.getV()) {
			if(nodes.isEmpty())
				nodes.add(n);
			n.setInfo(NodeData.white);
		}
		
		while(!nodes.isEmpty()) {
			node_data n = nodes.remove(0);
			
			if(n.getInfo().equals(NodeData.white)) {
				++count;
				n.setInfo(NodeData.gray);
			}
			
			if(n.getInfo().equals(NodeData.gray)) {
				for(node_data neighbor: n.getNi()) {
					if(neighbor.getInfo().equals(NodeData.white)) {
						++count;
						neighbor.setInfo(NodeData.gray);
						nodes.add(neighbor);
					}
				}
				
				n.setInfo(NodeData.black);
			}
		}
		
		return m_graph.getV().size() == count;
	}
	
	

	@Override
	public int shortestPathDist(int src, int dest) {
		node_data srcNode = NodeData.getNodeByKey(src), destNode = NodeData.getNodeByKey(dest);
		
		HashMap<node_data, node_data> prev = new HashMap<node_data, node_data>();
		HashMap<node_data, Integer> dist = new HashMap<node_data, Integer>();
		
		dist.put(destNode, 0);
		
		for(node_data n: m_graph.getV()) {
			prev.put(n, null);
			dist.putIfAbsent(n, -1);
		}
		
		ArrayList<node_data> nodes =new ArrayList<node_data>();
		
		nodes.add(destNode);
		while(!nodes.isEmpty()) {
			node_data n = nodes.remove(0);
			
			for(node_data neighbor: n.getNi()) {
				if(dist.get(neighbor) == -1 || dist.get(neighbor) + 1 > dist.get(n)) {
					dist.computeIfPresent(neighbor, (k, v) -> v = dist.get(n) + 1);
					prev.computeIfPresent(neighbor, (k, v) -> v = n);
					
					int srcPath = dist.get(srcNode);
					
					if(srcPath != -1 || srcPath > dist.get(neighbor))
						nodes.add(neighbor);
				}
			}
		}
		
		return dist.get(srcNode);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data srcNode = NodeData.getNodeByKey(src), destNode = NodeData.getNodeByKey(dest);
		
		HashMap<node_data, node_data> prev = new HashMap<node_data, node_data>();
		HashMap<node_data, Integer> dist = new HashMap<node_data, Integer>();
		
		dist.put(destNode, 0);
		
		for(node_data n: m_graph.getV()) {
			prev.put(n, null);
			dist.putIfAbsent(n, -1);
		}
		
		ArrayList<node_data> nodes =new ArrayList<node_data>();
		
		nodes.add(destNode);
		while(!nodes.isEmpty()) {
			node_data n = nodes.remove(0);
			
			for(node_data neighbor: n.getNi()) {
				if(dist.get(neighbor) == -1 || dist.get(neighbor) + 1 > dist.get(n)) {
					dist.computeIfPresent(neighbor, (k, v) -> v = dist.get(n) + 1);
					prev.computeIfPresent(neighbor, (k, v) -> v = n);
					
					int srcPath = dist.get(srcNode);
					
					if(srcPath != -1 || srcPath > dist.get(neighbor))
						nodes.add(neighbor);
				}
			}
		}
		
		if(dist.get(srcNode) == -1)
			return null;
		
		List<node_data> ret = new ArrayList<node_data>();
		
		while(srcNode != destNode) {
			ret.add(srcNode);
			srcNode = prev.get(srcNode);
		}
		
		ret.add(srcNode);
		
		return ret;
	}

}
