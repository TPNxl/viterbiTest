import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.zip.DataFormatException;

/**
 * @author Tim
 * <p>
 * Trellis: an object for a trellis, an ordered graph with nodes and edges
 */
public class Trellis {
    protected ArrayList<Edge> edges;
    protected ArrayList<Layer> layers;

    // Constructors/Closers
    public Trellis() {
        edges = new ArrayList<>();
        layers = new ArrayList<>();
    }

    private Trellis(ArrayList<Edge> edges, ArrayList<Layer> layers) {
        this.edges = edges;
        this.layers = layers;
    }

    public Trellis duplicate() {
        return new Trellis(new ArrayList<>(edges), new ArrayList<>(layers));
    }

    public void close() {
        edges = null;
        layers = null;
    }

    // Global trellis operations

    /**
     * verify(): verifies trellis data to make sure it's valid
     */
    public ArrayList<String> verify() {
        ArrayList<String> errors = new ArrayList<>();
        for (Layer l : layers) {
            if (l.nodes.size() == 0) {
                errors.add(String.format("Layer %d in trellis has no nodes", layers.indexOf(l)));
            }
        }
        for (Edge e : edges) {
            if (e.getStartLayerIndex() >= e.getEndLayerIndex()) {
                errors.add(String.format("%s has invalid layerIndex values", e));
            }
            if (e.getStartLayerIndex() >= layers.size() || e.getEndLayerIndex() >= layers.size()) {
                errors.add(String.format("%s references layers that don't exist", e));
            }
            if (!layers.get(e.getStartLayerIndex()).nodes.contains(e.getStartNode())) {
                errors.add(String.format("%s references start node that doesn't exist", e));
            }
            if (!layers.get(e.getEndLayerIndex()).nodes.contains(e.getEndNode())) {
                errors.add(String.format("%s references end node that doesn't exist", e));
            }
        }
        return errors;
    }

    // No editing of nodes needed

    // Edge operations
    public Edge getEdge(int startLayerNumber, int endLayerNumber, int startNodeIndex, int endNodeIndex) throws InputMismatchException {
        if (endLayerNumber <= startLayerNumber) {
            throw new InputMismatchException("Invalid getEdge layerNumber input");
        }
        for (Edge e : edges) {
            if (e.getStartLayerIndex() == startLayerNumber && e.getEndLayerIndex() == endLayerNumber &&
                    layers.get(e.getStartLayerIndex()).nodes.indexOf(e.getStartNode()) == startNodeIndex &&
                    layers.get(e.getStartLayerIndex()).nodes.indexOf(e.getEndNode()) == endNodeIndex) {
                return e;
            }
        }
        return null;
    }

    public Edge getEdge(int startLayerNumber, int endLayerNumber, Node startNode, Node endNode) throws EdgeException {
        for (Edge e : edges) {
            if (e.getStartLayerIndex() == startLayerNumber && e.getStartNode().equals(startNode) &&
                    e.getEndLayerIndex() == endLayerNumber && e.getEndNode().equals(endNode)) {
                return e;
            }
        }
        throw new EdgeException("Edge not found");
    }

    public ArrayList<Edge> getEdgesStartingAt(int startLayer, Node startNode) {
        ArrayList<Edge> out = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getStartLayerIndex() == startLayer && e.getStartNode().equals(startNode)) {
                out.add(e);
            }
        }
        return out;
    }

    public ArrayList<Edge> getEdgesEndingAt(int endLayer, Node endNode) {
        ArrayList<Edge> out = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getEndLayerIndex() == endLayer && e.getEndNode().equals(endNode)) {
                out.add(e);
            }
        }
        return out;
    }

    public ArrayList<Edge> getEdgesEndingAt(Layer endLayer, Node endNode) {
        return getEdgesEndingAt(layers.indexOf(endLayer), endNode);
    }

    public void putEdge(Edge e) throws EdgeException {
        if (!edges.contains(e)) {
            throw new EdgeException("Edge to be added exists in trellis");
        }
        edges.add(e);
    }

    public void putEdge(int startLayerIndex, int endLayerIndex, int startNodeIndex, int endNodeIndex, double weight) throws EdgeException {
        if (getEdge(startLayerIndex, endLayerIndex, startNodeIndex, endNodeIndex) != null) {
            throw new EdgeException("Edge to be added exists in trellis");
        }

        // Populate layers
        while (layers.size() < endLayerIndex + 1) {
            layers.add(new Layer());
        }

        // Populate start nodes
        Layer startLayer = layers.get(startLayerIndex);
        while (startLayer.nodes.size() < startNodeIndex + 1) {
            startLayer.nodes.add(new Node());
        }

        // Populate end nodes
        Layer endLayer = layers.get(endLayerIndex);
        while (endLayer.nodes.size() < endNodeIndex + 1) {
            endLayer.nodes.add(new Node());
        }

        edges.add(new Edge(startLayerIndex, endLayerIndex, startLayer.get(startNodeIndex), endLayer.get(endNodeIndex), weight));
    }

    public void removeEdge(Edge e) throws EdgeException {
        if (!edges.contains(e)) {
            throw new EdgeException("Edge to be removed does not exist in trellis");
        }
        edges.remove(e);
    }

    // Layer operations
    public void replaceLayer(Layer oldLayer, Layer l) {
        layers.set(layers.indexOf(oldLayer), l);
    }

    public boolean layerHasNext(Layer l) {
        try {
            layers.get(layers.indexOf(l) + 1);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public Layer nextLayer(Layer l) {
        try {
            return layers.get(layers.indexOf(l) + 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
