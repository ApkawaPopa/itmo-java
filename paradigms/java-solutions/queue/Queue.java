package queue;

/*
    Model: a[0]..a[n-1]

    Invariant: n >= 0 && (0 <= i < n -> a[i] != null)

    Let Immutable(n): 0 <= i < n -> a'[i] = a[i]
 */
public interface Queue {
    /*
        Pred: element != null
        Post: n' = n + 1 && Immutable(n) && a[n' - 1] == element
     */
    void enqueue(final Object element);

    /*
        Pred: n >= 1
        Post: n' == n && Immutable(n) && R == a[0]
     */
    Object element();

    /*
        Pred: n >= 1
        Post: n' == n - 1 && (0 <= i < n' -> a'[i] == a[i + 1]) && R == a[0]
     */
    Object dequeue();

    /*
        Pred: true
        Post: n' == n && Immutable(n) && R == n
     */
    int size();

    /*
        Pred: true
        Post: n' == n && Immutable(n) && R == (n == 0)
     */
    boolean isEmpty();

    /*
        Pred: true
        Post: n' = 0
     */
    void clear();

    /*
        Pred: true
        Post: n' == n && Immuatble(n) && R == +(i=0..n-1, a[i] == element)
     */
    int count(final Object element);
}
