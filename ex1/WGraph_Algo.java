package ex1;

import java.util.ArrayList;
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
		node_info srcNode = m_weightedGraph.getNode(src), destNode = m_weightedGraph.getNode(dest);

		HashMap<node_info, Double> dist = new HashMap<node_info, Double>();

		for (node_info n : m_weightedGraph.getV()) {
			dist.put(n, -1.0);
		}

		dist.put(destNode, 0.0);

		TreeMap<Double, node_info> q = new TreeMap<Double, node_info>();
		q.put(0.0, destNode);

		while (!q.isEmpty()) {
			Double closest = q.firstKey();
			node_info u = q.get(closest);
			
			q.remove(closest);

			if(dist.get(srcNode) != -1) 
				break;

			for (node_info v : m_weightedGraph.getV(u.getKey())) {
				double alt = dist.get(u) + m_weightedGraph.getEdge(v.getKey(), u.getKey());
				if (dist.get(v) == -1.0 || alt < dist.get(v)) {
					dist.put(v, alt);

					q.put(alt, v);
				}
			}
		}

		return dist.get(srcNode);
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		node_info srcNode = m_weightedGraph.getNode(src), destNode = m_weightedGraph.getNode(dest);

		HashMap<node_info, node_info> prev = new HashMap<node_info, node_info>();
		HashMap<node_info, Double> dist = new HashMap<node_info, Double>();

		for (node_info n : m_weightedGraph.getV()) {
			prev.put(n, null);
			dist.put(n, -1.0);
		}

		dist.put(destNode, 0.0);
		prev.put(destNode, null);

		TreeMap<Double, node_info> q = new TreeMap<Double, node_info>();
		q.put(0.0, destNode);

		while (!q.isEmpty()) {
			Double closest = q.firstKey();
			node_info u = q.get(closest);
			
			q.remove(closest);

			if(dist.get(srcNode) != -1) 
				break;

			for (node_info v : m_weightedGraph.getV(u.getKey())) {
				double alt = dist.get(u) + m_weightedGraph.getEdge(v.getKey(), u.getKey());
				if (dist.get(v) == -1.0 || alt < dist.get(v)) {
					dist.put(v, alt);
					prev.put(v, u);

					q.put(alt, v);
				}
			}
		}

		if (dist.get(srcNode) == -1)
			return null;

		List<node_info> ret = new ArrayList<node_info>();

		while (srcNode != destNode) {
			ret.add(srcNode);
			srcNode = prev.get(srcNode);
		}

		ret.add(srcNode);

		return ret;
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
