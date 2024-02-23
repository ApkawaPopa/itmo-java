package expression;

public class Add extends BinaryOperation {
    public Add(Evaluational first, Evaluational second) {
        super(first, second, "+");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult + secondResult;
    }
}
