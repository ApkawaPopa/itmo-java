package expression.generic.evaluator;

public class FloatEvaluator implements Evaluator<Float> {

    @Override
    public Float add(Float first, Float second) {
        return first + second;
    }

    @Override
    public Float subtract(Float first, Float second) {
        return first - second;
    }

    @Override
    public Float multiply(Float first, Float second) {
        return first * second;
    }

    @Override
    public Float divide(Float first, Float second) {
        return first / second;
    }

    @Override
    public Float negate(Float first) {
        return -first;
    }

    @Override
    public Float valueOf(int i) {
        return (float) i;
    }
}
