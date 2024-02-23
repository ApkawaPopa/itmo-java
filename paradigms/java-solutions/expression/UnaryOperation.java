package expression;
public abstract class UnaryOperation implements TripleExpression {
    private final TripleExpression value;
    private final String operationMark;
    protected UnaryOperation(TripleExpression value, String operationMark) {
        this.value = value;
        this.operationMark = operationMark;
    }

    public TripleExpression getValue() {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return applyOperation(value.evaluate(x, y, z));
    }

    protected abstract int applyOperation(int result);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnaryOperation that = (UnaryOperation) o;
        return value.equals(that.getValue());
    }

    @Override
    public String toString() {
        return operationMark + "(" + value.toString() + ")";
    }

    @Override
    public int hashCode() {
        return value.hashCode() * 31 + operationMark.hashCode();
    }
}
