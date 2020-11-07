package ex0;

import java.util.ArrayList;
import java.util.Collection;

public class NodeData implements node_data {
	
	private static int m_keyGenerator = 0;
	
	private int m_key;
	private ArrayList<node_data> m_neighbors;
	private String m_innerInfo = "No info special aviable yet!";
	private int m_innerTag = Integer.MIN_VALUE;
	
	public NodeData() {
		m_key = m_keyGenerator;
		++m_keyGenerator;
		
		m_neighbors = new ArrayList<node_data>();
	}
	
	private NodeData(int key) {
		m_key = key;
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
		// Dummy Object
		return m_neighbors.contains(new NodeData(key));
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
