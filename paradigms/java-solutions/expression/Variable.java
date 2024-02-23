package expression;

public class Variable implements TripleExpression {
    private final String variableName;

    public String getName() {
        return variableName;
    }

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (variableName) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new AssertionError("Invalid variable name for TripleExpression: " + variableName);
        }
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variable that = (Variable) o;
        return variableName.equals(that.getName());
    }

    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
}
