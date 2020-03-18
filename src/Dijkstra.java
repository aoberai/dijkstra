import java.util.*;

/**
 * Class that runs dijkstra on a bidirectional graph and finds the shortest path.
 * @author adityaoberai
 */
public class Dijkstra {

    private static ArrayList<Edge> graph = new ArrayList<>();
    private static int numOfVertices;
    private static int[] distances, prevDistances, parents;

    /**
     * Tester method for contest problem
     * @param args Required for java main method.
     */
    public static void main(String[] args) {
        //Population of graph
        addToGraph(new Edge(3, 4, 1));
        addToGraph(new Edge(1, 2, 3));
        addToGraph(new Edge(1, 4, 2));
        addToGraph(new Edge(1, 2, 1));
        addToGraph(new Edge(2, 3, 500));
        addToGraph(new Edge(3, 4, 2));
        numOfVertices = findNumberOfVertices();
        distances = new int[numOfVertices];
        prevDistances = new int[numOfVertices];
        parents = new int[numOfVertices];
        dijkstra();
        printSolution(distances, distances.length, parents);
    }

    /**
     * Finds the distance from the source vertex, node 1, to every other node in the graph and
     * populates the data into the distances array.
     */
    public static void dijkstra() {
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        int currentNode, iterationCount = 0;
        addToGraph(new Edge(1, 1, 0));
        distanceFinder.addAll(graph);
        for (int i = 0; i < numOfVertices; i++) {
            distances[i] = parents[i] = Integer.MAX_VALUE - 300;
        }
        distances[0] = 0;
        while (distanceFinder.size() != 0) {
            Edge u = distanceFinder.poll();
            currentNode = u.node1;
            for (Edge edge : graph) {
                if (edge.node1 == currentNode) {
                    if (distances[edge.node2 - 1] > distances[u.node1 - 1] + edge.cost) {
                        distances[edge.node2 - 1] = distances[u.node1 - 1] + edge.cost;
                        parents[edge.node2 - 1] = edge.node1;
                        distanceFinder.add(edge);
                        distanceFinder.add(new Edge(edge.node2, edge.node1, edge.cost));
                    }
                }
                if (!Arrays.equals(prevDistances, distances)) {
                    System.out.println("Iteration " + (++iterationCount) + ": " + Arrays.toString(distances));
                }
                prevDistances = distances.clone();
            }
        }
        parents[0] = -1;
    }

     /**
     * Recursively finds node path from source node to goal node and prints it out. Method credit to: https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
     * @param parent Array that stores the each nodes parent node.
     * @param j Node for which to find path.
     */
    public static void printPath(int[] parent, int j) {
        if (parent[j - 1] == -1)
            return;
        printPath(parent, parent[j - 1]);
        System.out.print(j + " ");
    }

    /**
     * Prints path to every node. Method credit to: https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
     * @param dist Array that stores distances to every node from source.
     * @param numOfVertices Number of vertices in graph.
     * @param parent Array that stores every nodes parent node in the shortest path from source to target node.
     */
    public static void printSolution(int[] dist, int numOfVertices,
                                     int[] parent) {
        int src = 1;
        System.out.println("----------------------");
        System.out.println("Vertex\t Distance\tPath");
        for (int i = 1; i <= numOfVertices; i++) {
            System.out.printf("\n%d -> %d \t\t %d\t\t%d ",
                    src, i, dist[i - 1], src);
            printPath(parent, i);
        }
    }

    /**
     * Finds the number of vertices in the graph by adding every vertex into a unique hash set
     * and finding the total number of objects in the hash set.
     * @return Number of unique vertices in graph.
     */
    public static int findNumberOfVertices() {
        HashSet<Integer> uniqueNumberFinder = new HashSet<Integer>();
        for (int i = 0; i < graph.size(); i += 2) {
            uniqueNumberFinder.add(graph.get(i).node1);
            uniqueNumberFinder.add(graph.get(i).node2);
        }
        return uniqueNumberFinder.size();
    }

    /**
     * Adds edge to graph and the edge's conjugate making the graph bidirectional.
     * @param e Edge to add to graph.
     */
    public static void addToGraph(Edge e) {
        graph.add(e);
        graph.add(new Edge(e.node2, e.node1, e.cost));
    }

    /**
     * Edge class that represents a connection between 2 nodes and the cost to travel between them.
     */
    public static class Edge {
        private int node1, node2, cost;

        /**
         * Constructor initializing edge instance with given parameters.
         * @param node1,  @param node2 2 nodes used create a a path.
         * @param cost Cost to travel path.
         */
        Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        /**
         * Represents edge class by showing all attributes of object.
         * @return String representing edge class.
         */
        @Override
        public String toString() {
            return "Edge{" +
                    "node1=" + node1 +
                    ", node2=" + node2 +
                    ", cost=" + cost +
                    '}';
        }
    }
}
