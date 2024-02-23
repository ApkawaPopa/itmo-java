import java.io.*;
import java.util.*;

public class K {
    static void setKingdom(char a[][], int l, int r, int u, int d) {
        int firstRow = -1;
        for (int i = u; firstRow == -1 && i <= d; i++) {
            for (int j = l; j <= r; j++) {
                if (a[i][j] != '.') {
                    firstRow = i;
                }
            }
        }
        if (firstRow == -1) {
            return;
        }
        for (int i = firstRow; i <= d; i++) {
            char king = '.';
            for (int j = l; j <= r; ++j) {
                if (a[i][j] != '.') {
                    king = Character.toLowerCase(a[i][j]);
                    break;
                }
            }
            if (king == '.') {
                for (int j = l; j <= r; j++) {
                    a[i][j] = Character.toLowerCase(a[i - 1][j]);
                }
            } else {
                for (int j = l; j <= r; j++) {
                    if (a[i][j] != '.') {
                        king = Character.toLowerCase(a[i][j]);
                    } else {
                        a[i][j] = king;
                    }
                }
            }
        }
        for (int i = firstRow - 1; i >= u; i--) {
            for (int j = l; j <= r; j++) {
                a[i][j] = Character.toLowerCase(a[i + 1][j]);
            }
        }
    }

    public static void main(String[] args) {
        try (
            MyScanner in = new MyScanner(System.in);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        ) {
            int n = in.nextInt();
            int m = in.nextInt();
            char a[][] = new char[n][m];
            int Ai = 0;
            int Aj = 0;
            for (int i = 0; i < n; i++) {
                String curLine = in.next();
                for (int j = 0; j < m; j++) {
                    a[i][j] = curLine.charAt(j);
                    if (a[i][j] == 'A') {
                        Ai = i;
                        Aj = j;
                    }
                }
            }
            int[] firstNotFree = new int[m];
            Arrays.fill(firstNotFree, -1);
            int[] dpL = new int[m];
            int[] stack = new int[m];
            int sz;
            int res = 0;
            int l = -1;
            int r = -1;
            int u = -1;
            int d = -1;
            for (int i = 0; i < n; i++) {
                sz = 0;
                for (int j = 0; j < m; j++) {
                    if (a[i][j] > 'A') {
                        firstNotFree[j] = i;
                    }
                    while (sz != 0 && firstNotFree[stack[sz - 1]] <= firstNotFree[j]) {
                        sz--;
                    }
                    dpL[j] = sz != 0 ? stack[sz - 1] : -1;
                    stack[sz++] = j;
                }
                sz = 0;
                for (int j = m - 1; j >= 0; j--) {
                    while (sz != 0 && firstNotFree[stack[sz - 1]] <= firstNotFree[j]) {
                        sz--;
                    }
                    int dpR = sz != 0 ? stack[sz - 1] : m;
                    if (
                        firstNotFree[j] < Ai &&
                        Ai <= i &&
                        dpL[j] < Aj &&
                        Aj < dpR &&
                        res < (i - firstNotFree[j])*(dpR - dpL[j] - 1)
                    ) {
                        res = (i - firstNotFree[j])*(dpR - dpL[j] - 1);
                        r = dpR;
                        l = dpL[j];
                        u = firstNotFree[j];
                        d = i + 1;
                    }
                    stack[sz++] = j;
                }
            }
            ++l;
            --r;
            ++u;
            --d;
            setKingdom(a, 0, m - 1, 0, u - 1);
            setKingdom(a, 0, l - 1, u, d);
            setKingdom(a, l, r, u, d);
            setKingdom(a, r + 1, m - 1, u, d);
            setKingdom(a, 0, m - 1, d + 1, n - 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    out.write(a[i][j]);
                }
                out.newLine();
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
