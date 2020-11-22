package ex1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;




class WGraph_AlgoTest_1 {
	static NodeInfo n0;
	static NodeInfo n1;
	static NodeInfo n2;
	static NodeInfo n3;
	///////////////////////////
	private static WGraph_Algo g;
	private static WGraph_DS g_1, g_2;
	//////////////////////////////////
	WGraph_Algo x;
	WGraph_DS y;
	@BeforeAll
	static void print_welcome() {
		System.out.println("welcome to GraphAlgoTest");
		/////////////////////////////////////
		/*
		 * arbitrary graph
		 */
		n0 = new NodeInfo();
		n1 = new NodeInfo();
		n2 = new NodeInfo();
		n3 = new NodeInfo();

		g_1=new WGraph_DS();
		g_2= new WGraph_DS();
		////////////////////////////////
		g=new WGraph_Algo();

	}
	@BeforeEach
	void init_graph() {
		x= new WGraph_Algo();
		y= new WGraph_DS();
		/////////////////////
		////////////////////////////////
		/*
		 * creating first graph (star graph)
		 */
		for (int i = 0; i <= 1000001 ; i++) {
			g_1.addNode(i);
		}
		//forming the star
		for (int i = 1; i < 1000001; i++) {
			g_1.connect(0, i, Math.random());
		}

		/*
		 * creating second graph (path graph)
		 */
		for (int i = 0; i <= 10000 ; i++) {
			g_2.addNode(i);
		}
		for (int i = 1; i <=10000; i++) {
			g_2.connect(i-1, i, 1);
		}
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
		assertDoesNotThrow(()->x.load("Test.txt"));
	}

	@Test
	void testSave() {
		x.init(y);
		assertDoesNotThrow(()->x.save("Test.txt"));
	}

	@Test
	void testIsConnected() {
		x.init(y);
		assertTrue(x.isConnected());

	}

	@Nested
	class testShortestPathDist
	{
		@Test
		@DisplayName("test the known arbitrary graph")
		void first() 
		{

			y.connect(0, 1, 700);
			y.connect(1, 2, 10);
			y.connect(2, 3, 12);
			y.connect(3, 0, 500);
			y.connect(3, 1, 0.7);
			x.init(y);
			assertEquals(10.7, x.shortestPathDist(3, 2));
		}

		@Test
		@DisplayName("Path Graph")
		void second()
		{

			g.init(g_2);
			assertEquals(g.getGraph().edgeSize(), g.shortestPathDist(0, 10000));
		}
	}

	@Nested
	class shortest_path_test
	{

		@Test
		@DisplayName("First Graph")
		void first() {
			y.connect(0, 1, 700);
			y.connect(1, 2, 10);
			y.connect(2, 3, 12);
			y.connect(3, 0, 500);
			y.connect(3, 1, 0.7);
			x.init(y);

			List<node_info> real= x.shortestPath(3, 2);
			List<node_info> expected= new ArrayList<node_info>();
			expected.add(n3);
			expected.add(n1);
			expected.add(n2);
			assertTrue(expected.containsAll(real));

		}
		@Test
		@DisplayName("Star Graph")
		void second() 
		{
			g.init(g_1);
			int index_1= 0;
			int index_2;
			index_1 = ThreadLocalRandom.current().nextInt(1, 10000 + 1);
			index_2= index_1;
			while(index_2==index_1)
				index_2 = ThreadLocalRandom.current().nextInt(1, 10000 + 1);
			assertTrue(g.shortestPath(index_1, index_2).get(1).getKey()==0);
			/*
			 * removing the central node of the star graph
			 */
			g.getGraph().removeNode(0);
			assertTrue(g.shortestPath(index_1, index_2)==null);
			
		}
		@Test
		@DisplayName("Path Graph")
		void third()
		{
			g.init(g_2);
			List<node_info> path_list=g.shortestPath(0,10000);
			for (int i = 0; i <= 10000; i++) {
				assertTrue(path_list.get(i).getKey()==i);
			}
		}
	}
	@Test
	void testTSP() {
		y.connect(0, 1, 700);
		y.connect(1, 2, 10);
		y.connect(2, 3, 12);
		y.connect(3, 0, 500);
		y.connect(0, 3, 0.5);
		y.connect(3, 1, 0.7);
		x.init(y);
		List<node_info> real= x.shortestPath(3, 2);
		List<node_info> expected= new ArrayList<node_info>();
		expected.add(n3);
		expected.add(n1);
		expected.add(n2);


		assertTrue(expected.containsAll(real));

	}

}