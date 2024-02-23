package expression;

public class Add extends BinaryOperation {
    public Add(TripleExpression first, TripleExpression second) {
        super(first, second, "+");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult + secondResult;
    }
}
