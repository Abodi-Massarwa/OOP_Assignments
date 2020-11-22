package ex1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class RunTimeTest {



	@Nested
	class RuntimeTestSet
	{

		@Test
		@DisplayName("First Test")
		void test_1() {
			weighted_graph g = new WGraph_DS();
			for (int i = 0; i < 9; i++)
				g.addNode(i);
			g.connect(1, 2, 8);
			g.connect(6, 7, 10);
			g.connect(2, 6, 4);
			g.connect(4, 8, 7);
			g.connect(5, 6, 2);
			g.connect(1, 4, 11);
            g.connect(0, 1, 4);
			g.connect(4, 5, 1);
			g.connect(2, 3, 7);
			g.connect(0, 4, 8);
			g.connect(2, 8, 2);
			g.connect(3, 7, 9);
			g.connect(3, 6, 14);
			g.connect(5, 8, 6);
			weighted_graph_algorithms a=new WGraph_Algo();
			a.init(g);
			long start= new Date().getTime();
			assertEquals(21,a.shortestPathDist(0,7));
			assertEquals(19,a.shortestPathDist(0,3));
			assertEquals(14,a.shortestPathDist(0,8));
			assertEquals(4,a.shortestPathDist(0,1));
			assertEquals(11,a.shortestPathDist(0,6));
			assertEquals(8,a.shortestPathDist(0,4));
			assertEquals(9,a.shortestPathDist(0,5));
			assertEquals(12,a.shortestPathDist(0,2));
			long end = new Date().getTime();
			double dt = (end - start) / 1000.0;
			boolean t= dt<0.5;
			assertTrue(t);
			System.out.println("Time elapsed : " + dt);
		}
		
		 @Test 
		 @DisplayName("Second Test")
		    void test_2() {
		        long start = new Date().getTime();

		        weighted_graph gr = new WGraph_DS();
		        double w = 0.1;

		        for (int i = 0; i < 1000000; i++) {
		            gr.addNode(i);
		        }
		        int [] arr = new int [10];
		        for (int i = 0; i < arr.length; i++) {
					arr[i]=ThreadLocalRandom.current().nextInt(1, 10000 + 1);
				}

		        for (int i = 0, j = 1; i < 1000000; i++, w++) {
		            gr.connect(i, j, w);
		            
		            for(int k = 0; k < arr.length; ++k)
			            gr.connect(arr[k], j, w);
			            
		            j += 2;
		        }
		        long end = new Date().getTime();
		        double elapsed = (end - start) / 1000.0;
		        assertTrue(elapsed<17);
		      
		    }

		
		
	}
}
