package expression;

public class Divide extends BinaryOperation {
    public Divide(TripleExpression first, TripleExpression second) {
        super(first, second, "/");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult / secondResult;
    }
}
