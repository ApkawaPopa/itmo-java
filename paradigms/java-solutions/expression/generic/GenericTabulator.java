package expression.generic;

import expression.generic.evaluator.*;
import expression.generic.expression.Evaluational;
import expression.generic.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return staticTabulate(mode, expression, x1, x2, y1, y2, z1, z2);
    }

    private static Object[][][] staticTabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        if (x1 > x2 || y1 > y2 || z1 > z2) {
            throw new Exception(
                    "Wrong range of calculation: " + x1 + " " + x2 + " " + y1 + " " + y2 + " " + z1 + " " + z2
            );
        }
        ExpressionParser<?> parser = switch (mode) {
            case "i" -> new ExpressionParser<>(new CheckedIntegerEvaluator());
            case "d" -> new ExpressionParser<>(new DoubleEvaluator());
            case "bi" -> new ExpressionParser<>(new BigIntegerEvaluator());
            case "u" -> new ExpressionParser<>(new IntegerEvaluator());
            case "s" -> new ExpressionParser<>(new ShortEvaluator());
            case "f" -> new ExpressionParser<>(new FloatEvaluator());
            default -> throw new Exception("Wrong mode. Expected one of: i, d, bi, u, f, s. Got: " + mode + ".");
        };
        Evaluational<?> parsed = parser.parse(expression);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = parsed.evaluate(i, j, k);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong amount of arguments. Expected 2, got 3.");
            return;
        }
        if (args[0].length() == 0) {
            System.out.println("First argument length must be greater 0!");
            return;
        }
        Object[][][] result;
        try {
            result = staticTabulate(args[0].substring(1), args[1], -2, 2, -2, 2, -2, 2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.println(result[i][j][k]);
                }
            }
        }
    }
}
