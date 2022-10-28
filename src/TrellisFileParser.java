import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class TrellisFileParser {
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

    public Trellis parseFile() throws Exception {
        Trellis t = new Trellis();
        File f = new File(filePath);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        Scanner sc;

        // Read in nodes
        while(true) {
            String line = bfr.readLine();
            if(line.equals("---")) {
                break;
            }
            sc = new Scanner(line);

            int layerNumber = sc.nextInt();
            String nodeContents = sc.nextLine();

            t.putNode(new Node(nodeContents), layerNumber);
        }

        // Read in edges

        while(true) {
            String line = bfr.readLine();
            if(line.equals("---")) {
                break;
            }
            sc = new Scanner(line);

            int startLayerNumber = sc.nextInt();
            String startNodeContents = sc.nextLine();
            int endLayerNumber = sc.nextInt();
            String endNodeContents = sc.nextLine();
            double weight = sc.nextDouble();

            t.putEdge(new Edge(startLayerNumber, endLayerNumber, new Node(startNodeContents), new Node(endNodeContents), weight));
        }

        bfr.close();
        fr.close();

        return t;
    }
}
