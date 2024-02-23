package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {
    public ExpressionParser() {}

    @Override
    public Evaluational parse(String expression) {
        setSource(new StringSource(expression));
        return parseAddSub();
    }

    private Evaluational parseAddSub() {
        Evaluational result = parseMulDiv();
        while (true) {
            skipWhitespace();
            if (!test('+') && !test('-')) {
                return result;
            }
            result = build(take(), result, parseMulDiv());
        }
    }

    private Evaluational parseMulDiv() {
        Evaluational result = parseFactor();
        while (true) {
            skipWhitespace();
            if (!test('*') && !test('/')) {
                return result;
            }
            result = build(take(), result, parseFactor());
        }
    }

    private Evaluational parseFactor() {
        skipWhitespace();
        if (take('(')) {
            Evaluational result = parseAddSub();
            expect(')');
            return result;
        }
        if (take('-')) {
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new Negate(parseFactor());
        }
        if (between('0', '9')) {
            return parseConst(false);
        }
        if (take('c')) {
            expect("ount");
            return new Count(parseFactor());
        }
        return new Variable(String.valueOf(take()));
    }

    private Const parseConst(boolean minus) {
        StringBuilder sb = new StringBuilder();
        if (minus) {
            sb.append("-");
        }
        while (between('0', '9')) {
            sb.append(take());
        }
        return new Const(Integer.parseInt(sb.toString()));
    }

    private Evaluational build(char operationMark, Evaluational first, Evaluational second) {
        return switch (operationMark) {
            case '+' -> new Add(first, second);
            case '-' -> new Subtract(first, second);
            case '*' -> new Multiply(first, second);
            default -> new Divide(first, second);
        };
    }
}
