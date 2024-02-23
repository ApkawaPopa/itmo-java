package expression.generic.evaluator;

public class CheckedIntegerEvaluator implements Evaluator<Integer> {

    @Override
    public Integer add(Integer first, Integer second) {
        if (
                first < 0 && second < Integer.MIN_VALUE - first || 
                first > 0 && second > Integer.MAX_VALUE - first
        ) {
            throw new ArithmeticException("overflow");
        }
        return first + second;
    }

    @Override
    public Integer subtract(Integer first, Integer second) {
        if (
            first < 0 && second > -(Integer.MIN_VALUE - first) ||
            first >= 0 && second < first - Integer.MAX_VALUE
        ) {
            throw new ArithmeticException("overflow");
        }
        return first - second;
    }

    @Override
    public Integer multiply(Integer first, Integer second) {
        int firstSign = first < 0 ? -1 : 1;
        int secondSign = second < 0 ? -1 : 1;
        if (
            first < 0 && (
                second < 0 && second < Integer.MAX_VALUE / first ||
                second > 0 && first < Integer.MIN_VALUE / second
            ) ||
                first > 0 && (
                second < 0 && second < Integer.MIN_VALUE / first ||
                second > 0 && first > Integer.MAX_VALUE / second
            )
        ) {
            throw new ArithmeticException("overflow");
        }
        return first * second;
    }

    @Override
    public Integer divide(Integer first, Integer second) {
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new ArithmeticException("overflow");
        }
        if (second == 0) {
            throw new ArithmeticException("division by zero");
        }
        return first / second;
    }

    @Override
    public Integer negate(Integer first) {
        if (first == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -first;
    }

    @Override
    public Integer valueOf(int i) {
        return i;
    }
}
