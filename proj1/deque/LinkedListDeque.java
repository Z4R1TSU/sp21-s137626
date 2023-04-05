package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    public class linkedList {
        public T value;
        public linkedList next;
        public linkedList prev;
        public  linkedList(linkedList p, T i, linkedList n) {
            this.prev = p;
            this.value = i;
            this.next = n;
        }
    }

    private final linkedList sentinelHead;
    private final linkedList sentinelTail;
    private int size;

    public LinkedListDeque() {
        sentinelTail = new linkedList(null, null, null);
        sentinelHead = new linkedList(null, null, sentinelTail);
        sentinelTail.prev = sentinelHead;
    }

    @Override
    public void addFirst(T item) {
        size += 1;
        sentinelHead.next = new linkedList(sentinelHead, item, sentinelHead.next);
        sentinelHead.next.next.prev = sentinelHead.next;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        sentinelTail.prev = new linkedList(sentinelTail.prev, item, sentinelTail);
        sentinelTail.prev.prev.next = sentinelTail.prev;
    }

    @Override
    public boolean isEmpty() {
        return sentinelHead.next == sentinelTail;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        linkedList p = sentinelHead;
        while (p.next != sentinelTail) {
            if (p != sentinelHead) {
                System.out.print(p.value.toString() + ' ');
            }
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (sentinelHead.next == sentinelTail) {
            return null;
        }
        else {
            size -= 1;
            T temp = sentinelHead.next.value;
            sentinelHead.next = sentinelHead.next.next;
            sentinelHead.next.prev = sentinelHead;
            return temp;
        }
    }

    @Override
    public T removeLast() {
        if (sentinelTail.prev == sentinelHead) {
            return null;
        }
        else {
            size -= 1;
            T temp = sentinelTail.prev.value;
            sentinelTail.prev = sentinelTail.prev.prev;
            sentinelTail.prev.next = sentinelTail;
            return temp;
        }
    }

    @Override
    public T get(int index) {
        linkedList p = sentinelHead;
        while (p != sentinelTail) {
            p = p.next;
            if (index == 0) {
                return p.value;
            }
            index -= 1;
        }
        return null;
    }

    public T getRecursive(int index) {
        return helperGetRecur(sentinelHead, index);
    }
    public T helperGetRecur(linkedList p, int idx) {
        if (this.isEmpty()) {
            return null;
        }
        else if (p.next == sentinelTail) {
            return null;
        }
        else if (idx == 0) {
            return p.value;
        }
        else {
            return helperGetRecur(p.next, idx - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        // if the type of o is not equivalent to T or their size not fit, returns False.
        if (!(o instanceof Deque) || ((Deque<?>) o).size() != this.size()) {
            return false;
        }
        for (int i = 0; i <= this.size(); i += 1) {
            if (((Deque<?>) o).get(i) != this.get(i)) {
                return false;
            }
        }
        return true;
    }

    private class LinkedListIterator implements Iterator<T> {
        private linkedList node;
        LinkedListIterator() {
            this.node = sentinelHead.next;
        }
        public boolean hasNext() {
            return this.node != sentinelTail;
        }
        public T next() {
            T temp = this.node.value;
            this.node = this.node.next;
            return temp;
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
}
