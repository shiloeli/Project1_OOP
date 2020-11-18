package ex1;

import ex0.node_data;

import java.io.Serializable;
//import java.util.Collection;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
import java.util.*;

/**
 * * This implementation weighted_graph
 * * Which is literally different methods of operations on the graph  itself and on vertices in the graph
 * * It should support a large number of nodes (over 10 ^ 6, with average degree of 10).
 * *
 * *   graph-Contains all nodes in the graph using HashMap.
 * *   neighbors-Contains the neighbors of a given junction using HashMap within HashMap.
 * *   edge-Contains the weight of a side in the graph using HashMap within HashMap.
 * *   sum_edge-Represents the number of sides in the graph.
 * *   MC-Represents the number of changes made to the graph (adding a vertex and more ..).
 */
public class WGraph_DS implements weighted_graph, Comparator<node_info>, Serializable {

    private HashMap<Integer, node_info> graph;
    private HashMap<Integer, HashMap<Integer, node_info>> neighbors;
    private HashMap<Integer, HashMap<Integer, Double>> edge;
    private int sum_edge;
    private int MC;

    /**
     * A constructor that initializes the graph variables of the graph.
     */
    public WGraph_DS() {
        this.graph = new HashMap<Integer, node_info>();
        this.neighbors = new HashMap<Integer, HashMap<Integer, node_info>>();
        this.edge = new HashMap<Integer, HashMap<Integer, Double>>();
        sum_edge = 0;
        MC = 0;
    }

    /**
     * Return the node_data by the node_id.
     *
     * @param key - the node_id
     * @return node_info
     */
    @Override
    public node_info getNode(int key) {
        return graph.get(key);
    }

    /**
     * Returns true if there is a edge between node 1 and node2
     * (ie if the node are neighbor to each other).
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (graph.get(node1) == null || graph.get(node2) == null) return false;
        if (node1 == node2) return false;
        return (edge.get(node1).containsKey(node2));
    }

    /**
     * return the weight if the edge (node1, node1).
     * If there is no rib between them it will return -1.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (graph.get(node1) == null || graph.get(node2) == null) return -1;
        if (node1 == node2) return 0;
        if (neighbors.get(node1).containsKey(node2))
            return edge.get(node1).get(node2);
        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * If such a node exists, nothing will be done.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!graph.containsKey(key)) {
            node_base n = new node_base(key, " ", -1);
            HashMap<Integer, node_info> new_neighbors = new HashMap<Integer, node_info>();
            HashMap<Integer, Double> new_edge = new HashMap<Integer, Double>();
            graph.put(key, n);
            neighbors.put(key, new_neighbors);
            edge.put(key, new_edge);
            MC++;
        }
    }

    /**
     * Connecting a edge between node1 and node 2 by their key.
     * (if there is a edge between them nothing is done).
     * And the weight for the rib.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 == node2) return;
        if (graph.get(node1) == null || graph.get(node2) == null) return;
        if (w >= 0) {
            if (!neighbors.get(node1).containsKey(node2)) {
                neighbors.get(node1).put(node2, graph.get(node2));
                edge.get(node1).put(node2, w);
                neighbors.get(node2).put(node1, graph.get(node1));
                edge.get(node2).put(node1, w);
                sum_edge++;
                MC++;
            } else if (w!=getEdge(node1,node2)){
                edge.get(node1).put(node2, w);
                edge.get(node2).put(node1, w);
                MC++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     *
     * @return Collection<node_info>.
     */
    @Override
    public Collection<node_info> getV() {
        if(graph==null)return null;
        return graph.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     *
     * @param node_id
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(neighbors.get(node_id) == null) return null;
        return neighbors.get(node_id).values();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     *
     * @param key
     * @return The deleted node.
     */
    @Override
    public node_info removeNode(int key) {
        if (graph.get(key) == null) return null;
        if (!neighbors.isEmpty()) {
            node_info[] array = getV(key).toArray(new node_info[0]);
            for (int i = 0; i < array.length; i++) {
                removeEdge(key, array[i].getKey());
            }
        }
        neighbors.remove(key);
        edge.remove(key);
        MC++;
        return graph.remove(key);
    }

    /**
     * Deletes the edge from the graph that connects the two given nodes.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (graph.get(node1) == null || graph.get(node2) == null) return;
        if (node1 == node2) return;
        if (!edge.get(node1).containsValue(node2)) {
            neighbors.get(node1).remove(node2);
            neighbors.get(node2).remove(node1);
            edge.get(node1).remove(node2);
            edge.get(node2).remove(node1);
            MC++;
            sum_edge--;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     *
     * @return Number of nodes in the graph
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * return the number of edges (undirectional graph).
     *
     * @return Number of sides in the graph.
     */
    @Override
    public int edgeSize() {
        return sum_edge;
    }

    public void setSize(int s) {
        this.sum_edge = s;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     *
     * @return Number of changes made to the graph.
     */
    @Override
    public int getMC() {
        return MC;
    }

    /**
     * set a new value for the changes made to the graph.
     *
     * @param mc
     */
    public void setMC(int mc) {
        this.MC = mc;
    }

    /**
     * A method that defines a priority for a priority column.
     * In this case what is the lowest tag to the highest.
     *
     * @param n1 key
     * @param n2 key
     * @return
     */
    @Override
    public int compare(node_info n1, node_info n2) {
        if (n1.getTag() > n2.getTag())
            return 1;
        else if (n1.getTag() < n2.getTag())
            return -1;

        return 0;
    }

    /**
     * This method compares a given graph to this graph and checks if the structure of the graph is the same:
     * the graph nodes, the neighbors of the nodes and the sides
     * @param obj
     * @return True if objects are identical, else false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WGraph_DS)) {
            return false;
        }
        WGraph_DS gr = (WGraph_DS) obj;
        if (gr.edgeSize() != this.edgeSize() || gr.nodeSize() != this.nodeSize()) {

            return false;
        }
        Iterator<node_info> list = gr.getV().iterator();
        while (list.hasNext()) {
            node_info node1 = list.next();
            if (graph.get(node1.getKey()) == null) {
                return false;
            }
            if (graph.get(node1.getKey()).getTag() != node1.getTag()) {
                return false;
            }
            if (!graph.get(node1.getKey()).getInfo().equalsIgnoreCase(node1.getInfo())) {
                return false;
            }
            Iterator<node_info> Ni = gr.getV(node1.getKey()).iterator();
            while (Ni.hasNext()) {
                node_info node2 = Ni.next();
                if (neighbors.get(node1.getKey()).get(node2.getKey()) == null) {
                    return false;
                }

                if (gr.getEdge(node1.getKey(),node2.getKey())!=getEdge(node1.getKey(),node2.getKey())) {
                    return false;
                }

            }
        }
        return true;
    }


    /**
     * This implements node_info
     * that represent various actions that can be performed about node in graph,
     * key-this is unique id of node in graph.
     * tag-Specifies the weight up to the node.
     * info-Indicates if we visited the junction
     */

    private class node_base implements node_info, Serializable {

        private int key;
        private String info;
        private double tag;

        /**
         * A default constructor that Defines values for the object variable
         *
         * @param key
         * @param info
         * @param tag
         */
        public node_base(int key, String info, double tag) {
            this.key = key;
            this.info = info;
            this.tag = tag;
        }

        /**
         * Return a key associated with this node.
         *
         * @return key
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * This method returns the feature associated with this node.
         *
         * @return info
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * The method changes the info value of the node.
         *
         * @param s new info.
         */
        @Override
        public void setInfo(String s) {
            info = s;
        }

        /**
         * The method returns The weight up to the current junction
         *
         * @return tag
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * This method changes the tag value of the node.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag = t;
        }
    }
}