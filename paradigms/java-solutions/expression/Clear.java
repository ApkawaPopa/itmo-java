package expression;

import java.net.Inet4Address;

public class Clear extends BinaryOperation {
    public Clear(TripleExpression first, TripleExpression second) {
        super(first, second, "clear");
    }

    @Override
    protected int applyOperation(int firstResult, int secondResult) {
        return firstResult & (0xffffffff - (1 << secondResult));
    }
}
