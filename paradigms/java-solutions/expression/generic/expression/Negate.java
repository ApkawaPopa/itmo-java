package expression.generic.expression;

import expression.generic.evaluator.Evaluator;

public class Negate<T> extends UnaryOperation<T> {
    public Negate(Evaluational<T> value, Evaluator<T> evaluator) {
        super(value, "-", evaluator);
    }

    @Override
    protected T applyOperation(T result) {
        return evaluator.negate(result);
    }
}
