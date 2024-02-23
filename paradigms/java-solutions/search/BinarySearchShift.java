package search;

public class BinarySearchShift {
    // Pred1:
    //      exists 0 <= k < a.length: 0 < i < a.length -> i == k || a[i - 1] > a[i]
    // Post1: R == k
    private static int iterFindShift(final int[] a) {
        // Pred1
        int l = 0, r = a.length;
        // Pred2: Pred1 && (0 < i <= l -> a[i] < a[0]) && (r <= i < a.length -> a[i] > a[0]) && 0 <= l < r <= a.length
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
            if (a[m] > a[0]) {
                // Pred7 && a[m] < a[0] ->
                // Pred8: Pred7 && (m <= i < a.length -> a[i] > a[0]) && 0 <= l < m <= a.length
                r = m;
                // Post8: Pred2
            } else {
                // Pred7 && a[m] <= a[0] ->
                // Pred7 && (a[m] < a[0] || m == 0) ->
                // Pred9: Pred7 && (0 < i <= m -> a[i] < a[0]) && 0 <= m < r <= a.length
                l = m;
                // Post9: Pred2
            }
            // Post7: Pred2
            // Post3: Post7
        }
        // Pred2 && l + 1 >= r ->
        // Pred10: Pred2 && l + 1 == r
        if (r == a.length) {
            return 0;
            // Pred10 && r == a.length ->
            // Post11: 0 < i < a.length -> a[i - 1] > a[i]
        } else {
            return r;
            // Pred10 && r != a.length ->
            // Post12: 0 < i < a.length -> i == r || a[i - 1] > a[i]
        }
        // Post10: Post1
    }

    // Pred1:
    //      exists 0 <= k < a.length: 0 < i < a.length -> i == k || a[i - 1] > a[i] &&
    //      (0 < i <= l -> a[i] < a[0]) &&
    //      (r <= i < a.length -> a[i] > a[0]) &&
    //      0 <= l < r <= a.length
    private static int recFindShift(final int[] a, int l, int r) {
        // Pred1:
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
            // Pred6: Post3
            if (a[m] > a[0]) {
                // Pred6 && a[m] > a[0] ->
                // Pred7:
                //      exists 0 <= k < a.length: 0 < i < a.length -> i == k || a[i - 1] > a[i] &&
                //      (0 < i <= l -> a[i] < a[0]) &&
                //      (m <= i < a.length -> a[i] > a[0]) &&
                //      0 <= l < m <= a.length
                return recFindShift(a, l, m);
                // Post7: Post1
            } else {
                // Pred6 && a[m] <= a[0] ->
                // Pred6 && a[m] < a[0] ->
                // Pred8:
                //      exists 0 <= k < a.length: 0 < i < a.length -> i == k || a[i - 1] > a[i] &&
                //      (0 < i <= m -> a[i] < a[0]) &&
                //      (r <= i < a.length -> a[i] > a[0]) &&
                //      0 <= m < r <= a.length
                return recFindShift(a, m, r);
                // Post8: Post1
            }
        } else {
            // Pred2 && l + 1 >= r ->
            // Pred9: Pred2 && l + 1 == r
            if (r == a.length) {
                return 0;
                // Pred9 && r == a.length ->
                // Post10: 0 < i < a.length -> a[i - 1] > a[i] ->
                // Post1
            } else {
                return r;
                // Pred9 && r != a.length ->
                // Post11: 0 < i < a.length -> i == r || a[i - 1] > a[i] ->
                // Post1
            }
        }
        // Post2: Post1
    }

    // Pred1:
    //      0 <= i < args.length -> args[i] - string(int) &&
    //      exists 0 <= k < args.length: 0 < i < args.length -> i == k || int(args[i - 1]) > int(args[i])
    // Post1: prints k
    public static void main(String[] args) {
        // Pred1
        int oddity = 0;
        final int[] a = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
            oddity ^= a[i] & 1;
        }
        final int result;
        if (oddity == 1) {
            // Pred1 && a == int(args) ->
            // Pred2: exists 0 <= k < a.length: 0 < i < a.length -> i == k || a[i - 1] > a[i]
            result = iterFindShift(a);
            // Post2: result == k
        } else {
            // Pred1 && a == int(args) ->
            // Pred3:
            //      exists 0 <= k < a.length: 0 < i < a.length -> i == k || a[i - 1] > a[i] &&
            //      (0 < i <= l -> a[i] < a[0]) &&
            //      (r <= i < a.length -> a[i] > a[0]) &&
            //      0 <= l < r <= a.length
            result = recFindShift(a, 0, a.length);
        }
        System.out.println(result);
        // Post1: result == k
    }
}
