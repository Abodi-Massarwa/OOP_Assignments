package ex0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

		public void execute(Graph_DS g) {
			node_data source = g.sourceNode();
			this.execute(g, source);
		}

		public void execute(Graph_DS g, node_data source) {
			for (node_data v : g.getV()) {
				v.setInfo(NodeData.white);
				m_dist.put(v, -1);
				m_prev.put(v, null);
			}

			source.setInfo(NodeData.gray);
			m_dist.put(source, 0);

			Queue<node_data> q = new LinkedList<node_data>();
			q.add(source);

			while (!q.isEmpty()) {
				node_data u = q.poll();

				for (node_data v : u.getNi()) {
					if (v.getInfo().equals(NodeData.white)) {
						v.setInfo(NodeData.gray);
						m_dist.put(v, m_dist.get(u) + 1);
						m_prev.put(v, u);

						q.add(v);
					}
				}

				u.setInfo(NodeData.black);
			}
		}
	}

	public boolean isConnected_____() {
		BFS bfs = new BFS();
		bfs.execute((Graph_DS) m_graph);

		for (node_data node : m_graph.getV()) {
			if (bfs.m_dist.get(node) == -1)
				return false;
		}

		return true;
	}

	@Override
	public boolean isConnected() {
		int count = 0;

		ArrayList<node_data> nodes = new ArrayList<node_data>();

		// Making all nodes white (in case they were black before)
		for (node_data n : m_graph.getV()) {
			if (nodes.isEmpty())
				nodes.add(n);
			n.setInfo(NodeData.white);
		}

		while (!nodes.isEmpty()) {
			node_data n = nodes.remove(0);

			if (n.getInfo().equals(NodeData.white)) {
				++count;
				n.setInfo(NodeData.gray);
			}

			if (n.getInfo().equals(NodeData.gray)) {
				for (node_data neighbor : n.getNi()) {
					if (neighbor.getInfo().equals(NodeData.white)) {
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

		TreeMap<node_data, node_data> prev = new TreeMap<node_data, node_data>();
		TreeMap<node_data, Integer> dist = new TreeMap<node_data, Integer>();

		for (node_data n : m_graph.getV()) {
			prev.put(n, null);
			dist.put(n, -1);
			n.setInfo(NodeData.white);
		}

		dist.put(destNode, 0);
		prev.put(destNode, null);
		destNode.setInfo(NodeData.gray);

		Queue<node_data> q = new LinkedList<node_data>();
		q.add(destNode);

		while (!q.isEmpty()) {
			node_data u = q.poll();

			if(dist.get(srcNode) != -1) 
				break;

			for (node_data v : u.getNi()) {
				if (v.getInfo().equals(NodeData.white)) {
					v.setInfo(NodeData.gray);
					dist.put(v, dist.get(u) + 1);
					prev.put(v, u);

					q.add(v);
				}
			}

			u.setInfo(NodeData.black);
		}

		return dist.get(srcNode);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data srcNode = NodeData.getNodeByKey(src), destNode = NodeData.getNodeByKey(dest);

		TreeMap<node_data, node_data> prev = new TreeMap<node_data, node_data>();
		TreeMap<node_data, Integer> dist = new TreeMap<node_data, Integer>();

		for (node_data n : m_graph.getV()) {
			prev.put(n, null);
			dist.put(n, -1);
			n.setInfo(NodeData.white);
		}

		dist.put(destNode, 0);
		prev.put(destNode, null);
		destNode.setInfo(NodeData.gray);

		Queue<node_data> q = new LinkedList<node_data>();
		q.add(destNode);

		while (!q.isEmpty()) {
			node_data u = q.poll();

			if(dist.get(srcNode) != -1) 
				break;

			for (node_data v : u.getNi()) {
				if (v.getInfo().equals(NodeData.white)) {
					v.setInfo(NodeData.gray);
					dist.put(v, dist.get(u) + 1);
					prev.put(v, u);

					q.add(v);
				}
			}

			u.setInfo(NodeData.black);
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
