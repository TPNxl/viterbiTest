import java.util.ArrayList;

public class Trellis {
    private ArrayList<Edge> trEdges;
    private ArrayList<Layer> layers;

    public Trellis() {
        trEdges = new ArrayList<>();
        layers = new ArrayList<>();
    }

    public void putNode(Node n, int layer) throws NodeException {
        while (layers.size() < layer + 1) {
            layers.add(new Layer());
        }
        layers.get(layer).put(n);
    }

    public void removeNode(Node n, int layer) throws NodeException {
        int indexInLayer = layers.get(layer).indexOf(n);
        layers.get(layer).remove(n);
        for(Edge e : getEdgesStartingAt(layer, n)) {
            layers.remove(e);
        }
        for(Edge e : getEdgesEndingAt(layer, n)) {
            layers.remove(e);
        }
    }

    public Node getNode(int layer, int index) {
        return layers.get(layer).get(index);
    }

    public void close() {
        trEdges = null;
        layers = null;
    }

    public void putEdge(Edge e) throws EdgeException {
        if (!trEdges.contains(e)) {
            throw new EdgeException("Edge to be added exists in trellis");
        }
        trEdges.add(e);
    }

    public Edge getEdge(int startLayer, Node startNode, int endLayer, Node endNode) throws EdgeException {
        for (Edge e : trEdges) {
            if(e.getStartLayer() == startLayer && e.getStartNode().equals(startNode) && e.getEndLayer() == endLayer && e.getEndNode().equals(endNode)) {
                return e;
            }
        }
        throw new EdgeException("Edge not found");
    }

    public ArrayList<Edge> getEdgesStartingAt(int startLayer, Node startNode) {
        ArrayList<Edge> out = new ArrayList<>();
        for (Edge e : trEdges) {
            if(e.getStartLayer() == startLayer && e.getStartNode().equals(startNode)) {
                out.add(e);
            }
        }
        return out;
    }

    public ArrayList<Edge> getEdgesEndingAt(int endLayer, Node endNode) {
        ArrayList<Edge> out = new ArrayList<>();
        for (Edge e : trEdges) {
            if(e.getEndLayer() == endLayer && e.getEndNode().equals(endNode)) {
                out.add(e);
            }
        }
        return out;
    }

    public void removeEdge(Edge e) throws EdgeException {
        if (!trEdges.contains(e)) {
            throw new EdgeException("Edge to be removed does not exist in trellis");
        }
        trEdges.remove(e);
    }
}
