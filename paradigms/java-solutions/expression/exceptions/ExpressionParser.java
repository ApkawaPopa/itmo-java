package expression.exceptions;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {
    public ExpressionParser() {}

    @Override
    public TripleExpression parse(String expression) {
        setSource(new StringSource(expression));
        TripleExpression result = parseSetClear();
        if (!eof()) {
            throw new ParserException("The end of expression wasn't reached");
        }
        return result;
    }

    private TripleExpression parseSetClear() {
        TripleExpression result = parseAddSub();
        while (true) {
            skipWhitespace();
            if (take('s')) {
                expect("et");
                if (!oneOf(" (\0+-*/") && !Character.isWhitespace(take())) {
                    throw new ParserException("Unknown operation 'set" + take() + "'");
                }
                result = build("set", result, parseAddSub());
            } else if (take('c')) {
                expect("lear");
                if (!oneOf(" (\0+-*/") && !Character.isWhitespace(take())) {
                    throw new ParserException("Unknown operation 'clear" + take() + "'");
                }
                result = build("clear", result, parseAddSub());
            } else {
                return result;
            }
        }
    }

    private TripleExpression parseAddSub() {
        TripleExpression result = parseMulDiv();
        while (true) {
            skipWhitespace();
            if (!oneOf("+-")) {
                return result;
            }
            result = build(String.valueOf(take()), result, parseMulDiv());
        }
    }

    private TripleExpression parseMulDiv() {
        TripleExpression result = parseFactor();
        while (true) {
            skipWhitespace();
            if (!oneOf("*/")) {
                return result;
            }
            result = build(String.valueOf(take()), result, parseFactor());
        }
    }

    private TripleExpression parseFactor() {
        skipWhitespace();
        if (take('(')) {
            TripleExpression result = parseSetClear();
            expect(')');
            return result;
        }
        if (take('-')) {
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new CheckedNegate(parseFactor());
        }
        if (between('0', '9')) {
            return parseConst(false);
        }
        if (take('c')) {
            expect("ount");
            if (!oneOf(" (\0")) {
                throw new ParserException("Unknown operation 'count" + take() + "'");
            }
            return new Count(parseFactor());
        }
        if (oneOf("xyz")) {
            return new Variable(String.valueOf(take()));
        }
        throw new ParserException("No operand");
    }

    private Const parseConst(boolean minus) {
        StringBuilder sb = new StringBuilder();
        if (minus) {
            sb.append("-");
        }
        while (between('0', '9')) {
            sb.append(take());
        }
        Const result;
        try {
            result = new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw new ParserException("Constant overflow");
        }
        return result;
    }

    private TripleExpression build(String operationMark, TripleExpression first, TripleExpression second) {
        return switch (operationMark) {
            case "+" -> new CheckedAdd(first, second);
            case "-" -> new CheckedSubtract(first, second);
            case "*" -> new CheckedMultiply(first, second);
            case "/" -> new CheckedDivide(first, second);
            case "set" -> new Set(first, second);
            default -> new Clear(first, second);
        };
    }
}
