package expression.generic.evaluator;

public class ShortEvaluator implements Evaluator<Short> {

    @Override
    public Short add(Short first, Short second) {
        return (short) (first + second);
    }

    @Override
    public Short subtract(Short first, Short second) {
        return (short) (first - second);
    }

    @Override
    public Short multiply(Short first, Short second) {
        return (short) (first * second);
    }

    @Override
    public Short divide(Short first, Short second) {
        return (short) (first / second);
    }

    @Override
    public Short negate(Short first) {
        return (short) -first;
    }

    @Override
    public Short valueOf(int i) {
        return (short) i;
    }
}
