package expression.generic.evaluator;

public class DoubleEvaluator implements Evaluator<Double> {

    @Override
    public Double add(Double first, Double second) {
        return first + second;
    }

    @Override
    public Double subtract(Double first, Double second) {
        return first - second;
    }

    @Override
    public Double multiply(Double first, Double second) {
        return first * second;
    }

    @Override
    public Double divide(Double first, Double second) {
        return first / second;
    }

    @Override
    public Double negate(Double first) {
        return -first;
    }

    @Override
    public Double valueOf(int i) {
        return (double) i;
    }
}
