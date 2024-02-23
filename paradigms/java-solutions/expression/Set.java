package expression;

public class Set extends BinaryOperation {
    public Set(TripleExpression first, TripleExpression second) {
        super(first, second, "set");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult | (1 << secondResult);
    }
}
