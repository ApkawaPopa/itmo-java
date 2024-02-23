package expression.exceptions;

import expression.*;

public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second, "/");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        if (firstResult == Integer.MIN_VALUE && secondResult == -1) {
            throw new ArithmeticException("overflow");
        }
        if (secondResult == 0) {
            throw new ArithmeticException("division by zero");
        }
        return firstResult / secondResult;
    }
}
