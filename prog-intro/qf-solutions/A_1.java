import java.io.*;
import java.util.*;

public class A {
    public static void main(String[] args) {
        int a, b, n;
        Scanner in = new Scanner(System.in);
        a = in.nextInt();
        b = in.nextInt();
        n = in.nextInt();
        System.out.print(2*((n - b) / (b - a)) + ((n - b) % (b - a) == 0 ? 1 : 3));
    }
}
