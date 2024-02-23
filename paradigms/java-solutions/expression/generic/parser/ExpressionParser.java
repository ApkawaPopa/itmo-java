package expression.generic.parser;

import expression.generic.evaluator.Evaluator;
import expression.generic.expression.*;

public class ExpressionParser<T> extends BaseParser implements EvaluationalParser<T> {
    private final Evaluator<T> evaluator;
    public ExpressionParser(Evaluator <T> evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public Evaluational<T> parse(String expression) {
        setSource(new StringSource(expression));
        Evaluational<T> result = parseAddSub();
        if (!eof()) {
            throw new RuntimeException("The end of expression wasn't reached");
        }
        return result;
    }

    private Evaluational<T> parseAddSub() {
        Evaluational<T> result = parseMulDiv();
        while (true) {
            skipWhitespace();
            if (!oneOf("+-")) {
                return result;
            }
            result = build(String.valueOf(take()), result, parseMulDiv());
        }
    }

    private Evaluational<T> parseMulDiv() {
        Evaluational<T> result = parseFactor();
        while (true) {
            skipWhitespace();
            if (!oneOf("*/")) {
                return result;
            }
            result = build(String.valueOf(take()), result, parseFactor());
        }
    }

    private Evaluational<T> parseFactor() {
        skipWhitespace();
        if (take('(')) {
            Evaluational<T> result = parseAddSub();
            expect(')');
            return result;
        }
        if (take('-')) {
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new Negate<>(parseFactor(), evaluator);
        }
        if (between('0', '9')) {
            return parseConst(false);
        }
        if (oneOf("xyz")) {
            return new Variable<>(String.valueOf(take()), evaluator);
        }
        throw new RuntimeException("No operand");
    }

    private Const<T> parseConst(boolean minus) {
        StringBuilder sb = new StringBuilder();
        if (minus) {
            sb.append("-");
        }
        while (between('0', '9')) {
            sb.append(take());
        }
        Const<T> result;
        try {
            result = new Const<>(evaluator.valueOf(Integer.parseInt(sb.toString())));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Constant overflow");
        }
        return result;
    }

    private Evaluational<T> build(String operationMark, Evaluational<T> first, Evaluational<T> second) {
        return switch (operationMark) {
            case "+" -> new Add<>(first, second, evaluator);
            case "-" -> new Subtract<>(first, second, evaluator);
            case "*" -> new Multiply<>(first, second, evaluator);
            default -> new Divide<>(first, second, evaluator);
        };
    }
}
