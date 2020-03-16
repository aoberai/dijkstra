import java.util.*;

public class Graph {

    private static ArrayList<Edge> graph = new ArrayList<>();
    private static int numOfVertices = 4;

    public static void main(String[] args) {
        graph.add(new Edge(1, 2, 1));
        graph.add(new Edge(2, 3, 1));
        graph.add(new Edge(3, 4, 1));
        graph.add(new Edge(1, 2, 3));
        graph.add(new Edge(3, 4, 2));
        graph.add(new Edge(1, 4, 3));
        graph.toArray();
        System.out.println(Arrays.toString(shortestSubPathInPath()));
    }

    public static int[] shortestSubPathInPath() {
        ArrayList<Boolean> settled = new ArrayList<>();
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost-o2.cost;
            }
        });
        int currentNode = 1;
        int[] distances = new int[numOfVertices];
//        graph.add(new Edge(1,1,0));
        distanceFinder.addAll(graph);
        for (int i = 0; i < numOfVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            settled.add(false);
        }
        distances[0] = 0;
        while (distanceFinder.size() != 0) {
            Edge e = distanceFinder.poll();
            System.out.println(e);
            distances[e.node2 - 1] = e.cost;
            settled.set(e.node2 - 1, true);
            currentNode = e.node2;
            for (int i = 0; i < graph.size(); i++) {
                if (graph.get(i).node1 == currentNode) {
                    distances[graph.get(i).node2 - 1] = Math.min(graph.get(i).cost + distances[graph.get(i).node1 - 1], distances[graph.get(i).node2 - 1]);
                }
            }

        }
        return distances;
    }

    public static class Edge {
        private int node1, node2, cost;

        Edge (int node1, int node2, int cost) {
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
