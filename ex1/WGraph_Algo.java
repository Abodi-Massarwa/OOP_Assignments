package ex1;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;


public class WGraph_Algo implements weighted_graph_algorithms  {

	/**
	 * 
	 */
	
	/**
	 * 
	 */
	
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

		TreeMap<Double, ArrayList<node_info>> q = new TreeMap<Double, ArrayList<node_info>>();
		q.put(0.0, new ArrayList<node_info>(Arrays.asList(destNode)));

		while (!q.isEmpty()) {
			Double closest = q.firstKey();
			node_info u = q.get(closest).remove(0);
			if(q.get(closest).isEmpty())
				q.remove(closest);

			for (node_info v : m_weightedGraph.getV(u.getKey())) {
				double alt = dist.get(u) + m_weightedGraph.getEdge(v.getKey(), u.getKey());
				if (dist.get(v) == -1.0 || alt < dist.get(v)) {
					dist.put(v, alt);
					
					q.computeIfPresent(alt, (k,list) -> {
						list.add(v);
						
						return list;
					});
					q.putIfAbsent(alt, new ArrayList<node_info>(Arrays.asList(v)));
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

		TreeMap<Double, ArrayList<node_info>> q = new TreeMap<Double, ArrayList<node_info>>();
		q.put(0.0, new ArrayList<node_info>(Arrays.asList(destNode)));

		while (!q.isEmpty()) {
			Double closest = q.firstKey();
			node_info u = q.get(closest).remove(0);
			if(q.get(closest).isEmpty())
				q.remove(closest);

			for (node_info v : m_weightedGraph.getV(u.getKey())) {
				double alt = dist.get(u) + m_weightedGraph.getEdge(v.getKey(), u.getKey());
				if (dist.get(v) == -1.0 || alt < dist.get(v)) {
					dist.put(v, alt);
					prev.put(v, u);
					
					q.computeIfPresent(alt, (k,list) -> {
						list.add(v);
						
						return list;
					});
					q.putIfAbsent(alt, new ArrayList<node_info>(Arrays.asList(v)));
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

	@SuppressWarnings("null")
	@Override
	public boolean save(String file) {
		ObjectOutputStream om = null;
		try {
			om = new ObjectOutputStream(new FileOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			try {
				om.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		try {
			om.writeObject(this.getGraph());
		} catch (IOException e) {
			e.printStackTrace();
			try {
				om.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			return false;
		}
		try {
			om.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				om.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean load(String file) {
		ObjectInputStream os =null;
		boolean flag=true;
		weighted_graph read=null;
		try {
			os= new ObjectInputStream(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		while(flag) {
			try {
				read= (weighted_graph) os.readObject();
			} catch (ClassNotFoundException e) {e.printStackTrace();
				return false;}
			catch (EOFException e) {
				flag=false;
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		this.m_weightedGraph=read;
		return true;
	}

}
