package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int head;
    private int tail;
    private int length;
    private int size;
    private T[] value;

    public ArrayDeque() {
        this.head = 0;
        this.tail = 0;
        this.length = 0;
        this.size = 8;
        this.value = (T[]) new Object[this.size];
    }

    private void reSize(int num) {
        T[] newArray = (T[]) new Object[num];
        for (int i = 0; i < this.length; i += 1) {
            newArray[i] = this.value[(head + i) % this.size];
        }
        this.head = 0;
        this.tail = Math.max(this.length - 1, 0);
        this.size = num;
        this.value = newArray;
    }

    @Override
    public void addFirst(T item) {
        if (this.length + 1 >= this.size) {
            reSize(this.size * 2);
        }
        if (!this.isEmpty()) {
            this.head = (this.head - 1 + this.size) % this.size;
        }
        value[this.head] = item;
        this.length += 1;
    }

    @Override
    public void addLast(T item) {
        if (this.length + 1 >= this.size) {
            reSize(this.size * 2);
        }
        if (!this.isEmpty()) {
            this.tail = (this.tail + 1) % this.size;
        }
        value[this.tail] = item;
        this.length += 1;
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public void printDeque() {
        for (int i = head; i != tail; i = (i + 1) % size) {
            System.out.print(value[i].toString() + ' ');
        }
        System.out.println(value[tail].toString());
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        this.length -= 1;
        T temp = this.value[this.head];
        this.head = (this.head + 1) % this.size;
        if (4 * this.length <= this.size) {
            reSize(this.size / 2);
        }
        return temp;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        this.length -= 1;
        T temp = this.value[this.tail];
        this.tail = (this.tail - 1 + this.size) % this.size;
        if (4 * this.length <= this.size) {
            reSize(this.size / 2);
        }
        return temp;
    }

    @Override
    public T get(int index) {
        if (index >= this.length || index < 0) {
            return null;
        }
        int idx = (this.head + index) % this.size;
        return this.value[idx];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque) {
            if (((Deque<?>) o).size() != this.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i += 1) {
                if (this.get(i) != ((Deque<?>) o).get(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private class LinkedListIterator implements Iterator<T> {
        private int cnt;
        LinkedListIterator() {
            this.cnt = 0;
        }
        public boolean hasNext() {
            return this.cnt < length;
        }
        public T next() {
            int temp = this.cnt;
            this.cnt += 1;
            return get(temp);
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
}