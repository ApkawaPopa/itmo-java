package queue;

/*
    Model: a[0]..a[n-1]

    Invariant: n >= 0 && (0 <= i < n -> a[i] != null)

    Let Immutable(n): 0 <= i < n -> a'[i] = a[i]
 */
public class ArrayQueueModule {
    private static Object[] elements = new Object[1];
    private static int size = 0;
    private static int begin = 0;
    private static int end = 0;

    /*
        Pred: element != null
        Post: n' = n + 1 && Immutable(n) && a'[n' - 1] == element
     */
    public static void enqueue(final Object element) {
        assert element != null;
        ensureCapacity();
        if (++end == elements.length) {
            end = 0;
        }
        elements[end] = element;
        size++;
    }

    private static void ensureCapacity() {
        if (elements.length == size) {
            Object[] temp = new Object[2 * size];
            for (int i = 0; i < size; i++) {
                temp[i] = elements[begin++];
                if (begin == size) {
                    begin = 0;
                }
            }
            begin = 0;
            end = size - 1;
            elements = temp;
        }
    }

    /*
        Pred: n >= 1
        Post: n' == n && Immutable(n) && R == a[0]
     */
    public static Object element() {
        assert size >= 1;
        return elements[begin];
    }

    /*
        Pred: n >= 1
        Post: n' == n - 1 && (0 <= i < n' -> a'[i] == a[i + 1]) && R == a[0]
     */
    public static Object dequeue() {
        assert size >= 1;
        Object result = elements[begin];
        elements[begin] = null;
        if (++begin == elements.length) {
            begin = 0;
        }
        size--;
        return result;
    }

    /*
        Pred: true
        Post: n' == n && Immutable(n) && R == n
     */
    public static int size() {
        return size;
    }

    /*
        Pred: true
        Post: n' == n && Immutable(n) && R == (n == 0)
     */
    public static boolean isEmpty() {
        return size == 0;
    }

    /*
        Pred: true
        Post: n' = 0
     */
    public static void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    /*
        Pred: true
        Post: n' == n && Immutable(n) && R == "[a[0], a[1], .., a[n - 1]]"
     */
    public static String toStr() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                result.append(", ");
            }
            result.append(elements[(begin + i) % elements.length].toString());
        }
        return result.append("]").toString();
    }
}
