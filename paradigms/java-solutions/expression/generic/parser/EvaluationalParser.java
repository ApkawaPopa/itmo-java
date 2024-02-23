package expression.generic.parser;

import expression.generic.expression.Evaluational;

public interface EvaluationalParser<T> {
    Evaluational<T> parse(String expression);
}
