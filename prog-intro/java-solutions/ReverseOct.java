import java.util.*;
import java.io.*;

public class ReverseOct {
    public static void main(String args[]) {
        try (MyScanner inputScanner = new MyScanner(System.in);) {
            String numbers[][] = new String[1][];
            int curLine = 0;
            boolean valCheck = inputScanner.hasNext();
            String val = inputScanner.next();
            while (valCheck) {
                if (curLine == numbers.length) {
                    numbers = Arrays.copyOf(numbers, numbers.length * 2);
                }
                numbers[curLine] = new String[1];
                int curColumn = 0;
                while (curLine == inputScanner.currentLine() && valCheck) {
                    if (curColumn == numbers[curLine].length) {
                        numbers[curLine] = Arrays.copyOf(numbers[curLine], numbers[curLine].length * 2);
                    }
                    numbers[curLine][curColumn] = val;
                    curColumn++;
                    valCheck = inputScanner.hasNext();
                    val = inputScanner.next();
                }
                numbers[curLine] = Arrays.copyOf(numbers[curLine], curColumn);
                curLine++;
            }
            numbers = Arrays.copyOf(numbers, inputScanner.currentLine());
            Arrays.fill(numbers, curLine, numbers.length, new String[0]);
            for (int i = numbers.length - 1; i >= 0; i--) {
                for (int j = numbers[i].length - 1; j >= 0; j--) {
                    System.out.print(numbers[i][j] + " ");
                }
                System.out.println();
            }
        } catch(IOException e) {
            System.err.println("Error while using MyScanner: " + e.getMessage());
        }
    }
}
