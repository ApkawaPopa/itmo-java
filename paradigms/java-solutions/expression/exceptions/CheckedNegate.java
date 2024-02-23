package expression.exceptions;

import expression.*;

public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(TripleExpression value) {
        super(value, "-");
    }

    @Override
    protected int applyOperation(int result) {
        if (result == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -result;
    }
}
