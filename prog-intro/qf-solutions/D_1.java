import java.io.*;
import java.util.*;

public class D {
    static final long mod = 998_244_353;

    static long binaryPower(int x, int power) {
        if (power == 0) {
            return 1;
        }
        if ((power & 1) == 1) {
            return (x * binaryPower(x, power - 1)) % mod;
        }
        long t = binaryPower(x, power >> 1);
        return (t * t) % mod;
    }

    static void calculateR(long R[], int n, int k) {
        for (int i = 1; i <= n; i++) {
            if ((i & 1) == 0) {
                R[i] = (binaryPower(k, (i >> 1) + 1) + binaryPower(k, i >> 1)) * (i >> 1);
            } else {
                R[i] = i * binaryPower(k, (i + 1) >> 1);
            }
            R[i] %= mod;
        }
    }

    static void calculateD(long D[], long R[], int n) {
        for (int i = 1; i <= n; ++i) {
            for (int l = 1; l*l <= i; ++l) {
                if (i%l == 0) {
                    if (l*l == i) {
                        D[i] += (i / l * D[l]) % mod;
                    } else {
                        D[i] += (i / l * D[l] + l * D[i / l]) % mod;
                    }
                }
            }
            D[i] = ((R[i] - D[i]) % mod + mod) % mod;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        long R[] = new long[n + 1];
        calculateR(R, n, k);
        long D[] = new long[n + 1];
        calculateD(D, R, n);
        long sum = 0;
        for (int i = 1; i <= n; ++i)
            for (int l = 1; l*l <= i; ++l)
                if (i%l == 0) {
                    if (l*l == i) {
                        sum = (sum + D[l]) % mod;
                    } else {
                        sum = (sum + D[l] + D[i / l]) % mod;
                    }
                }
        System.out.println(sum);
    }
}
