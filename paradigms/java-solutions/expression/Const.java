package expression;

public class Const implements TripleExpression {
    private final int constValue;

    public int getValue() {
        return constValue;
    }

    public Const(int constValue) {
        this.constValue = constValue;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constValue;
    }

    @Override
    public String toString() {
        return String.valueOf(constValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Const that = (Const) o;
        return constValue == that.getValue();
    }

    @Override
    public int hashCode() {
        return constValue;
    }
}
