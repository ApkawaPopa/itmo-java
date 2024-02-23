package expression.generic.evaluator;

public interface Evaluator<T> {
    T add(T first, T second);
    T subtract(T first, T second);
    T multiply(T first, T second);
    T divide(T first, T second);
    T negate(T first);
    T valueOf(int i);
}
