package ex1;

import java.io.*;
import java.util.*;

/**
 * This class implement weighted_graph_algorithms. that implement an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 * <p>
 * graphW- Is an abstract representation of a set of nodes,
 * each side has a weight,
 * where each pair of node in the set may be linked together.
 */

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph graphW;

    /**
     * Init the graph on which this set of algorithms operates on, by pointing to a new object.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graphW = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return this.graphW
     */
    @Override
    public weighted_graph getGraph() {
        return this.graphW;
    }

    /**
     * Compute a deep copy of this weighted graph,
     * By going through all the nodes in the graph using an iterator.
     * and creating a list of neighbors and sides corresponding to each node using iterator.
     *
     * @return copy(graphW).
     */
    @Override
    public weighted_graph copy(){
        WGraph_DS copy = new WGraph_DS();

        Iterator<node_info> c = graphW.getV().iterator();
        while (c.hasNext()) {
            node_info temp = c.next();
            copy.addNode(temp.getKey());
            copy.getNode(temp.getKey()).setInfo(temp.getInfo());
            copy.getNode(temp.getKey()).setTag(temp.getTag());
        }
        Iterator<node_info> ni = copy.getV().iterator();
        while (ni.hasNext()) {
            node_info temp2 = ni.next();
            Iterator<node_info> ni2 = graphW.getV(temp2.getKey()).iterator();
            while (ni2.hasNext()) {
                node_info temp3 = ni2.next();
                copy.connect(temp2.getKey(), temp3.getKey(), graphW.getEdge(temp2.getKey(), temp3.getKey()));
            }
        }
        copy.setSize(graphW.edgeSize());
        if (copy.getMC() != graphW.getMC()) {
            copy.setMC(graphW.getMC());
        }
        return copy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each,
     * by checking if there is a vertex in the graph
     * that the method did not reach him from all other vertex.
     *
     * Checking is done by selecting 2 nodes using an iterator and calling the shortestPathDist method () with these nodes.
     * This method checks if there is a proper trajectory from the src to the dest by marking all the nodes that they have a proper trajectory from src.
     * Then by switching with an iterator a check is made if there is a vertex in the graph marked as "not visited" if there is one meaning that there
     * is no path from any node to any node and the graph is not linked.
     *
     * @return true if the graph is linked, otherwise false.
     */
    @Override
    public boolean isConnected() {
        if (graphW.nodeSize() == 0 || graphW.nodeSize() == 1) return true;
        Iterator<node_info> random = graphW.getV().iterator();
        shortestPathDist(random.next().getKey(), random.next().getKey());
        Iterator<node_info> test_info = graphW.getV().iterator();
        while (test_info.hasNext()) {
            node_info temp3 = test_info.next();
            if (temp3.getInfo().equalsIgnoreCase("not visited"))
                return false;
        }
        return true;
    }

    /**
     * Returns the shortest path length between src and dest
     * by implementing a dijkstra algorithm.
     * if there is no such path -> returns -1.
     *
     * The algorithm gets the start and destination value and first goes over all the nodes in the graph using an iterator
     * and updates the tag value to Integer.Max_Value and the info of each node to "not visited".
     * Then insert the source node into the PriorityQueue structure which allows to say priority (this priority was set
     * by the comperator in the DS_Wgraph class) and thus arrange the node with the shortest route at the top of the queue.
     * The algorithm then passes over neighbors by using iterator of each vertex starting from src and removes
     * from the queue the neighbor with the shortest route and marked with "visited".
     * Which ensures that we will not be able to reach every junction with a shorter route.
     * Will finally return the tag value of the destination node.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return The length of the path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graphW.getNode(src) == null || graphW.getNode(dest) == null) return -1;
        if (src == dest) return 0;

        PriorityQueue<node_info> Q = new PriorityQueue<node_info>(new WGraph_DS());
        Iterator<node_info> max = graphW.getV().iterator();
        while (max.hasNext()) {
            node_info temp = max.next();
            temp.setTag(Integer.MAX_VALUE);
            temp.setInfo("not visited");
        }
        graphW.getNode(src).setTag(0);
        Q.add(graphW.getNode(src));
        Q.peek().setInfo("visited");
        while (!Q.isEmpty()) {
            Iterator<node_info> weight_edge = graphW.getV(Q.peek().getKey()).iterator();
            node_info prev = Q.peek();
            Q.poll();
            while (weight_edge.hasNext()) {
                node_info temp2 = weight_edge.next();
                if (temp2.getTag() > prev.getTag() + graphW.getEdge(prev.getKey(), temp2.getKey())) {
                    temp2.setTag(prev.getTag() + graphW.getEdge(prev.getKey(), temp2.getKey()));
                    Q.add(temp2);
                    temp2.setInfo("visited");
                }
            }
        }
        if (graphW.getNode(dest).getTag() != Integer.MAX_VALUE)
            return graphW.getNode(dest).getTag();

        return -1;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest.
     * By pass the shortest path from the end to the beginning.
     * If no such path exists return null.
     *
     * The method sends the given values of src and dest to the shortestPathDist method (),
     * and adds to the ArrayList the nodes of the corresponding path by passing iterator
     * on neighbors of each node starting from dest, since the nodes were added from the end to the beginning,
     * a new ArrayList was set (which we return) From beginning to end.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return The short path, if it does not exist will return null.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (graphW.getNode(src) == null || graphW.getNode(dest) == null) return null;

        shortestPathDist(src, dest);
        if (graphW.getNode(dest).getTag() == Integer.MAX_VALUE) return null;
        ArrayList<node_info> path = new ArrayList<node_info>();
        int dest2 = dest;
        path.add(graphW.getNode(dest2));
        while (true) {
            Iterator<node_info> ni = graphW.getV(dest2).iterator();
            while (ni.hasNext()) {
                node_info temp = ni.next();
                if (graphW.getNode(dest2).getTag() == temp.getTag() + (graphW.getEdge(temp.getKey(), dest2))) {
                    path.add(temp);
                    dest2 = temp.getKey();
                }
            }
            if (graphW.getNode(dest2).getTag() == 0) break;
        }

        ArrayList<node_info> pathNew = new ArrayList<node_info>();
        int j = 0;
        for (int i = path.size() - 1; i >= 0; i--) {
            pathNew.add(j++, path.get(i));
        }
        return pathNew;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name.
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved.
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream update = new FileOutputStream(file);
            ObjectOutputStream File = new ObjectOutputStream(update);
            File.writeObject(graphW);
            update.close();
            File.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream upload = new FileInputStream(file);
            ObjectInputStream update = new ObjectInputStream(upload);
            graphW = (WGraph_DS) update.readObject();
            update.close();
            upload.close();
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        return true;

    }

}
