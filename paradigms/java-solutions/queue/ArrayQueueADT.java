package queue;

/*
    Model: a[0]..a[n-1]

    Invariant: n >= 0 && (0 <= i < n -> a[i] != null)

    Let Immutable(n): 0 <= i < n -> a'[i] = a[i]
 */
public class ArrayQueueADT {
    private Object[] elements = new Object[1];
    private int size = 0;
    private int begin = 0;
    private int end = 0;

    /*
        Pred: true
        Post: R == new queue && R.n == 0
     */
    public static ArrayQueueADT create() {
        final ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[1];
        queue.size = queue.begin = queue.end = 0;
        return queue;
    }

    /*
        Pred: queue != null && element != null
        Post: n' = n + 1 && Immutable(n) && a'[n' - 1] == element
     */
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        assert queue != null;
        assert element != null;
        ensureCapacity(queue);
        if (++queue.end == queue.elements.length) {
            queue.end = 0;
        }
        queue.elements[queue.end] = element;
        queue.size++;
    }

    private static void ensureCapacity(final ArrayQueueADT queue) {
        if (queue.elements.length == queue.size) {
            Object[] temp = new Object[2 * queue.size];
            for (int i = 0; i < queue.size; i++) {
                temp[i] = queue.elements[queue.begin++];
                if (queue.begin == queue.size) {
                    queue.begin = 0;
                }
            }
            queue.begin = 0;
            queue.end = queue.size - 1;
            queue.elements = temp;
        }
    }

    /*
        Pred: queue != null && n >= 1
        Post: n' == n && Immutable(n) && R == a[0]
     */
    public static Object element(final ArrayQueueADT queue) {
        assert queue != null;
        assert queue.size >= 1;
        return queue.elements[queue.begin];
    }

    /*
        Pred: queue != null && n >= 1
        Post: n' == n - 1 && (0 <= i < n' -> a'[i] == a[i + 1]) && R == a[0]
     */
    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue != null;
        assert queue.size >= 1;
        Object result = queue.elements[queue.begin];
        queue.elements[queue.begin] = null;
        if (++queue.begin == queue.elements.length) {
            queue.begin = 0;
        }
        queue.size--;
        return result;
    }

    /*
        Pred: queue != null
        Post: n' == n && Immutable(n) && R == n
     */
    public static int size(final ArrayQueueADT queue) {
        assert queue != null;
        return queue.size;
    }

    /*
        Pred: queue != null
        Post: n' == n && Immutable(n) && R == (n == 0)
     */
    public static boolean isEmpty(final ArrayQueueADT queue) {
        assert queue != null;
        return queue.size == 0;
    }

    /*
        Pred: queue != null
        Post: n' = 0
     */
    public static void clear(final ArrayQueueADT queue) {
        assert queue != null;
        while (!isEmpty(queue)) {
            dequeue(queue);
        }
    }

    /*
        Pred: queue != null
        Post: n' == n && Immutable(n) && R == "[a[0], a[1], .., a[n - 1]]"
     */
    public static String toStr(final ArrayQueueADT queue) {
        assert queue != null;
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < queue.size; i++) {
            if (i != 0) {
                result.append(", ");
            }
            result.append(queue.elements[(queue.begin + i) % queue.elements.length].toString());
        }
        return result.append("]").toString();
    }
}
