import java.util.*;

public class Graph {

    private static ArrayList<Edge> graph = new ArrayList<>();
    private static int numOfVertices = 5;

    public static void main(String[] args) {
        graph.add(new Edge(1, 2, 3));
        System.out.println(shortestSubPathInPath());
    }

    public static int shortestSubPathInPath() {
        HashSet<Integer> settled = new HashSet<>();
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>();
        int source = 0;
        int currentNode = source;
        graph.add(new Edge(0,0,0));
        int[] distances = new int[numOfVertices];
        for (Edge e : graph) {
            if (e.node1 != currentNode) {
                distances[e.node2] = Integer.MAX_VALUE;
            }
            distanceFinder.add(e);
        }
        distances[source] = 0;
        while (distanceFinder.size() != 0) {
            Edge e = distanceFinder.poll();
            distances[e.node2] = e.cost;
        }
        return distances[1];
    }

    public static class Edge implements Comparator<Edge> {
        private int node1, node2, cost;
        Edge (int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        public int getNode1() {
            return node1;
        }

        public int getNode2() {
            return node2;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.cost - o2.cost;
        }
    }
}
