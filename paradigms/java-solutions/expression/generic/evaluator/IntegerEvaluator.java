package expression.generic.evaluator;

public class IntegerEvaluator implements Evaluator<Integer> {

    @Override
    public Integer add(Integer first, Integer second) {
        return first + second;
    }

    @Override
    public Integer subtract(Integer first, Integer second) {
        return first - second;
    }

    @Override
    public Integer multiply(Integer first, Integer second) {
        return first * second;
    }

    @Override
    public Integer divide(Integer first, Integer second) {
        return first / second;
    }

    @Override
    public Integer negate(Integer first) {
        return -first;
    }

    @Override
    public Integer valueOf(int i) {
        return i;
    }
}
