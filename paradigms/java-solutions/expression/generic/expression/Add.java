package expression.generic.expression;

import expression.generic.evaluator.Evaluator;

public class Add<T> extends BinaryOperation<T> {
    public Add(Evaluational<T> first, Evaluational<T> second, Evaluator<T> evaluator) {
        super(first, second, "+", evaluator);
    }

    @Override
    protected T applyOperation(T firstResult, T secondResult) {
        return evaluator.add(firstResult, secondResult);
    }
}
