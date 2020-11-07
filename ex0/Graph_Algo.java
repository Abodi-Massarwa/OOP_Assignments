package ex0;

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
		
		for(node_data n: m_graph.getV()) {
			n.setInfo(NodeData.white);
		}
		
		for(node_data n: m_graph.getV()) {
			if(n.getInfo() == NodeData.white) {
				++count;
				
				
			}
		}
		
		return m_graph.getV().size() == count;
	}

	@Override
	public int shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

}
