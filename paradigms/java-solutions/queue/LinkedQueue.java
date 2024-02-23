package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    @Override
    protected void enqueueImpl(final Object element) {
        if (head == null) {
            head = tail = new Node(element);
        } else {
            tail.next = new Node(element);
            tail = tail.next;
        }
    }

    @Override
    protected Object elementImpl() {
        return head.element;
    }

    @Override
    protected void dequeueImpl() {
        head = head.next;
    }

    @Override
    public int count(final Object element) {
        int result = 0;
        for (Node i = head; i != null; i = i.next) {
            if (i.element.equals(element)) {
                result++;
            }
        }
        return result;
    }

    private static class Node {
        private final Object element;
        private Node next;

        public Node(final Object element) {
            this.element = element;
        }
    }
}
