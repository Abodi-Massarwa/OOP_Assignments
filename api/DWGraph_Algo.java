package api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;


public class DWGraph_Algo implements dw_graph_algorithms {

	private directed_weighted_graph m_graph;
	
	@Override
	public void init(directed_weighted_graph g) {
		m_graph = g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		return m_graph;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copy = new DWGraph_DS();
		HashMap<Integer, Integer> oldToCopy = new HashMap<Integer, Integer>();
		for (node_data oldNode : m_graph.getV()) {
			node_data newNode = new NodeData();
			oldToCopy.put(oldNode.getKey(), newNode.getKey());
			copy.addNode(newNode);
		}
		
		for (node_data oldNode : m_graph.getV()) {
			for (edge_data oldEdge : m_graph.getE(oldNode.getKey())) {
				copy.connect(oldToCopy.get(oldEdge.getSrc()), oldToCopy.get(oldEdge.getDest()), oldEdge.getWeight());
			}
		}
		
		return copy;
	}

	@Override
	public boolean isConnected() {
		int numberOfNodes = 1;
		node_data source = null;
		for (node_data node : m_graph.getV()) {
			source = node;
			node.setTag(NodesDataHolder.WHITE);
		}
		
		Queue<node_data> q = new LinkedList<node_data>();
		q.add(source);
		
		while (!q.isEmpty()) {
			node_data node = q.poll();
			node.setTag(NodesDataHolder.GRAY);
			
			for (edge_data node_edges : m_graph.getE(node.getKey())) {
				node_data neihgbor = NodesDataHolder.getNodeByKey(node_edges.getDest());
				if(neihgbor.getTag() == NodesDataHolder.WHITE) {
					q.add(neihgbor);
					neihgbor.setTag(NodesDataHolder.GRAY);
					
					++numberOfNodes;
				}
			}
			
			node.setTag(NodesDataHolder.BLACK);
		}
		
		return (numberOfNodes == m_graph.getV().size());
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		HashMap<node_data, node_data> nodeToPre = new HashMap<node_data, node_data>();
		TreeMap<Double, ArrayList<node_data>> distToNode = new TreeMap<Double, ArrayList<node_data>>();
		
		for (node_data node: m_graph.getV()) {
			node.setWeight(Integer.MAX_VALUE);
		}
		
		distToNode.put(0.0, (ArrayList<node_data>) Arrays.asList(NodesDataHolder.getNodeByKey(src)));
		
		while (!distToNode.isEmpty()) {
			ArrayList<node_data> lstOfClosestNodes = distToNode.firstEntry().getValue();
			for(node_data minDistNode: lstOfClosestNodes) {
				double minDist = distToNode.firstEntry().getKey();
				minDistNode.setWeight(minDist);
				
				for (edge_data edge: m_graph.getE(minDistNode.getKey())) {
					double alternativeWeight = minDistNode.getWeight() + edge.getWeight();
					node_data dstNode = NodesDataHolder.getNodeByKey(edge.getDest());
					if(alternativeWeight < dstNode.getWeight()) {
						dstNode.setWeight(alternativeWeight);
						
						distToNode.computeIfPresent(alternativeWeight, (w, list) -> {
							list.add(minDistNode);
							
							return list;
						});
						
						distToNode.putIfAbsent(alternativeWeight, (ArrayList<node_data>) Arrays.asList(dstNode));
						
						nodeToPre.put(dstNode, minDistNode);
					}
				}
			}
			
			distToNode.pollFirstEntry();
		}
		
		node_data destNode = NodesDataHolder.getNodeByKey(dest);
		return nodeToPre.get(destNode) != null? destNode.getWeight(): -1.0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		HashMap<node_data, node_data> nodeToPre = new HashMap<node_data, node_data>();
		TreeMap<Double, ArrayList<node_data>> distToNode = new TreeMap<Double, ArrayList<node_data>>();
		
		for (node_data node: m_graph.getV()) {
			node.setWeight(Integer.MAX_VALUE);
		}
		
		distToNode.put(0.0, (ArrayList<node_data>) Arrays.asList(NodesDataHolder.getNodeByKey(src)));
		
		while (!distToNode.isEmpty()) {
			ArrayList<node_data> lstOfClosestNodes = distToNode.firstEntry().getValue();
			for(node_data minDistNode: lstOfClosestNodes) {
				double minDist = distToNode.firstEntry().getKey();
				minDistNode.setWeight(minDist);
				
				for (edge_data edge: m_graph.getE(minDistNode.getKey())) {
					double alternativeWeight = minDistNode.getWeight() + edge.getWeight();
					node_data dstNode = NodesDataHolder.getNodeByKey(edge.getDest());
					if(alternativeWeight < dstNode.getWeight()) {
						dstNode.setWeight(alternativeWeight);
						
						distToNode.computeIfPresent(alternativeWeight, (w, list) -> {
							list.add(minDistNode);
							
							return list;
						});
						
						distToNode.putIfAbsent(alternativeWeight, (ArrayList<node_data>) Arrays.asList(dstNode));
						
						nodeToPre.put(dstNode, minDistNode);
					}
				}
			}
			
			distToNode.pollFirstEntry();
		}
		
		node_data destNode = NodesDataHolder.getNodeByKey(dest);
		if(nodeToPre.get(destNode) == null)
			return null;
		
		List<node_data> ans = new ArrayList<node_data>();
		ans.add(destNode);
		while(destNode.getKey() != src) {
			ans.add(0, destNode);
		}
		
		ans.add(0, destNode);
		
		
		return ans;
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