package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[1];
    private int head = 0;
    private int tail = 0;

    private int normalize(int x) {
        if (x >= elements.length) {
            x -= elements.length;
        }
        if (x < 0) {
            x += elements.length;
        }
        return x;
    }

    @Override
    protected void enqueueImpl(final Object element) {
        ensureCapacity();
        tail = normalize(tail + 1);
        elements[tail] = element;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            Object[] temp = new Object[2 * size];
            System.arraycopy(elements, head, temp, 0, size - head);
            if (head != 0) {
                System.arraycopy(elements, 0, temp, size - head, head);
            }
            head = 0;
            tail = size - 1;
            elements = temp;
        }
    }

    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    @Override
    protected void dequeueImpl() {
        elements[head] = null;
        head = normalize(head + 1);
    }

    /*
        Pred: true
        Post: n' == n && Immutable(n) && R == "[a[0], a[1], .., a[n - 1]]"
     */
    public String toStr() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                result.append(", ");
            }
            result.append(elements[normalize(head + i)].toString());
        }
        return result.append("]").toString();
    }

    @Override
    public int count(final Object element) {
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (elements[normalize(head + i)].equals(element)) {
                result++;
            }
        }
        return result;
    }
}
