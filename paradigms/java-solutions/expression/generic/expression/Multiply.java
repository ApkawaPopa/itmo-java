package expression.generic.expression;

import expression.generic.evaluator.Evaluator;

public class Multiply<T> extends BinaryOperation<T> {
    public Multiply(Evaluational<T> first, Evaluational<T> second, Evaluator<T> evaluator) {
        super(first, second, "*", evaluator);
    }

    @Override
    protected T applyOperation(T firstResult, T secondResult) {
        return evaluator.multiply(firstResult, secondResult);
    }
}
