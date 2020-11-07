package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class NodeData implements node_data {
	
	private static int s_keyGenerator = 0;
	private static HashMap<Integer, NodeData> s_allNodes = new HashMap<Integer, NodeData>();
	
	public static node_data getNodeByKey(int key) {
		return s_allNodes.get(key);
	}
	
	private int m_key;
	private HashSet<node_data> m_neighbors = new HashSet<node_data>();
	private String m_innerInfo = "No info special aviable yet!";
	private int m_innerTag = Integer.MIN_VALUE;
	
	public NodeData() {
		m_key = s_keyGenerator;
		++s_keyGenerator;
		
		// To have access to this node by it's ID
		s_allNodes.put(m_key, this);
	}

	@Override
	public int getKey() {
		return m_key;
	}

	@Override
	public Collection<node_data> getNi() {
		return m_neighbors;
	}

	@Override
	public boolean hasNi(int key) {
		return m_neighbors.contains(getNodeByKey(key));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof NodeData)
			return ((NodeData) o).getKey() == this.getKey();
		
		return false;
	}

	@Override
	public void addNi(node_data t) {
		this.m_neighbors.add(t);
	}

	@Override
	public void removeNode(node_data node) {
		this.m_neighbors.remove(node);
	}

	@Override
	public String getInfo() {
		return m_innerInfo;
	}

	@Override
	public void setInfo(String s) {
		m_innerInfo = s;
	}

	@Override
	public int getTag() {
		return m_innerTag;
	}

	@Override
	public void setTag(int t) {
		m_innerTag = t;
	}

}
