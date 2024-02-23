import java.util.*;

public class I {
    private static final int infinity = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int xl = infinity;
        int xr = -infinity;
        int yl = infinity;
        int yr = -infinity;
        for (int i = 0; i < n; ++i) {
            int x = in.nextInt(), y = in.nextInt(), h = in.nextInt();
            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }
        System.out.print((xl + xr)/2 + " " + (yl + yr)/2);
        System.out.print(" " + (Math.max(xr - xl, yr - yl) + 1)/2);
    }
}
