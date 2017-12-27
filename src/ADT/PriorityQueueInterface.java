package ADT;

public interface PriorityQueueInterface<T> {

    void enqueue(T element);

    T dequeue() throws QueueUnderflowException;

    T getFront();

    boolean isEmpty();

    int size();

    void clear();

    void remove(T entry);

    boolean contains(T entry);

    T getEntry(int position);

}
