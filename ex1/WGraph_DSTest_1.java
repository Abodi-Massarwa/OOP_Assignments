package ex1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WGraph_DSTest_1 {

	 private static WGraph_DS g;

	 @BeforeAll
	 static void print()
	 {
		 System.out.println("Welcome to Weighted-Graph Test Section");
	 }
	 
	    @BeforeEach
	    void GraphGenerator() {
	        g = new WGraph_DS();
	        for (int i = 0; i < 4; i++)
	            g.addNode(i);
	        g.connect(0, 1, ThreadLocalRandom.current().nextInt(300, 999 + 1));
	        g.connect(2, 3, ThreadLocalRandom.current().nextInt(300, 999 + 1));
	        g.connect(3, 0, ThreadLocalRandom.current().nextInt(300, 999 + 1));
	        g.connect(2, 0, ThreadLocalRandom.current().nextInt(300, 999 + 1));
	        g.connect(1, 3, ThreadLocalRandom.current().nextInt(300, 999 + 1));
	    }
	    
	    
	    @Test
	    void getNode() {
	    	
	        assertTrue(g.getNode(10)==null);
	        assertTrue(g.getNode(0)!=null);
	    }

	    @Test
	    void hasEdge() {
	    	
	    	
	    	for (int i = 0; i < 1000; i++) {
				
	    	for (int j = 0; j < g.nodeSize(); j++) {
	    		assertFalse(g.hasEdge(j, ThreadLocalRandom.current().nextInt(20, 10000 + 1)));
			}
	    	}

	    	for (int i = 0; i < 1000; i++) {
				
	    	for (int j = 0; j < g.nodeSize(); j++) {
	    		assertFalse(g.hasEdge(j, j));
			}
	    	}
	    }

	    @Test
	    void getEdge() {
	        for (int i = 1; i < 4; i++) {
	        	double weight=g.getEdge(i-1, i);
				assertTrue(weight>=300 && weight<=998 || weight==-1);
			}
	    }
	    void edgeSize() {
	        weighted_graph g1 = new WGraph_DS();
	        assertEquals(0, g1.edgeSize());
	        g1.addNode(2);
	        assertEquals(0, g1.edgeSize());
	        g1.connect(2, 0, 3);
	        assertEquals(0, g1.edgeSize());
	        g1.connect(2, 2, 0);
	        assertEquals(0, g1.edgeSize());
	        g1.addNode(1);
	        g1.connect(1, 2, 3);
	        assertEquals(1, g1.edgeSize());
	        g1.connect(2, 1, 4);
	        assertEquals(1, g1.edgeSize());
	    
	       
	    }
}
