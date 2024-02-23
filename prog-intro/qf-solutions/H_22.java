import java.io.*;
import java.util.*;

public class H {
    public static void main(String[] args) {
        try (
             MyScanner in = new MyScanner(System.in);
             Writer out = new BufferedWriter(new OutputStreamWriter(System.out));
        ) {
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
                    out.write(counted[t] + "\n");
                } else if (t < max) {
                    out.write("Impossible\n");
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
                    out.write(counted[t] + "\n");
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
    private int READ_AHEAD_LIMIT = 1 << 20;
    private char buffer[];
    private int current;
    private int end;
    private int line;
    private int checkedCurrent;
    private int checkedEnd;
    private int checkedLine;
    private Types checkedType;
    private String lineSep = System.lineSeparator();

    public MyScanner(Reader reader) throws IOException {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
        current = 0;
        end = reader.read(buffer);
        checkedType = Types.NONE;
    }

    public MyScanner(File file) throws IOException {
        this(file, "utf-8");
    }

    public MyScanner(File file, String charset) throws IOException {
        this(new InputStreamReader(new FileInputStream(file), charset));
    }

    public MyScanner(InputStream stream) throws IOException {
        this(stream, "utf-8");
    }

    public MyScanner(InputStream stream, String charset) throws IOException {
        this(new InputStreamReader(stream, charset));
    }

    public MyScanner(String str) throws IOException {
        this(new StringReader(str));
    }

    public int currentLine() {
        return line;
    }

    private boolean isLineSep(char c1, char c2) {
        return  lineSep.length() == 1 && c2 == lineSep.charAt(0) ||
                lineSep.length() == 2 && c2 == lineSep.charAt(1) &&
                c1 == lineSep.charAt(0);
    }

    private boolean hasNext(Separator sep, Types type) throws IOException {
        if (checkedType == type) {
            return checkedCurrent < checkedEnd;
        }
        checkedType = type;
        checkedCurrent = current;
        checkedEnd = end;
        checkedLine = line;
        char lastSym = 'a';
        boolean moreThanLimit = false;
        while (checkedCurrent < checkedEnd) {
            if (!sep.isSeparator(buffer[checkedCurrent])) {
                if (moreThanLimit) {
                    end = checkedEnd;
                }
                return true;
            }
            if (isLineSep(lastSym, buffer[checkedCurrent])) {
                checkedLine++;
            }
            lastSym = buffer[checkedCurrent];
            checkedCurrent++;
            if (checkedCurrent == checkedEnd) {
                if (buffer.length == READ_AHEAD_LIMIT) {
                    moreThanLimit = true;
                    if (checkedEnd + BUFFER_SIZE >= buffer.length) {
                        checkedCurrent = checkedEnd = current = 0;
                        line = checkedLine;
                    }
                } else {
                    buffer = Arrays.copyOf(buffer, buffer.length * 2);
                }
                checkedEnd += reader.read(buffer, checkedEnd, BUFFER_SIZE);
            }
        }
        if (moreThanLimit) {
            end = checkedEnd + 1;
        }
        return false;
    }

    private String next(Separator sep, Types type) throws IOException {
        hasNext(sep, type);
        current = checkedCurrent;
        end = checkedEnd;
        line = checkedLine;
        char token[] = new char[1];
        int tokenLen = 0;
        while (current < end && !sep.isSeparator(buffer[current])) {
            if (token.length == tokenLen) {
                token = Arrays.copyOf(token, tokenLen * 2);
            }
            token[tokenLen++] = buffer[current];
            current++;
            if (current == end) {
                if (buffer.length != BUFFER_SIZE) {
                    buffer = Arrays.copyOf(buffer, BUFFER_SIZE);
                }
                current = 0;
                end = reader.read(buffer);
            }
        }
        checkedType = Types.NONE;
        return String.valueOf(Arrays.copyOf(token, tokenLen));
    }

    private Separator defaultSep = x -> Character.isWhitespace(x);

    public boolean hasNext() throws IOException {
        return hasNext(defaultSep, Types.DEFAULT);
    }

    public String next() throws IOException {
        return next(defaultSep, Types.DEFAULT);
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    private Separator wordSep = x -> !(
        Character.getType(x) == Character.DASH_PUNCTUATION ||
        Character.isLetter(x) ||
        x == '\''
    );

    public boolean hasNextWord() throws IOException {
        return hasNext(wordSep, Types.WORD);
    }

    public String nextWord() throws IOException {
        return next(wordSep, Types.WORD).toLowerCase();
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
