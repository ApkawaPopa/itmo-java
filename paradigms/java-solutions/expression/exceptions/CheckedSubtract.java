package expression.exceptions;

import expression.*;

public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second, "-");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        if (
            firstResult < 0 && secondResult > -(Integer.MIN_VALUE - firstResult) ||
            firstResult >= 0 && secondResult < firstResult - Integer.MAX_VALUE
        ) {
            throw new ArithmeticException("overflow");
        }
        return firstResult - secondResult;
    }
}
