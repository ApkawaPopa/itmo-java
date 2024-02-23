package expression.generic.expression;

import expression.generic.evaluator.Evaluator;

public class Subtract<T> extends BinaryOperation<T> {
    public Subtract(Evaluational<T> first, Evaluational<T> second, Evaluator<T> evaluator) {
        super(first, second, "-", evaluator);
    }

    @Override
    protected T applyOperation(T firstResult, T secondResult) {
        return evaluator.subtract(firstResult, secondResult);
    }
}
