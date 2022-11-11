import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Tim
 * <p>
 * Viterbi:
 * A class containing an implementation of the Viterbi algorithm for the Trellis object class
 */
public class Viterbi {
    /**
     * viterbiAlgorithm: performs Viterbi algorithm on an input trellis
     * reference video: https://www.youtube.com/watch?v=6JVqutwtzmo
     * @param trellis: the input trellis (duplicated)
     * @return a LinkedList of Edges representing the lowest cost path
     */
    public static Path viterbiAlgorithm(Trellis trellis) {
        Trellis t = trellis.duplicate();
        for (Layer l : t.layers) {
            // Run algorithm until you get to the last layer
            if (t.layerHasNext(l)) {
                for (Node n : l.nodes) {
                    ArrayList<Edge> edges = t.getEdgesEndingAt(l, n);

                    // Make an ArrayList with the following contents:
                    // Edge object (startNode, endNode contained in the Edge object), cost
                    ArrayList<EdgeCost> eCost = new ArrayList<>();
                    for (Edge e : edges) {
                        eCost.add(new EdgeCost(e, e.getWeight() + e.getStartNode().getValue()));
                    }
                    // Find the minimum cost
                    EdgeCost minEdgeCost = new EdgeCost(null, Double.MAX_VALUE);
                    for (EdgeCost e : eCost) {
                        if (e.cost < minEdgeCost.cost) {
                            minEdgeCost = e;
                        }
                    }

                    // Set node value
                    Edge minEdge = minEdgeCost.edge;
                    minEdge.getEndNode().setValue(minEdgeCost.cost);

                    // Remove ancillary edges
                    for (Edge e : t.edges) {
                        if (e.getEndNode() != minEdge.getEndNode() && !(e == minEdgeCost.edge)) {
                            t.edges.remove(e);
                        }
                    }
                }
            }
        }

        // Find lowest cost node in last layer
        Layer lastLayer = t.layers.get(t.layers.size() - 1);
        double minCost = Double.MAX_VALUE;
        Node minCostNode = null;
        for (Node n : lastLayer.nodes) {
            if (n.getValue() < minCost) {
                minCostNode = n;
                minCost = n.getValue();
            }
        }

        // Back propagate node path through edges to get the final path
        LinkedList<Edge> ln = new LinkedList<>();
        for (int i = t.layers.size() - 1; i >= 0; i--) {
            ArrayList<Edge> ae = t.getEdgesEndingAt(t.layers.get(i), minCostNode);
            if (ae.size() == 0) {
                System.err.println("Missing edge in path reconstruction");
                return null;
            } else if (ae.size() > 1) {
                System.err.println("Too many edges in path reconstruction");
                return null;
            }
            ln.add(ae.get(0));
            minCostNode = ae.get(0).getStartNode();
        }

        return new Path(ln, minCost);
    }
}
