package expression;

public class Multiply extends BinaryOperation {
    public Multiply(Evaluational first, Evaluational second) {
        super(first, second, "*");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult * secondResult;
    }
}
