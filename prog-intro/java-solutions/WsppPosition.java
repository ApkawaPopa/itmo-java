import java.io.*;
import java.util.*;
import java.nio.charset.Charset;

class IntPair {
    public int f, s;

    public IntPair(int f, int s) {
        this.f = f;
        this.s = s;
    }

}

public class WsppPosition {

    public static void main(String fileNames[]) {
        try (MyScanner in = new MyScanner(new File(fileNames[0]), "utf-8");) {
            Map<String, ArrayList<IntPair>> map = new LinkedHashMap<>();
            int curPos = 0, curLine = 0;
            while (in.hasNextWord()) {
                String word = in.nextWord();
                if (curLine != in.currentLine()) {
                    curLine = in.currentLine();
                    curPos = 0;
                }
                if (!map.containsKey(word)) {
                    map.put(word, new ArrayList<>());
                }
                map.get(word).add(new IntPair(curLine + 1, ++curPos));
            }
            try (
                BufferedWriter out = new BufferedWriter(
                    new FileWriter(
                        fileNames[1],
                        Charset.forName("utf-8")
                    )
                );
            ) {
                for (Map.Entry<String, ArrayList<IntPair>> e : map.entrySet()) {
                    out.write(e.getKey());
                    ArrayList<IntPair> list = e.getValue();
                    out.write(" " + list.size());
                    for (IntPair pos : list) {
                        out.write(" " + pos.f);
                        out.write(":" + pos.s);
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
