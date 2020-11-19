package ex1;

public class NodeInfo implements node_info {
	
	private static int s_keyGenerator = 0;
	
	private int m_key;
	private String m_info;
	private double m_tag;
	
	public NodeInfo() {
		m_key = s_keyGenerator;
		++s_keyGenerator;
	}

	@Override
	public int getKey() {
		return this.m_key;
	}

	@Override
	public String getInfo() {
		return this.m_info;
	}

	@Override
	public void setInfo(String s) {
		this.m_info = s;
	}

	@Override
	public double getTag() {
		return this.m_tag;
	}

	@Override
	public void setTag(double t) {
		this.m_tag = t;
	}

}
