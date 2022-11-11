import java.util.ArrayList;

/**
 * @author Tim
 * <p>
 * Main: a class to run
 */
public class Main {
    public static void main(String[] args) {
        TrellisFileParser tfp = new TrellisFileParser("TrellisInfo.txt");
        Trellis t = tfp.parseFile();
        Path p = Viterbi.viterbiAlgorithm(t);

        if (p != null) {
            ArrayList<String> pErrors = p.verify();
            if (pErrors.size() > 0) {
                System.err.println("Errors with input data:");
                for (String err : pErrors) {
                    System.err.println(err);
                }
            }
        }

        System.out.println("Path: " + p);
    }
}
