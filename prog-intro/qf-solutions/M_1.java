import java.io.*;
import java.util.*;

public class M {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- != 0) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int currentDay = 0; currentDay < n; ++currentDay) {
                a[currentDay] = in.nextInt();
            }
            Map<Integer, Integer> map = new TreeMap<>();
            long answer = 0;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    answer += map.getOrDefault(2*a[i] - a[j], 0);
                }
                map.put(a[i], map.getOrDefault(a[i], 0) + 1);
            }
            System.out.println(answer);
        }
    }
}
