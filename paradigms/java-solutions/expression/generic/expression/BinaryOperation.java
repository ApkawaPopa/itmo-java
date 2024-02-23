package expression.generic.expression;


import expression.generic.evaluator.Evaluator;

public abstract class BinaryOperation<T> implements Evaluational<T> {
    private final Evaluational<T> first;
    private final Evaluational<T> second;
    private final String operationMark;
    protected final Evaluator<T> evaluator;

    public Evaluational<T> getFirst() {
        return first;
    }

    public Evaluational<T> getSecond() {
        return second;
    }

    protected BinaryOperation(
        Evaluational<T> first,
        Evaluational<T> second,
        String operationMark,
        Evaluator<T> evaluator
    ) {
        this.first = first;
        this.second = second;
        this.operationMark = operationMark;
        this.evaluator = evaluator;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return applyOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
    
    protected abstract T applyOperation(T firstResult, T secondResult);

    @Override
    public String toString() {
        return "(" + first.toString() + " " + operationMark + " " + second.toString() + ")";
    }
}
