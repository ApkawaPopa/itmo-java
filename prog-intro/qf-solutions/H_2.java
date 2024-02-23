import java.io.*;
import java.util.*;

public class H {
    private static final int infinity = Integer.MAX_VALUE;

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

interface CorrectSymbol{
    boolean oneOfCorrect(char sym);
}

class MyScanner implements AutoCloseable {
    private Reader in = null;
    private FileInputStream inputStream = null;
    private int bufferSize = 1024;
    private char[] buffer = new char[bufferSize];
    private int pos = 0;
    private int size = 0;
    private int lineCount = 0;
    private int wordInLine = 0;

    public MyScanner(InputStream stream) throws IOException {
        in = new InputStreamReader(stream, "utf-8");
        size = in.read(buffer);
    }

    public MyScanner(File file) throws IOException {
        inputStream = new FileInputStream(file);
        in = new InputStreamReader(inputStream, "utf-8");
        size = in.read(buffer);
    }

    public MyScanner(String str) throws IOException {
        in = new StringReader(str);
        size = in.read(buffer);
    }

    public int currentLine() {
        return lineCount;
    }

    public int numberInLine() {
        return wordInLine;
    }

    private CorrectSymbol checkedPattern = null;
    private int checkedPos;
    private int checkedLineCount;

    private boolean hasNextSmth(CorrectSymbol pattern) throws IOException {
        checkedPattern = pattern;
        checkedPos = pos;
        checkedLineCount = lineCount;
        while (true) {
            if (checkedPos < size) {
                if (buffer[checkedPos] == '\n') {
                    checkedLineCount++;
                }
                if (pattern.oneOfCorrect(buffer[checkedPos])) {
                    return true;
                }
                checkedPos++;
            } else {
                if (size == -1) {
                    return false;
                }
                buffer = Arrays.copyOf(buffer, buffer.length + bufferSize);
                size = in.read(buffer, buffer.length - bufferSize, bufferSize);
                if (size != -1)
                    size += buffer.length - bufferSize;
            }
        }
    }

    private String nextSmth(CorrectSymbol pattern) throws IOException {
        StringBuilder token = new StringBuilder();
        if (pattern == checkedPattern) {
            lineCount = checkedLineCount;
            pos = checkedPos;
        }
        checkedPattern = null;
        while (true) {
            if (pos < size) {
                if (buffer[pos] == '\n') {
                    ++lineCount;
                    wordInLine = 0;
                }
                if (pattern.oneOfCorrect(buffer[pos])) {
                    token.append(buffer[pos++]);
                } else {
                    if (token.length() != 0) {
                        break;
                    }
                    pos++;
                }
            } else {
                if (size == -1) {
                    break;
                }
                if (buffer.length != bufferSize) {
                    buffer = Arrays.copyOf(buffer, bufferSize);
                }
                size = in.read(buffer);
                pos = 0;
            }
        }
        if (pattern == wordPattern) {
            wordInLine++;
        }
        return token.toString();
    }


    private static boolean nonWhitespacePatternChecker(char sym) {
        return !Character.isWhitespace(sym);
    }
    CorrectSymbol nonWhitespacePattern = MyScanner::nonWhitespacePatternChecker;

    public boolean hasNext() throws IOException {
        return hasNextSmth(nonWhitespacePattern);
    }

    public String next() throws IOException {
        return nextSmth(nonWhitespacePattern);
    }


    private static boolean linePatternChecker(char sym) {
        return sym != '\n';
    }
    CorrectSymbol linePattern = MyScanner::linePatternChecker;

    public boolean hasNextLine() throws IOException {
        return hasNextSmth(linePattern);
    }

    public String nextLine() throws IOException {
        return nextSmth(linePattern);
    }


    public static boolean wordPatternChecker(char sym) {
        sym = Character.toLowerCase(sym);
        return sym == '\''
            || Character.isLetter(sym)
            || Character.getType(sym) == Character.DASH_PUNCTUATION;
    }
    CorrectSymbol wordPattern = MyScanner::wordPatternChecker;

    public boolean hasNextWord() throws IOException {
        return hasNextSmth(wordPattern);
    }

    public String nextWord() throws IOException {
        return nextSmth(wordPattern).toLowerCase();
    }


    public String nextHexDecAbc() throws IOException {
        String strVal = next().toLowerCase();
        if (strVal.length() == 0)
            return "";
        if (Character.isLetter(strVal.charAt(0)) || strVal.charAt(0) == '-' && Character.isLetter(strVal.charAt(1))) {
            return strVal;
        } else {
            if (strVal.length() > 2 && strVal.substring(0, 2).equals("0x")) {
                strVal = Integer.parseUnsignedInt(strVal.substring(2), 16) + "";
            }
            char[] replaced = new char[strVal.length()];
            int i = 0;
            if (strVal.charAt(0) == '-') {
                replaced[i++] = '-';
            }
            for (; i < strVal.length(); ++i) {
                replaced[i] = (char) (strVal.charAt(i) - '0' + 'a');
            }
            return String.valueOf(replaced);
        }
    }

    private static boolean intPatternChecker(char sym) {
        return Character.isDigit(sym) || sym == '-' || sym == '+';
    }
    CorrectSymbol intPattern = MyScanner::intPatternChecker;

    public boolean hasNextInt() throws IOException {
        return hasNextSmth(intPattern);
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextSmth(intPattern));
    }

    public void close() throws IOException {
        if (in != null) {
            in.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
