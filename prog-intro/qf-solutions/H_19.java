import java.io.*;
import java.util.*;

public class H {
    public static void main(String[] args) {
        try (MyScanner in = new MyScanner(System.in)) {
            int n = in.nextInt();
            int[] prefixSums = new int[n + 1];
            int max = 0;
            for (int i = 1; i <= n; i++) {
                prefixSums[i] = in.nextInt();
                if (max < prefixSums[i]) {
                    max = prefixSums[i];
                }
                prefixSums[i] += prefixSums[i - 1];
            }
            int[] counted = new int[prefixSums[n] + 1];
            Arrays.fill(counted, -1);
            int q = in.nextInt();
            for (int i = 0; i < q; i++) {
                int t = in.nextInt();
                if (counted[t] != -1) {
                    System.out.println(counted[t]);
                } else if (t < max) {
                    System.out.println("Impossible");
                } else {
                    int count = 0;
                    int currentTransaction = 0;
                    while (currentTransaction < n) {
                        int l = currentTransaction + 1, r = n + 1;
                        while (l + 1 < r) {
                            int m = (l + r) >> 1;
                            if (prefixSums[m] - prefixSums[currentTransaction] > t) {
                                r = m;
                            } else {
                                l = m;
                            }
                        }
                        ++count;
                        currentTransaction = l;
                    }
                    counted[t] = count;
                    System.out.println(count);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

interface Separator {
    boolean isSeparator(char c);
}

enum Types {INT, LINE, WORD, DEFAULT, NONE};

class MyScanner implements AutoCloseable {
    private Reader reader;
    private int BUFFER_SIZE = 1 << 10;
    private char buffer[];
    private int current;
    private int end;

    public MyScanner(Reader reader) throws IOException {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
        current = 0;
        end = reader.read(buffer);
    }

    public MyScanner(InputStream stream) throws IOException {
        this(stream, "utf-8");
    }

    public int nextInt() throws IOException {
        while (Character.isWhitespace(buffer[current])) {
            if (++current == end) {
                current = 0;
                end = reader.read(buffer);
            }
        }
        int res = 0, sign = buffer[current] == '-' ? -1 : 1;
        while (current < end && !Character.isWhitespace(buffer[current])) {
            res = res*10 + (buffer[current++] - '0');
            if (current == end) {
                current = 0;
                end = reader.read(buffer);
            }
        }
        return res*sign;
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}

