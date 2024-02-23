public class SumFloat {
    public static void main(String args[]) {
        float sum = 0;
        for (int i = 0; i < args.length; ++i) {
            int l = 0, r = 0;
            while (r < args[i].length()) {
                if (Character.isWhitespace(args[i].charAt(r))) {
                    if (l != r) {
                        sum += Float.parseFloat(args[i].substring(l, r));
                    }
                    l = r + 1;
                }
                r++;
            }
            if (l != r) {
                sum += Float.parseFloat(args[i].substring(l));
            }
        }
        System.out.print(sum);
    }
}
