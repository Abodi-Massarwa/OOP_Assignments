package ex0;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NodeData implements node_data, Comparable<node_data> {
	
	private static int s_keyGenerator = 0;
	public final static int white = 0, gray =  1, black = 2;

	
	private int m_key;
	private Set<node_data> m_neighbors = new HashSet<node_data>();
	private String m_innerInfo = "";
	private int m_innerTag = 0;
	
	public NodeData() {
		m_key = s_keyGenerator;
		++s_keyGenerator;
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
	
	@Override
	public int hashCode() {
		return m_key;
	}

	@Override
	public int compareTo(node_data arg0) {
		return this.m_key - arg0.getKey();
	}

}