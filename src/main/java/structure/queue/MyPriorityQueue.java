package structure.queue;

import org.junit.Test;

import java.util.Comparator;

/**
 * 最大或最小堆 -- 自定义优先级队列
 * @author yanchao
 * @date 2020-07-17 14:59
 */
public class MyPriorityQueue<T> {


    private static final int DEFAULT_INITIALIZER = 16;
    private Object[] items;
    private int size = 0;

    private Comparator<? super T> comparator;

    public MyPriorityQueue() {
        this(DEFAULT_INITIALIZER);
    }

    public MyPriorityQueue(int initializer) {
        this(initializer, null);
    }

    public MyPriorityQueue(Comparator<? super T> comparator) {
        this(DEFAULT_INITIALIZER, comparator);
    }

    public MyPriorityQueue(int initializer, Comparator<? super T> comparator) {
        this.items = new Object[initializer];
        this.comparator = comparator;
    }

    public boolean add(T item) {
        growingIfNecessary();
        if (size == 0) {
            items[0] = item;
        } else {
            siftUp(size, item);
        }
        size++;
        return true;
    }

    public T poll() {
        if (size == 0) {
            return null;
        }
        int s = --size;
        T result = (T) items[0];
        T last = (T) items[s];
        items[s] = null;
        // 不是一个元素，如果是一个元素则不用重排了
        if (s != 0) {
            siftDown(0, last);
        }
        return result;
    }

    public T peek() {
        return size == 0 ? null : (T) items[0];
    }

    private void growingIfNecessary() {
        if (size >= items.length) {
            int newItemsSize = items.length << 1;
            Object[] newItems = new Object[newItemsSize];
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems;
        }
    }

    private void siftUp(int index, T item) {
        if (comparator != null) {
            siftUpByComparator(index, item);
        } else {
            siftUpComparable(index, item);
        }
    }

    private void siftUpByComparator(int index, T item) {
        while (index >= 0) {
            int parentIndex = (index - 1) / 2;
            Object parent = items[parentIndex];
            if (this.comparator.compare(item, (T) parent) >= 0) {
                break;
            }
            items[index] = parent;
            index = parentIndex;
        }
        items[index] = item;
    }

    private void siftUpComparable(int index, T item) {
        Comparable<? super T> insert = (Comparable<? super T>) item;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            Object parent = items[parentIndex];
            if (insert.compareTo((T) parent) >= 0) {
                break;
            }
            items[index] = parent;
            index = parentIndex;
        }
        items[index] = item;
    }

    private void siftDown(int k, T e) {
        if (this.comparator != null) {
            siftDownByComparator(k, e);
        } else {
            siftDownComparable(k, e);
        }
    }

    private void siftDownByComparator(int k, T x) {
        while (k < size / 2) {
            int left = (k << 1) + 1;
            int right = left + 1;
            if (right < size && this.comparator.compare((T) items[left], (T) items[right]) > 0) {
                left = right;
            }
            if (this.comparator.compare(x, (T) items[left]) <= 0) {
                break;
            }
            items[k] = items[left];
            k = left;
        }
        items[k] = x;
    }

    private void siftDownComparable(int k, T x) {
        Comparable<? super T> last = (Comparable<? super T>) x;
        while (k < size / 2) {
            int left = (k << 1) + 1;
            int right = left + 1;
            if (right < size && ((Comparable<? super T>) items[left]).compareTo((T) items[right]) > 0) {
                left = right;
            }
            if (last.compareTo((T) items[left]) <= 0) {
                break;
            }
            items[k] = items[left];
            k = left;
        }
        items[k] = x;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        MyPriorityQueue<Integer> priorityQueue = new MyPriorityQueue<>();
        priorityQueue.add(1);
        priorityQueue.add(3);
        priorityQueue.add(2);
        priorityQueue.add(4);
        priorityQueue.add(5);
        priorityQueue.add(6);
        priorityQueue.add(7);

        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }

    }
}
