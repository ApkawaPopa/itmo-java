package expression;

public abstract class BinaryOperation implements Evaluational {
    private final Evaluational first;
    private final Evaluational second;
    private final String operationMark;

    public Evaluational getFirst() {
        return first;
    }

    public Evaluational getSecond() {
        return second;
    }

    protected BinaryOperation(Evaluational first, Evaluational second, String operationMark) {
        this.first = first;
        this.second = second;
        this.operationMark = operationMark;
    }

    @Override
    public int evaluate(int value) {
        return applyOperation(first.evaluate(value), second.evaluate(value));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return applyOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    protected abstract int applyOperation(int firstResult, int secondResult);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("(").append(first.toString()).append(" ").append(operationMark)
            .append(" ").append(second.toString()).append(")").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinaryOperation that = (BinaryOperation) o;
        return first.equals(that.getFirst()) && second.equals(that.getSecond());
    }

    @Override
    public int hashCode() {
        return (first.hashCode() * 31 + second.hashCode()) * 31 + operationMark.hashCode();
    }
}
