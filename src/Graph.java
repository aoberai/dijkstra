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
        System.out.println(shortestSubPathInPath());
    }

    public static int shortestSubPathInPath() {
        ArrayList<Boolean> settled = new ArrayList<>();
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost-o2.cost;
            }
        });
        int currentNode = 1;
        int[] distances = new int[numOfVertices];
        graph.add(new Edge(1,1,0));
        distanceFinder.addAll(graph);
        for (int i = 0; i < numOfVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            settled.add(false);
        }
        while (distanceFinder.size() != 0) {
            Edge e = distanceFinder.poll();
            System.out.println(e);
            distances[e.node2 - 1] = e.cost;
            Iterator iter = distanceFinder.iterator();
            settled.set(e.node2 - 1, true);
            while (iter.hasNext()) {
                Edge nextEdge = (Edge) iter.next();
                if (nextEdge.node1 == e.node2) {
                    int a = nextEdge.cost + distances[nextEdge.node2];
                    if (a < distances[nextEdge.node2]) {
                        distances[nextEdge.node2] = a;
                    }
                }
            }
        }
        return distances[3];
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
