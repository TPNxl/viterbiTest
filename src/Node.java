/**
 * Node: a wrapper class for a double representing a node in the trellis
 */
public class Node {
    private double value;

    public Node(double value) {
        this.value = value;
    }

    public Node() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double v) {
        value = v;
    }
}
