package queue;

public class ADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        for (int i = 0; i < 5;++i) {
            ArrayQueueADT.enqueue(queue1, "e_1_" + i);
            ArrayQueueADT.enqueue(queue1, "e_2_" + i);
        }
        dumpQueue(queue1);
        dumpQueue(queue2);
    }

    private static void dumpQueue(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " " + ArrayQueueADT.dequeue(queue));
        }
    }
}
