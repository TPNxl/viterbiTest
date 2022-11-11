import java.util.ArrayList;

/**
 * Edge: an object class for a trellis edge
 */
public class Edge {
    public static Edge minWeight(ArrayList<Edge> te) {
        Edge minEdge = new Edge(0, 0, new Node(), new Node(), Double.MAX_VALUE);
        for (Edge t : te) {
            if (t.getWeight() < minEdge.getWeight()) {
                minEdge = t;
            }
        }
        return minEdge;
    }

    // Object fields
    private final int startLayerIndex, endLayerIndex;
    private final Node startNode, endNode;
    private final double weight;

    // Constructor
    public Edge(int startLayerIndex, int endLayerIndex, Node startNode, Node endNode, double weight) {
        this.startLayerIndex = startLayerIndex;
        this.endLayerIndex = endLayerIndex;
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    // Edge recall ops
    public int getStartLayerIndex() {
        return startLayerIndex;
    }

    public int getEndLayerIndex() {
        return endLayerIndex;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public double getWeight() {
        return weight;
    }

    // Misc
    public String toString() {
        return String.format("Edge<(%d,%s)->(%d,%s), weight=%f>",
                startLayerIndex, startNode, endLayerIndex, endNode, weight);
    }
}