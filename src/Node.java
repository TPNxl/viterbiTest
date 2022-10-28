public class Node {
    private final String data;

    public Node(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public boolean equals(Object o) {
        if (o instanceof Node) {
            return ((Node) o).getData().equals(data);
        }
        return false;
    }
}
