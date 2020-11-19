package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

public class WGraph_Algo implements weighted_graph_algorithms {

	private weighted_graph m_weightedGraph;
	
	@Override
	public void init(weighted_graph g) {
		m_weightedGraph = g;
	}

	@Override
	public weighted_graph getGraph() {
		return m_weightedGraph;
	}

	@Override
	public weighted_graph copy() {
		weighted_graph deepCopy = new WGraph_DS();

		TreeMap<node_info, node_info> nodeToNodeCopyMapper = new TreeMap<node_info, node_info>();
		for (node_info n : m_weightedGraph.getV())
			nodeToNodeCopyMapper.put(n, new NodeInfo());

		for (node_info n : m_weightedGraph.getV()) {
			node_info nCopy = nodeToNodeCopyMapper.get(n);
			deepCopy.addNode(nCopy.getKey());

			for (node_info neighbor : m_weightedGraph.getV(n.getKey()))
				// nCopy.addNi(nodeToNodeCopyMapper.get(neighbor));
				deepCopy.connect(nCopy.getKey(), nodeToNodeCopyMapper.get(neighbor).getKey()
						, m_weightedGraph.getEdge(n.getKey(), neighbor.getKey()));
		}

		return deepCopy;
	}

	@Override
	public boolean isConnected() {
		int count = 0;
		
		int vertices = m_weightedGraph.nodeSize(), edges = m_weightedGraph.edgeSize();
		if(vertices <= 1 || (vertices-1)*(vertices-2)/2 < edges)
			return true;
		
		Queue<node_info> nodes = new LinkedList<node_info>();
		node_info source = null;

		// Making all nodes white (in case they were black before)
		for (node_info n : m_weightedGraph.getV()) {
			n.setTag(NodeInfo.white);
			source = n;
		}
		
		nodes.add(source);

		while (!nodes.isEmpty()) {
			node_info n = nodes.poll();

			if (n.getTag() == NodeInfo.white) {
				++count;
				n.setTag(NodeInfo.gray);
			}

			if (n.getTag() == NodeInfo.gray) {
				for (node_info neighbor : m_weightedGraph.getV(n.getKey())) {
					if (neighbor.getTag() == NodeInfo.white) {
						++count;
						neighbor.setTag(NodeInfo.gray);
						nodes.add(neighbor);
					}
				}

				n.setTag(NodeInfo.black);
			}
		}

		return m_weightedGraph.getV().size() == count;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		return 0;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

}