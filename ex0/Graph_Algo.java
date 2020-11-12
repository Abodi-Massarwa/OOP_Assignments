package ex0;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

public class Graph_Algo implements graph_algorithms {

	graph m_graph = new Graph_DS();

	@Override
	public void init(graph g) {
		m_graph = g;
	}

	@Override
	public graph copy() {
		graph deepCopy = new Graph_DS();

		TreeMap<node_data, node_data> nodeToNodeCopyMapper = new TreeMap<node_data, node_data>();
		for (node_data n : m_graph.getV())
			nodeToNodeCopyMapper.put(n, new NodeData());

		for (node_data n : m_graph.getV()) {
			node_data nCopy = nodeToNodeCopyMapper.get(n);
			deepCopy.addNode(nCopy);

			for (node_data neighbor : n.getNi())
				nCopy.addNi(nodeToNodeCopyMapper.get(neighbor));
		}

		return deepCopy;
	}

	class BFS {
		public TreeMap<node_data, Integer> m_dist = new TreeMap<>();
		public TreeMap<node_data, node_data> m_prev = new TreeMap<>();

		/*
		 * public void execute(Graph_DS g) { node_data source = g.sourceNode();
		 * this.execute(g, source); }
		 */

		public void execute(Graph_DS g, node_data source) {
			for (node_data v : g.getV()) {
				v.setTag(NodeData.white);
				m_dist.put(v, -1);
				m_prev.put(v, null);
			}

			source.setTag(NodeData.gray);
			m_dist.put(source, 0);

			Queue<node_data> q = new LinkedList<node_data>();
			q.add(source);

			while (!q.isEmpty()) {
				node_data u = q.poll();

				for (node_data v : u.getNi()) {
					if (v.getTag() == NodeData.white) {
						v.setTag(NodeData.gray);
						m_dist.put(v, m_dist.get(u) + 1);
						m_prev.put(v, u);

						q.add(v);
					}
				}

				u.setTag(NodeData.black);
			}
		}
	}

	@Override
	public boolean isConnected() {
		int count = 0;

		Queue<node_data> nodes = new LinkedList<node_data>();

		// Making all nodes white (in case they were black before)
		for (node_data n : m_graph.getV()) {
			if (nodes.isEmpty())
				nodes.add(n);
			n.setTag(NodeData.white);
		}

		while (!nodes.isEmpty()) {
			node_data n = nodes.poll();

			if (n.getTag() == NodeData.white) {
				++count;
				n.setTag(NodeData.gray);
			}

			if (n.getTag() == NodeData.gray) {
				for (node_data neighbor : n.getNi()) {
					if (neighbor.getTag() == NodeData.white) {
						++count;
						neighbor.setTag(NodeData.gray);
						nodes.add(neighbor);
					}
				}

				n.setTag(NodeData.black);
			}
		}

		return m_graph.getV().size() == count;
	}

	@Override
	public int shortestPathDist(int src, int dest) {
		node_data srcNode = m_graph.getNode(src), destNode = m_graph.getNode(dest);

		TreeMap<node_data, Integer> dist = new TreeMap<node_data, Integer>();

		for (node_data n : m_graph.getV()) {
			dist.put(n, -1);
			n.setTag(NodeData.white);
		}

		dist.put(destNode, 0);
		destNode.setTag(NodeData.gray);

		Queue<node_data> q = new LinkedList<node_data>();
		q.add(destNode);

		while (!q.isEmpty()) {
			node_data u = q.poll();

			if(dist.get(srcNode) != -1) 
				break;

			for (node_data v : u.getNi()) {
				if (v.getTag() == NodeData.white) {
					v.setTag(NodeData.gray);
					dist.put(v, dist.get(u) + 1);

					q.add(v);
				}
			}

			u.setTag(NodeData.black);
		}

		return dist.get(srcNode);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data srcNode = m_graph.getNode(src), destNode = m_graph.getNode(dest);

		TreeMap<node_data, node_data> prev = new TreeMap<node_data, node_data>();
		TreeMap<node_data, Integer> dist = new TreeMap<node_data, Integer>();

		for (node_data n : m_graph.getV()) {
			prev.put(n, null);
			dist.put(n, -1);
			n.setTag(NodeData.white);
		}

		dist.put(destNode, 0);
		prev.put(destNode, null);
		destNode.setTag(NodeData.gray);

		Queue<node_data> q = new LinkedList<node_data>();
		q.add(destNode);

		while (!q.isEmpty()) {
			node_data u = q.poll();

			if(dist.get(srcNode) != -1) 
				break;

			for (node_data v : u.getNi()) {
				if (v.getTag() == NodeData.white) {
					v.setTag(NodeData.gray);
					dist.put(v, dist.get(u) + 1);
					prev.put(v, u);

					q.add(v);
				}
			}

			u.setTag(NodeData.black);
		}

		if (dist.get(srcNode) == -1)
			return null;

		List<node_data> ret = new ArrayList<node_data>();

		while (srcNode != destNode) {
			ret.add(srcNode);
			srcNode = prev.get(srcNode);
		}

		ret.add(srcNode);

		return ret;
	}

}
