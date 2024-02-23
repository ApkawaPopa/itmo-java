package expression;
public abstract class UnaryOperation implements Evaluational {
    private final Evaluational value;
    private final String operationMark;
    protected UnaryOperation(Evaluational value, String operationMark) {
        this.value = value;
        this.operationMark = operationMark;
    }

    public Evaluational getValue() {
        return value;
    }

    @Override
    public int evaluate(int x) {
        return applyOperation(value.evaluate(x));
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
