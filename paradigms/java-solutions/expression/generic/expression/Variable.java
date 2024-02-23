package expression.generic.expression;

import expression.generic.evaluator.Evaluator;

public class Variable<T> implements Evaluational<T> {
    private final String variableName;
    private final Evaluator<T> evaluator;

    public String getName() {
        return variableName;
    }

    public Variable(String variableName, Evaluator<T> evaluator) {
        this.variableName = variableName;
        this.evaluator = evaluator;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return switch (variableName) {
            case "x" -> evaluator.valueOf(x);
            case "y" -> evaluator.valueOf(y);
            case "z" -> evaluator.valueOf(z);
            default -> throw new AssertionError("Expected one of: x, y, z\nGot: " + variableName);
        };
    }

    @Override
    public String toString() {
        return variableName;
    }
}
