package ex0;

import java.util.ArrayList;
import java.util.Collection;

public class NodeData implements node_data {
	
	private static int m_keyGenerator = 0;
	
	private int m_key;
	private ArrayList<node_data> m_neighbors;
	
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
		// TODO Auto-generated method stub
		return m_key;
	}

	@Override
	public Collection<node_data> getNi() {
		// TODO Auto-generated method stub
		return m_neighbors;
	}

	@Override
	public boolean hasNi(int key) {
		// TODO Auto-generated method stub
		// Dummy Object
		return m_neighbors.contains(new NodeData(key));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof NodeData)
			return ((NodeData) o).m_key == this.m_key;
		
		return false;
	}

	@Override
	public void addNi(node_data t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeNode(node_data node) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInfo(String s) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTag(int t) {
		// TODO Auto-generated method stub

	}

}
