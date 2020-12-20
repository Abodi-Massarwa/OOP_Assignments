package api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DWGraph_AlgoTest {
	static NodeData n0;
	static NodeData n1;
	static NodeData n2;
	static NodeData n3;

	
	static int numOfNodesG1 = 1000001, numOfNodesG2 = 10000;
	 

	static void initNodesZeroToThree() {
		n0 = (NodeData) NodesDataHolder.getNodeByKey(0);
		n0 = n0==null? new NodeData(): n0;
		
		n1 = (NodeData) NodesDataHolder.getNodeByKey(1);
		n1 = n1==null? new NodeData(): n1;
		
		n2 = (NodeData) NodesDataHolder.getNodeByKey(2);
		n2 = n2==null? new NodeData(): n2;
		
		n3 = (NodeData) NodesDataHolder.getNodeByKey(3);
		n3 = n3==null? new NodeData(): n3;
	}

	///////////////////////////
	private static DWGraph_Algo g;
	private static DWGraph_DS g_1, g_2;
	//////////////////////////////////
	DWGraph_Algo x;
	DWGraph_DS y;

	@BeforeAll
	static void print_welcome() {
		System.out.println("welcome to GraphAlgoTest");
		/////////////////////////////////////
		/*
		 * arbitrary graph
		 */

		g_1 = new DWGraph_DS();
		g_2 = new DWGraph_DS();
		////////////////////////////////
		g = new DWGraph_Algo();
		
		/////////////////////
		////////////////////////////////
		/*
		 * creating first graph (star graph)
		 */

		for (int i = 0; i <= numOfNodesG1; i++) {
			g_1.addNode(new NodeData());
		}
		// forming the star
		for (int i = 1; i < numOfNodesG1; i++) {
			g_1.connect(0, i, Math.random());
			g_1.connect(i, 0, Math.random());
		}

		/*
		 * creating second graph (path graph)
		 */
		for (int i = 0; i <= numOfNodesG2; i++) {
			node_data node = NodesDataHolder.getNodeByKey(i);
			node = node==null? new NodeData(): node;
			g_2.addNode(node);
		}
		for (int i = 1; i <= numOfNodesG2; i++) {
			g_2.connect(i-1, i, 1);
			g_2.connect(i, i-1, 1);
		}
	}

	@BeforeEach
	void init_graph() {
		x = new DWGraph_Algo();
		y = new DWGraph_DS();
	}

	@AfterEach
	void cleanup() {
		System.out.println("cleaning up .....");
	}

	@AfterAll
	static void print_bye() {
		System.out.println("Test completed !");
	}

	@Test
	void testInitGraph() {
		x.init(y);
		assertTrue(x.getGraph().equals(y));
	}

	@Test
	void testInitString() {
		assertDoesNotThrow(() -> x.load("Test.txt"));
	}

	@Test
	void testSave() {
		x.init(y);
		assertDoesNotThrow(() -> x.save("Test.txt"));
	}

	@Test
	void testIsConnected() {
		x.init(y);
		assertTrue(x.isConnected());

	}

	@Nested
	class testShortestPathDist {
		@Test
		@DisplayName("test the known arbitrary graph")
		void first() {

			initNodesZeroToThree();
			
			y.addNode(n0);
			y.addNode(n1);
			y.addNode(n2);
			y.addNode(n3);
			
			int[] nodeKeysSrc = {0, 1, 2, 3, 3},
					nodeKeysDest = {1, 2, 3, 0, 1};
			double[] weights = {700, 10, 12, 500, 0.7};
			
			for (int i = 0; i < weights.length; i++) {
				y.connect(nodeKeysSrc[i], nodeKeysDest[i], weights[i]);
				y.connect(nodeKeysDest[i], nodeKeysSrc[i], weights[i]);
			}
			
			
//			y.connect(0, 1, 700);
//			y.connect(1, 2, 10);
//			y.connect(2, 3, 12);
//			y.connect(3, 0, 500);
//			y.connect(3, 1, 0.7);
			
			x.init(y);
			assertEquals(10.7, x.shortestPathDist(3, 2));
		}

		@Test
		@DisplayName("Path Graph")
		void second() {

			g.init(g_2);
			double ans = g.shortestPathDist(0, numOfNodesG2);
			
			assertEquals(g.getGraph().edgeSize()/2, ans);
		}
	}

	@Nested
	class shortest_path_test {

		@Test
		@DisplayName("First Graph")
		void first() {
			initNodesZeroToThree();
			
			y.addNode(n0);
			y.addNode(n1);
			y.addNode(n2);
			y.addNode(n3);
			
			int[] nodeKeysSrc = {0, 1, 2, 3, 3},
					nodeKeysDest = {1, 2, 3, 0, 1};
			double[] weights = {700, 10, 12, 500, 0.7};
			
			for (int i = 0; i < weights.length; i++) {
				y.connect(nodeKeysSrc[i], nodeKeysDest[i], weights[i]);
				y.connect(nodeKeysDest[i], nodeKeysSrc[i], weights[i]);
			}
			
//			y.connect(0, 1, 700);
//			y.connect(1, 2, 10);
//			y.connect(2, 3, 12);
//			y.connect(3, 0, 500);
//			y.connect(3, 1, 0.7);
			x.init(y);

			List<node_data> real = x.shortestPath(3, 2);
			List<node_data> expected = new ArrayList<node_data>();

			initNodesZeroToThree();

			expected.add(n3);
			expected.add(n1);
			expected.add(n2);
			assertTrue(expected.containsAll(real));

		}

		@Test
		@DisplayName("Star Graph")
		void second() {
			g.init(g_1);
			int index_1 = 0;
			int index_2;
			index_1 = ThreadLocalRandom.current().nextInt(1, numOfNodesG2 + 1);
			index_2 = index_1;
			while (index_2 == index_1)
				index_2 = ThreadLocalRandom.current().nextInt(1, numOfNodesG2 + 1);
			assertTrue(g.shortestPath(index_1, index_2).get(1).getKey() == 0);
			/*
			 * removing the central node of the star graph
			 */
			g.getGraph().removeNode(0);
			assertTrue(g.shortestPath(index_1, index_2) == null);

		}

		@Test
		@DisplayName("Path Graph")
		void third() {
			g.init(g_2);
			List<node_data> path_list = g.shortestPath(0, numOfNodesG2);
			for (int i = 0; i <= numOfNodesG2; i++) {
				assertTrue(path_list.get(i).getKey() == i);
			}
		}
	}

	@Test
	void testTSP() {
		initNodesZeroToThree();
		
		y.addNode(n0);
		y.addNode(n1);
		y.addNode(n2);
		y.addNode(n3);
		
		int[] nodeKeysSrc = {0, 1, 2, 3, 0, 3},
				nodeKeysDest = {1, 2, 3, 0, 3, 1};
		double[] weights = {700, 10, 12, 500, 0.5, 0.7};
		
		for (int i = 0; i < weights.length; i++) {
			y.connect(nodeKeysSrc[i], nodeKeysDest[i], weights[i]);
			y.connect(nodeKeysDest[i], nodeKeysSrc[i], weights[i]);
		}
		
//		y.connect(0, 1, 700);
//		y.connect(1, 2, 10);
//		y.connect(2, 3, 12);
//		y.connect(3, 0, 500);
//		y.connect(0, 3, 0.5);
//		y.connect(3, 1, 0.7);
		
		x.init(y);
		List<node_data> real = x.shortestPath(3, 2);
		List<node_data> expected = new ArrayList<node_data>();

		expected.add(n3);
		expected.add(n1);
		expected.add(n2);

		assertTrue(expected.containsAll(real));

	}

}