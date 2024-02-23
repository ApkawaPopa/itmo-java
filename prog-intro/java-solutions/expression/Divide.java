package expression;

public class Divide extends BinaryOperation {
    public Divide(Evaluational first, Evaluational second) {
        super(first, second, "/");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult / secondResult;
    }
}
