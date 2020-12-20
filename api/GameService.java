package api;

import org.json.JSONObject;

import gameClient.CL_Agent;
import gameClient.CL_Pokemon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;

public class GameService implements game_service {
	
	private directed_weighted_graph m_graph;
	private List<CL_Pokemon> m_pokemons;
	private HashMap<Integer, CL_Agent> m_agents;
	private boolean m_isRunning = false;
	private long m_timerInMilisecond;
	
	public GameService(int someArg) {
		
	}

	@Override
	public String getGraph() {
		return new JSONObject(m_graph).toString();
	}

	@Override
	public String getPokemons() {
		return new JSONObject(m_agents).toString();
	}

	@Override
	public String getAgents() {
		return new JSONObject(m_agents).toString();
	}

	@Override
	public boolean addAgent(int start_node) {
		for(Integer id: m_agents.keySet())
			// might be getGEOLocation
			if(m_agents.get(id).getSrcNode() == start_node)
				return false;
		
		CL_Agent agent = new CL_Agent(m_graph, start_node);
		m_agents.put(agent.getID(),  agent);
		return true;
	}

	@Override
	public long startGame() {
		// TODO Hell if I know
		m_isRunning = true;
		
		return 0;
	}

	@Override
	public boolean isRunning() {
		return m_isRunning;
	}

	@Override
	public long stopGame() {
		// TODO I don't know what to do
		m_isRunning = false;
		
		return 0;
	}

	@Override
	public long chooseNextEdge(int id, int next_node) {
		CL_Agent agent = m_agents.get(id);
		if(agent == null)
			return -1;
		
		return 0;
	}

	@Override
	public long timeToEnd() {
		return m_timerInMilisecond;
	}

	@Override
	public String move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
