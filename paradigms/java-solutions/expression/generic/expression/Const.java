package expression.generic.expression;

public class Const<T> implements Evaluational<T> {
    private final T constValue;

    public T getValue() {
        return constValue;
    }

    public Const(T constValue) {
        this.constValue = constValue;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return constValue;
    }

    @Override
    public String toString() {
        return String.valueOf(constValue);
    }
}
