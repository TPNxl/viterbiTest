import java.util.ArrayList;

public class Edge {
    private final int startLayer, endLayer;
    private final Node startNode, endNode;
    private double weight;

    public Edge(int startLayer, int endLayer, Node startNode, Node endNode, double weight) {
        this.startLayer = startLayer;
        this.endLayer = endLayer;
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    public int getStartLayer() {
        return startLayer;
    }

    public int getEndLayer() {
        return endLayer;
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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static Edge minWeight(ArrayList<Edge> te) {
        Edge minEdge = new Edge(0,0,new Node(""),new Node(""),Double.MAX_VALUE);
        for(Edge t : te) {
            if (t.getWeight() < minEdge.getWeight()) {
                minEdge = t;
            }
        }
        return minEdge;
    }
}
