import java.util.*;

public class Graph {

    private static ArrayList<Edge> graph = new ArrayList<>();
    private static int numOfVertices;

    public static void main(String[] args) {
        addToGraph(new Edge(3, 4, 1));
        addToGraph(new Edge(1, 2, 3));
        addToGraph(new Edge(1, 4, 2));
        addToGraph(new Edge(1, 2, 1));
        addToGraph(new Edge(2, 3, 500));
        addToGraph(new Edge(3, 4, 2));
        numOfVertices = findNumberOfVertices();
        shortestSubPathInPath();
    }

    public static void shortestSubPathInPath() {
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        int currentNode, iterationCount = 0;
        int[] distances = new int[numOfVertices];
        int[] prevDistances = new int[numOfVertices];
        int[] parents = new int[numOfVertices];
        addToGraph(new Edge(1, 1, 0));
        distanceFinder.addAll(graph);
        for (int i = 0; i < numOfVertices; i++) {
            distances[i] = Integer.MAX_VALUE - 300;
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
        System.out.println("----------------------");
        printSolution(distances, distances.length, parents);
    }

    public static void printPath(int[] parent, int j) {
        if (parent[j - 1] == -1)
            return;

        printPath(parent, parent[j - 1]);
        System.out.print(j + " ");
    }

    public static void printSolution(int[] dist, int V,
                                     int[] parent) {
        int src = 1;
        System.out.println("Vertex\t Distance\tPath");
        for (int i = 1; i <= V; i++) {
            System.out.printf("\n%d -> %d \t\t %d\t\t%d ",
                    src, i, dist[i - 1], src);
            printPath(parent, i);
        }
    }

    public static int findNumberOfVertices() {
        HashSet<Integer> uniqueNumberFinder = new HashSet<Integer>();
        for (int i = 0; i < graph.size(); i += 2) {
            uniqueNumberFinder.add(graph.get(i).node1);
            uniqueNumberFinder.add(graph.get(i).node2);
        }
        return uniqueNumberFinder.size();
    }


    public static void addToGraph(Edge e) {
        graph.add(e);
        graph.add(new Edge(e.node2, e.node1, e.cost));
    }

    public static class Edge {
        private int node1, node2, cost;

        Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

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
