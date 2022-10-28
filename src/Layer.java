import java.util.ArrayList;

public class Layer {
    private final ArrayList<Node> nodes;

    public Layer(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Layer() {
        this.nodes = new ArrayList<>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node get(int index) {
        return nodes.get(index);
    }

    public int indexOf(Node n) throws NodeException {
        if (nodes.contains(n)) {
            return nodes.indexOf(n);
        }
        for (Node n2 : nodes) {
            if (n2.equals(n)) {
                return nodes.indexOf(n2);
            }
        }
        throw new NodeException("Node not found");
    }

    public void put(Node n) throws NodeException {
        if (nodes.contains(n)) {
            throw new NodeException("Node to be added already exists in layer");
        }
        nodes.add(n);
    }

    public void remove(Node n) throws NodeException {
        if (!nodes.contains(n)) {
            throw new NodeException("Node to be removed doesn't exist in layer");
        }
        nodes.set(nodes.indexOf(n), null);
    }
}
