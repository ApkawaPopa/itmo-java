import java.io.*;
import java.util.*;
import java.nio.charset.Charset;

public class WordStatWords {
    public static void main(String fileNames[]) {
        try (
            MyScanner reader = new MyScanner(new File(fileNames[0]), "utf-8");
        ) {
            Map<String, Integer> map = new TreeMap<>();
            while (reader.hasNextWord()) {
                String word = reader.nextWord();
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
            try (
                BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                        fileNames[1],
                        Charset.forName("utf-8")
                    )
                );
            ) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    writer.write(entry.getKey() + " " + entry.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("I/O error while opening input file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("I/O error while opening output file: " + e.getMessage());
        }
    }

    public static boolean isWordCharacter(char c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }
}
