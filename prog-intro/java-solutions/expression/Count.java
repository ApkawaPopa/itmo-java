package expression;
public class Count extends UnaryOperation {
    public Count(Evaluational value) {
        super(value, "count");
    }

    @Override
    protected int applyOperation(int result) {
        int x = 0;
        while (result != 0) {
            x += result & 1;
            result >>>= 1;
        }
        return x;
    }
}
