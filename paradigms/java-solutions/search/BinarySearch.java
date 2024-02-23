package search;
public class BinarySearch {
    // Pred1: 0 < i < a.length -> a[i - 1] >= a[i]
    // Post1:
    //      (0 <= i < R -> a[R] > x) && (
    //          R == a.length ||
    //          R != a.length &&
    //          a[R] <= x
    //      )
    private static int iterative(final int x, final int[] a) {
        // Pred1
        int l = -1, r = a.length;
        // Pred2: (0 <= i <= l -> a[i] > x) && (r <= i < a.length -> a[i] <= x) && -1 <= l < r <= a.length && Pred1
        while (l + 1 < r) {
            // Pred3: Pred2 && l + 1 < r
            int m;
            // Pred4: Pred3
            if ((l + r) % 2 == 1) {
                // Pred4 && (l + r) % 2 == 1 ->
                // Pred2 && 2l < l + r - 1 && l + r + 1 < 2r && (l + r) % 2 == 1 ->
                // Pred2 && l < (l + r - 1) / 2 &&  (l + r + 1) / 2 < r ->
                // Pred2 && l < (l + r - 1) / 2 &&  (l + r - 1) / 2 < r ->
                // Pred5: Pred2 && l < (l + r - 1) / 2 < r
                m = (l + r - 1) / 2;
                // Post5: Pred2 && l < m < r
            } else {
                // Pred4 && (l + r) % 2 == 0 ->
                // Pred2 && 2l < (l + r - 1) && (l + r + 1) < 2r && (l + r) % 2 == 0 ->
                // Pred2 && l < (l + r) / 2 &&  (l + r) / 2 < r ->
                // Pred6: Pred2 && l < (l + r) / 2 < r
                m = (l + r) / 2;
                // Post6: Pred2 && l < m < r
            }
            // Post4: Pred2 && l < m < r
            // Pred7: Post4
            if (a[m] > x) {
                // Pred7 && a[m] > x ->
                // Pred8: Pred2 && (0 <= i <= m -> a[i] > x) && m < r
                l = m;
                // Post8: Pred2
            } else {
                // Pred7 && a[m] <= x ->
                // Pred9: Pred2 && (m <= i <= a.length -> a[i] <= x) && l < m
                r = m;
                // Post9: Pred2
            }
            // Post7: Pred2
            // Post3: Post7
        }
        // Post2: Pred2 && (l + 1 == r) ->
        // Post1:
        //      (0 <= i < r -> a[r] > x) && (
        //          r == a.length ||
        //          r < a.length &&
        //          a[r] >= x
        //      )
        // -> R = r
        return r;
    }

    // Pred1:
    //      0 < i < a.length -> a[i - 1] >= a[i] &&
    //      (0 <= i <= l -> a[i] > x) &&
    //      (r <= i < a.length -> a[i] <= x) &&
    //      l < r
    // Post1:
    //      (0 <= i < R -> a[R] > x) && (
    //          R == a.length ||
    //          R != a.length &&
    //          a[R] <= x
    //      )
    private static int recursive(final int x, final int[] a, final int l, final int r) {
        // Pred1
        // Pred2: Pred1
        if (l + 1 < r) {
            int m;
            // Pred3: Pred2 && l + 1 < r
            if ((l + r) % 2 == 1) {
                // Pred3 && (l + r) % 2 == 1 ->
                // Pred2 && 2l < l + r - 1 && l + r + 1 < 2r && (l + r) % 2 == 1 ->
                // Pred2 && l < (l + r - 1) / 2 &&  (l + r + 1) / 2 < r ->
                // Pred2 && l < (l + r - 1) / 2 &&  (l + r - 1) / 2 < r ->
                // Pred4: Pred2 && l < (l + r - 1) / 2 < r
                m = (l + r - 1) / 2;
                // Post4: Pred2 && l < m < r
            } else {
                // Pred3 && (l + r) % 2 == 0 ->
                // Pred2 && 2l < (l + r - 1) && (l + r + 1) < 2r && (l + r) % 2 == 0 ->
                // Pred2 && l < (l + r) / 2 &&  (l + r) / 2 < r ->
                // Pred5: Pred2 && l < (l + r) / 2 < r
                m = (l + r) / 2;
                // Post5: Pred2 && l < m < r
            }
            // Post3: Pred2 && l < m < r
            // Pred7: Post3
            if (a[m] > x) {
                // Pred7 && a[m] > x ->
                // Pred8:
                //      0 < i < a.length -> a[i - 1] >= a[i] &&
                //      (0 <= i <= m -> a[i] > x) &&
                //      (r <= i < a.length -> a[i] <= x) &&
                //      m < r
                return recursive(x, a, m, r);
                // Post8: Post1
            } else {
                // Pred7 && a[m] <= x ->
                // Pred9:
                //      0 < i < a.length -> a[i - 1] >= a[i] &&
                //       (0 <= i <= l -> a[i] > x) &&
                //      (m <= i < a.length -> a[i] <= x) &&
                //      l < m
                return recursive(x, a, l, m);
                // Post9: Post1
            }
            // Post7: Post1
        } else {
            // Pred2 && l + 1 >= r -> (0 <= i < r -> a[i] > x) -> R = r
            return r;
        }
        // Post2: Post1
    }

    // Pred1:
    //      args.length > 0 &&
    //      (0 <= i < args.length -> args[i] - string(int)) &&
    //      (2 <= i < args.length -> int(args[i - 1]) > int(args[i]))
    // Post1: prints R: (1 <= i <= R -> int(args[i]) > int(args[0]))
    public static void main(String[] args) {
        // Pred1
        // Pred2: Pred1
        final int x = Integer.parseInt(args[0]);
        // Post2: Pred1 && x == int(args[0])
        // Pred3: Post2
        final int[] a = new int[args.length - 1];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        // Post3: Post2 && a == int(args[1:])
        // Post3 ->
        // Pred4: 0 < i < a.length -> a[i - 1] >= a[i]
        final int result = iterative(x, a);
        // Post4: 0 <= i < R -> a[R] > x
        // Pred5:
        // 0 < i < a.length -> a[i - 1] >= a[i] &&
        // (0 <= i <= -1 -> a[i] > x) &&
        // (a.length <= i < a.length -> a[i] <= x) &&
        // -1 < a.length
        assert result == recursive(x, a, -1, a.length);
        // Post5: iterative == recursive
        // Post1:
        System.out.println(result);
    }
}
