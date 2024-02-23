package expression;

public class Multiply extends BinaryOperation {
    public Multiply(TripleExpression first, TripleExpression second) {
        super(first, second, "*");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult * secondResult;
    }
}
