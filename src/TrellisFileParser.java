import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * @author Tim
 * <p>
 * TrellisFileParser:
 * A class to parse a text file for edge information and return a Trellis
 */
public class TrellisFileParser {
    // File path information
    private String filePath;

    public TrellisFileParser(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Parse the file for information
    public Trellis parseFile() {
        Trellis t = new Trellis();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filePath));

            for (String line = bfr.readLine(); line != null; line = bfr.readLine()) {
                // Comment feature
                if (line.charAt(0) == '#') {
                    continue;
                }

                int startLayerNumber = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);

                int startNodeIndex = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);
                int endLayerNumber = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);
                int endNodeIndex = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);
                double weight = Double.parseDouble(line);

                try {
                    t.putEdge(startLayerNumber, endLayerNumber, startNodeIndex, endNodeIndex, weight);
                } catch (EdgeException e) {
                    e.printStackTrace();
                }
            }

            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Print input verification errors
        ArrayList<String> errors = t.verify();
        if (errors.size() > 0) {
            System.err.println("Errors with input data:");
            for (String err : errors) {
                System.err.println(err);
            }
        }

        return t;
    }
}
