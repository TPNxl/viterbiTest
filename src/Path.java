import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.DataFormatException;

/**
 * Path: a wrapper class for a list of edges representing a path from one side of the trellis to the other
 */
public class Path {
    private final LinkedList<Edge> p;
    private final double cost;

    public Path(LinkedList<Edge> p, double cost) {
        this.p = p;
        this.cost = cost;
    }

    public LinkedList<Edge> getPath() {
        return p;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<String> verify() {
        ArrayList<String> errors = new ArrayList<>();
        if (p.size() < 2) {
            errors.add("Too few Edges in path list");
        } else {
            Edge e = p.poll();
            if (!(e.getEndLayerIndex() == e.getStartLayerIndex() + 1)) errors.add(String.format("%s jumps layers", e));
            while (p.size() > 0) {
                Edge newE = p.poll();
                if (newE.getStartLayerIndex() == e.getEndLayerIndex())
                    errors.add(String.format("%s has invalid layerIndex values in its path", e));
                if (!(newE.getEndLayerIndex() == newE.getStartLayerIndex() + 1))
                    errors.add(String.format("%s jumps layers", e));
            }
        }
        return errors;
    }

    public String toString() {
        StringBuilder pathString = new StringBuilder();
        for (Edge e : p) {
            pathString.append(e.toString());
            if (e != p.getLast()) {
                pathString.append(",");
            }
        }
        return String.format("Path<cost=%f, path=<%s>>", cost, pathString);
    }

}
