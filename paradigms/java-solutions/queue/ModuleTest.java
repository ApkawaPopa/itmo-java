package queue;

public class ModuleTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5;++i) {
            ArrayQueueModule.enqueue("e" + i);
        }
        while (!ArrayQueueModule.isEmpty()) {
            final Object value = ArrayQueueModule.dequeue();
            System.out.println(ArrayQueueModule.size() + " " + value);
        }
    }
}
