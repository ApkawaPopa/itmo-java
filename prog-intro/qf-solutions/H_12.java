import java.io.*;
import java.util.*;

public class H {
    public static int nextInt(Reader in) throws IOException {
        int c;
        while (Character.isWhitespace(c = in.read()));
        int res = 0, sign;
        if (c == '-') {
            sign = -1;
        } else {
            sign = 1;
            res = c;
        }
        while ((c = in.read()) != -1 && !Character.isWhitespace(c)) {
            res = res*10 + c;
        }
        return res*sign;
    }

    public static void main(String[] args) {
        try (Reader in = new BufferedReader(new InputStreamReader(System.in))) {
            int n = nextInt(in);
            int[] prefixSums = new int[n + 1];
            int max = 0;
            for (int i = 1; i <= n; i++) {
                prefixSums[i] = nextInt(in);
                if (max < prefixSums[i]) {
                    max = prefixSums[i];
                }
                prefixSums[i] += prefixSums[i - 1];
            }
            int[] counted = new int[prefixSums[n] + 1];
            Arrays.fill(counted, -1);
            int q = nextInt(in);
            for (int i = 0; i < q; i++) {
                int t = nextInt(in);
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



