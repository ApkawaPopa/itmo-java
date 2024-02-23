package queue;

public class ClassTest {
    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        for (int i = 0; i < 5;++i) {
            queue1.enqueue("e_1_" + i);
            queue2.enqueue("e_2_" + i);
        }
        dumpQueue(queue1);
        dumpQueue(queue2);
    }

    private static void dumpQueue(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.dequeue());
        }
    }
}
