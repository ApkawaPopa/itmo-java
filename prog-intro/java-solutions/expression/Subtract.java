package expression;

public class Subtract extends BinaryOperation {
    public Subtract(Evaluational first, Evaluational second) {
        super(first, second, "-");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult - secondResult;
    }
}
