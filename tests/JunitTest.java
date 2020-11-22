package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class JunitTest {

    @Test
    void getNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        assertNotNull(g.getNode(1));
        assertNull(g.getNode(8));
        assertNull(g.getNode(-1));
        weighted_graph g2 = graph_creator_Connected();
        assertNotNull(g2.getNode(0));
        assertNotNull(g2.getNode(10));
        assertNull(g.getNode(8));
        assertNull(g.getNode(-1));
        g2.removeNode(0);
        assertNull(g.getNode(0));
    }


    @Test
    void hasEdge() {
        weighted_graph g = graph_creator_Connected();
        if (g.hasEdge(1, 2) != true) {
            fail("was not true as excepted");
        }
        if (g.hasEdge(1, 10) == true) {
            fail("was not false as excepted");
        }
        if (g.hasEdge(2, 10) == false) {
            fail("was not true as excepted");
        }
        if (g.hasEdge(0, 10) == true) {
            fail("was not false as excepted");
        }
    }

    @Test
    void getEdge() {
        weighted_graph g = graph_creator_Connected();
        assertEquals(g.getEdge(1, 11), -1);
        assertNotEquals(g.getEdge(1, 2), -1);
        assertEquals(g.getEdge(1, 2), 3);
        assertEquals(g.getEdge(0, 10), -1);

    }

    @Test
    void addNode() {
        weighted_graph g = graph_creator_Connected();
        g.addNode(0);
        if (!(g.getNode(0) instanceof node_info)) {
            fail("was not node_info as excepted");
        }
        assertEquals(g.getNode(0).getTag(), -1);
        assertEquals(g.getNode(0).getKey(), 0);
        assertEquals(g.getNode(0).getInfo(), " ");
        assertNotNull(g.getV(0));
        assertNotNull(g.getV());

    }

    @Test
    void connect() {
        weighted_graph g = graph_creator_Connected();
        g.connect(0, 20, 4);
        assertEquals(g.getEdge(0, 20), -1);
        g.connect(-5, 3, 5);
        assertEquals(g.getEdge(-5, 3), -1);
        g.addNode(20);
        g.connect(0, 20, 3);
        assertEquals(g.getEdge(0, 20), 3);
        g.connect(0, 20, 4);
        assertEquals(g.getEdge(0, 20), 4);
    }

    @Test
    void getV() {
        weighted_graph g = graph_creator_Connected();
        assertNotNull(g.getV());
        g.addNode(21);
        assertNotNull(g.getV().contains(21));
        weighted_graph g2 = new WGraph_DS();
        assertNotNull(g2.getV());
            }

    @Test
    void testGetV() {
        weighted_graph g = graph_creator_Connected();
        assertNotNull(g.getV(0));
        assertNull(g.getV(30));
        assertNull(g.getV(-4));
    }

    @Test
    void removeNode() {
        weighted_graph g = graph_creator_Connected();
        assertNull(g.removeNode(12));
        assertNull(g.removeNode(-1));
        assertNotNull(g.removeNode(0));
        g.getV().remove(10);
        assertNull(g.removeNode(0));
    }


    @Test
    void removeEdge() {
        weighted_graph g = graph_creator_Connected();
        g.removeEdge(1, 2);
        assertEquals(g.hasEdge(1, 2), false);
        g.removeEdge(1, 20);
        assertNotNull(g.getNode(1));
        assertNotNull(g.getEdge(0, 1));
        assertNotEquals(g.hasEdge(0, 1), false);
    }

    @Test
    void nodeSize() {
        weighted_graph g = graph_creator_Connected();
        assertNotNull(g.nodeSize());
        assertEquals(g.nodeSize(), 11);
        g.removeNode(0);
        assertEquals(g.nodeSize(), 10);
        g.removeNode(12);
        assertEquals(g.nodeSize(), 10);
        g.removeEdge(1, 2);
        assertEquals(g.nodeSize(), 10);
        weighted_graph g2 = new WGraph_DS();
        assertNotNull(g2);
        assertEquals(g2.nodeSize(), 0);
    }

    @Test
    void edgeSize() {
        weighted_graph g = graph_creator_Connected();
        assertEquals(g.edgeSize(), 36);
        g.removeEdge(1, 2);
        assertEquals(g.edgeSize(), 35);
        g.removeEdge(1, 12);
        assertEquals(g.edgeSize(), 35);
        g.addNode(30);
        g.connect(2, 30, 4);
        assertEquals(g.edgeSize(), 36);
        weighted_graph g2 = new WGraph_DS();
        assertEquals(g2.edgeSize(), 0);
        g2.addNode(11);
        assertNotEquals(g2.edgeSize(), 1);
    }

    @Test
    void getMC() {
        weighted_graph g = new WGraph_DS();
        assertEquals(g.getMC(), 0);
        g.addNode(1);
        assertEquals(g.getMC(), 1);
        g.addNode(2);
        g.connect(1, 2, 3);
        assertEquals(g.getMC(), 3);
        g.connect(1, 0, 4);
        g.connect(1, -7, 4);
        g.connect(20, -7, 4);
        assertEquals(g.getMC(), 3);
        g.connect(1, 2, 7);
        assertEquals(g.getMC(), 4);
        g.connect(1, 2, 7);
        assertEquals(g.getMC(), 4);

    }

    @Test
    void setMC() {
        WGraph_DS g = new WGraph_DS();
        assertEquals(g.getMC(), 0);
        g.setMC(7);
        assertEquals(g.getMC(), 7);
        g.setMC(0);
        assertEquals(g.getMC(), 0);
    }


    public static weighted_graph graph_creator_weigh_notconnected() {
        weighted_graph g = new WGraph_DS();

        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.addNode(10);

        g.connect(1, 2, 3);
        g.connect(1, 3, 5);
        g.connect(2, 4, 2);
        g.connect(3, 6, 1);
        g.connect(5, 7, 2);
        g.connect(8, 5, 3);
        g.connect(4, 9, 6);
        g.connect(7, 8, 1);
        g.connect(3, 10, 20);

        return g;
    }


    public static weighted_graph graph_creator_weigh_connected() {
        weighted_graph g = new WGraph_DS();

        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.addNode(10);

        g.connect(1, 2, 3);
        g.connect(1, 3, 5);
        g.connect(2, 5, 1);
        g.connect(2, 4, 2);
        g.connect(3, 6, 1);
        g.connect(5, 7, 2);
        g.connect(8, 5, 3);
        g.connect(4, 9, 6);
        g.connect(7, 8, 1);
        g.connect(3, 10, 20);

        return g;
    }

    public static weighted_graph graph_creator_notConnected() {
        weighted_graph gn = new WGraph_DS();
        for (int i = 0; i < 11; i++) {
            gn.addNode(i);
        }
        for (int j = 0; j < 5; j++) {
            gn.connect(j, j + 1, Math.random() * 10);
        }
        gn.connect(1, 5, Math.random() * 10);
        gn.connect(3, 5, Math.random() * 10);

        for (int k = 6; k < 10; k++) {
            gn.connect(k, k + 1, Math.random() * 10);
        }
        gn.connect(6, 8, Math.random() * 10);
        return gn;
    }


    public static weighted_graph graph_creator_Connected() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 11; i++) {
            g.addNode(i);
        }
        for (int j = 0; j < 11; j++) {
            if (j + 1 < 11) {
                g.connect(j, j + 1, Math.random() * 10);
            }
        }
        Iterator<node_info> itr = g.getV().iterator();
        while (itr.hasNext()) {
            node_info temp = itr.next();
            g.connect(2, temp.getKey(), Math.random() * 10);
            g.connect(4, temp.getKey(), Math.random() * 10);
            g.connect(6, temp.getKey(), Math.random() * 10);
            g.connect(8, temp.getKey(), Math.random() * 10);
        }
        g.connect(1, 2, 3);
        return g;
    }

    @Test
    public void testRuntime() {

        weighted_graph g1 = new WGraph_DS();
        for (int i = 0; i < 1000000; i++) {
            g1.addNode(i);
        }

        for (int i = 0; i < 100000; i++) {
            g1.connect(i, 1, 1);
            g1.connect(i, 2, 2);
            g1.connect(i, 3, 3);
            g1.connect(i, 4, 4);
            g1.connect(i, 5, 5);
            g1.connect(i, 6, 6);
            g1.connect(i, 7, 7);
            g1.connect(i, 8, 8);
            g1.connect(i, 9, 9);
            g1.connect(i, 10, 10);
        }
    }

    //WGraph_Algo

    @Test
    void init() {
        weighted_graph g = graph_creator_Connected();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertNotNull(g1.getGraph());
    }

    @Test
    void getGraph() {
        weighted_graph g = graph_creator_Connected();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertNotNull(g1.getGraph());
    }

    @Test
    void copy() {
        //connected
        weighted_graph g = graph_creator_Connected();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);

        weighted_graph copy_graph = g1.copy();
        assertEquals(g.getMC(), copy_graph.getMC());
        assertEquals(g.edgeSize(), copy_graph.edgeSize());
        assertEquals(g.nodeSize(), copy_graph.nodeSize());
        assertNotNull(copy_graph);
        assertNotNull(copy_graph.getV());
        assertEquals(g.getNode(1).getTag(), copy_graph.getNode(1).getTag());
        assertEquals(g.getNode(1).getInfo(), copy_graph.getNode(1).getInfo());
        assertEquals(g.getNode(-4), copy_graph.getNode(-7));

        //not connected
        weighted_graph gn = graph_creator_notConnected();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(gn);

        weighted_graph copy_graph2 = g2.copy();
        assertEquals(gn.getMC(), copy_graph2.getMC());
        assertEquals(gn.edgeSize(), copy_graph2.edgeSize());
        assertEquals(gn.nodeSize(), copy_graph2.nodeSize());
        assertNotNull(copy_graph2);
        assertNotNull(copy_graph2.getV());
        assertEquals(gn.getNode(1).getTag(), copy_graph2.getNode(1).getTag());
        assertEquals(gn.getNode(1).getInfo(), copy_graph2.getNode(1).getInfo());
        assertEquals(gn.getNode(-4), copy_graph2.getNode(-7));

    }

    @Test
    void isConnected() {
        //connected
        weighted_graph g = graph_creator_Connected();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(g1.isConnected(), true);

        //not connected
        weighted_graph gn = graph_creator_notConnected();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(gn);

        assertEquals(g2.isConnected(), false);

        assertNotEquals(g1.isConnected(), g2.isConnected());
    }

    @Test
    void shortestPathDist() {
        //connected
        weighted_graph g = graph_creator_weigh_connected();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(g1.shortestPathDist(1, 8), 7);
        assertEquals(g1.shortestPathDist(1, 11), -1);
        assertEquals(g1.shortestPathDist(-7, 11), -1);
        assertEquals(g1.shortestPathDist(7, 6), 12);

        //notconnected
        weighted_graph gn = graph_creator_weigh_notconnected();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(gn);
        assertEquals(g2.shortestPathDist(1, 7), -1);
        assertEquals(g2.shortestPathDist(3, 5), -1);
        assertEquals(g2.shortestPathDist(-7, 12), -1);
        assertEquals(g2.shortestPathDist(-7, 12), -1);
        assertEquals(g2.shortestPathDist(5, 8), 3);
        assertEquals(g2.shortestPathDist(10, 9), 36);
    }

    @Test
    void shortestPath() {
        //connected
        weighted_graph g = graph_creator_weigh_connected();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        ArrayList<node_info> path = new ArrayList<node_info>();
        path.add(g.getNode(1));
        path.add(g.getNode(2));
        path.add(g.getNode(5));
        assertEquals(g1.shortestPath(1, 5), path);
        assertNotEquals(g1.shortestPath(1, 8), path);
        assertNull(g1.shortestPath(1, 11));

        //not connected
        weighted_graph gn = graph_creator_weigh_notconnected();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(gn);
        assertNull(g2.shortestPath(6, 5));
        assertNull(g2.shortestPath(-1, 20));
        ArrayList<node_info> path2 = new ArrayList<node_info>();
        path2.add(gn.getNode(1));
        path2.add(gn.getNode(2));
        path2.add(gn.getNode(4));
        assertEquals(g2.shortestPath(1, 4), path2);
    }
}
