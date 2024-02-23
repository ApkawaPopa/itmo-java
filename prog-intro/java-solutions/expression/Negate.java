package expression;
public class Negate extends UnaryOperation {
    public Negate(Evaluational value) {
        super(value, "-");
    }

    @Override
    protected int applyOperation(int result) {
        return -result;
    }
}
