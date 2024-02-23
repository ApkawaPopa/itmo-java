package expression.exceptions;

import expression.*;

public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second, "*");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        int firstSign = firstResult < 0 ? -1 : 1;
        int secondSign = secondResult < 0 ? -1 : 1;
        if (
            firstResult < 0 && (
                secondResult < 0 && secondResult < Integer.MAX_VALUE / firstResult ||
                secondResult > 0 && firstResult < Integer.MIN_VALUE / secondResult
            ) ||
            firstResult > 0 && (
                secondResult < 0 && secondResult < Integer.MIN_VALUE / firstResult ||
                secondResult > 0 && firstResult > Integer.MAX_VALUE / secondResult
            )
        ) {
            throw new ArithmeticException("overflow");
        }
        return firstResult * secondResult;
    }
}
