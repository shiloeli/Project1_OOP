# Project1_OOP

ex1 Readme

This project deals with the construction of undirectional weighted graph starting from the creation of the nodes and edges in the graph, continues with the creation of the graph itself (such as connecting the nodes in the graph and more) and basic algorithms related to the graph (paths in the parent link graph and more).
A graph is made up of three interfaces arranged according to the hierarchy from the creation of a node to the execution of an algorithm on the graph.<br>


**Introduction:**<br>

 *node_base class:*<br>
This implements node_info node in a graph consists of a<br>
unique key-identifier of each node.<br>
info- some property of the node This project used info to tell if made passed the node in algorithms made on the graph.<br>
tag- Defines the distance from a given node to the current node, this value can vary accordingly.<br>

 *Geaph_DS class:*<br>
This implementation weighted_graph
This class represents a graph
And various actions related to graph construction.<br>
A graph is made up of a <br>
graph- which contains all the nodes in the graph.<br>
neighbors- contains the neighbors of each node respectively.<br>
edge- contains the edges that are connected to each node respectively.<br>
sum_edge- Represents the number of sides in the graph.<br>
MC- Represents the number of changes made to the graph (adding a vertex and more ..).<br>

 *Graph_Algo class:*<br>
This class represents a number of algorithms that can be made on a graph<br>


**Now a nice explanation is given of how a graph building process is done**

will start by building a node in graph:

**In the node_base class can build a node and perform operations on it by the methods in the class:**<br>

node_base- a default builder.<br>
getKey- get the id.<br>
getInfo- get the info.<br>
setInfo- set info.<br>
getTag- get the tag.<br>
setTag- set the tag.<br>

*Code in java*

```bash
// Making a new graph
WGraph_DS graph=new WGraph_DS();
// graph.add
graph.addNode(1);
```

![new node](http://up419.siz.co.il/up2/gz1o0tnqyimt.jpg)



**Now that have created the first node in the Wgraph_DS class can create a graph using the following methods:**

WGraph_DS- Build a graph.<br>
getNode- Get a node from the graph.<br>
hasEdge- Check if the edge exists.<br>
getEdge- Get the side value.<br>
addNode- Adding a node.<br>
connect- Connecting 2 nodes to the edge.<br>
getV- Receives the collection of nodes.<br>
getV (int node_id )- Receive the neighbors collection.<br>
removeNode- Deletes a node.<br>
removeEdge- Delete edge.<br>

*Code in java* 

```bash
//remove neighbor
graph.removeNode(3);
```

![add node](http://up419.siz.co.il/up3/wfzkykzgt2wy.jpg)

*Code in java*

```bash
//connect 2 node
graph.connect(1,2,4);
//Delete a edge
graph.removeEdge(1,3);
```

![remove edge](http://up419.siz.co.il/up3/rmkwjl2hymnd.jpg)


**Will now describe some algorithms that can be made in the Wgraph_Algo class:**

init- Creates a pointer to the graph.<br>
getGraph- Get graph nodes.<br>
copy- Create a copy of the graph.<br>
isConnected- Check if the graph is linked.<br>
shortestPathDist- Get the value of the shortest path from node to node.<br>
shortestPath- Get the shortest route.<br>

*Code in java*

```bash
//Get the shortest route
graph.shortestPath(src,dest);
```

![shortespath](http://up419.siz.co.il/up2/g2myrzyt2mih.jpg)<br>



Author @Shilo Elimelech

