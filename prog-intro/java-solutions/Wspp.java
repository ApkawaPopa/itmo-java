import java.io.*;
import java.util.*;
import java.nio.charset.Charset;

public class Wspp {

    public static void main(String fileNames[]) {
        try (MyScanner in = new MyScanner(new File(fileNames[0]), "utf-8");) {
            Map<String, ArrayList<Integer>> map = new LinkedHashMap<>();
            int curPos = 0;
            while (in.hasNextWord()) {
                String word = in.nextWord();
                if (!map.containsKey(word)) {
                    map.put(word, new ArrayList<>());
                }
                map.get(word).add(++curPos);
            }
            try (
                BufferedWriter out = new BufferedWriter(
                    new FileWriter(
                        fileNames[1],
                        Charset.forName("utf-8")
                    )
                );
            ) {
                for (Map.Entry<String, ArrayList<Integer>> e : map.entrySet()) {
                    out.write(e.getKey());
                    out.write(" " + e.getValue().size());
                    for (int pos : e.getValue()) {
                        out.write(" " + pos);
                    }
                    out.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error while opening output file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error while opening input file: " + e.getMessage());
        }
    }

}
