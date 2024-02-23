package expression.generic.expression;

import expression.generic.evaluator.Evaluator;

public abstract class UnaryOperation<T> implements Evaluational<T> {
    private final Evaluational<T> value;
    private final String operationMark;
    protected final Evaluator<T> evaluator;
    protected UnaryOperation(Evaluational<T> value, String operationMark, Evaluator<T> evaluator) {
        this.value = value;
        this.operationMark = operationMark;
        this.evaluator = evaluator;
    }

    public Evaluational<T> getValue() {
        return value;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return applyOperation(value.evaluate(x, y, z));
    }

    protected abstract T applyOperation(T result);

    @Override
    public String toString() {
        return operationMark + "(" + value.toString() + ")";
    }
}
