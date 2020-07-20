package structure.queue;

/**
 * @author yanchao
 * @date 2020-07-17 19:12
 */
public class IntPriorityQueue {

    private int size = 0;
    private int[] nums;

    public IntPriorityQueue() {
        this(16);
    }

    /**
     * 将会把第一元素放置到 index = 1 的位置，所以初始化的数组要 + 1
     * @param initializer
     */
    public IntPriorityQueue(int initializer) {
        nums = new int[initializer + 1];
    }

    /**
     * 根据已有数组构造最小化堆。整个快排不好吗 -.-!!!
     * @param array 无序数组
     */
    public IntPriorityQueue(int[] array) {
        nums = new int[array.length + 10];
        System.arraycopy(array, 0, nums, 1, array.length);
        size = array.length;
        int notLeaf = size / 2;
        while (notLeaf >= 1) {
            siftDown(notLeaf, nums[notLeaf]);
            notLeaf--;
        }
    }

    public boolean addToMinDump(int i) {
        // TODO 考虑扩容
        int hole = ++size;
        // hole > 1 是考虑了最小化堆不存在元素的情况的
        for (; hole > 1 && i < nums[hole/2]; hole = hole / 2) {
                nums[hole] = nums[hole/2];
        }
        nums[hole] = i;
        return true;
    }

    public int pollFromMinDump() {
        int result = nums[1];
        int last = nums[size];
        nums[size] = 0;
        size--;
        if (size != 0) {
            siftDown(1, last);
        }
        return result;
    }

    private void siftDown(int index, int last) {
        while (index <= size / 2) {
            int left = index * 2;
            int right = left + 1;
            if (right <= size && nums[left] > nums[right]) {
                left = right;
            }
            if (last > nums[left]) {
                nums[index] = nums[left];
            } else {
                break;
            }
            index = left;
        }
        nums[index] = last;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public static void main(String[] args) {
        /*IntPriorityQueue priorityQueue = new IntPriorityQueue();
        priorityQueue.addToMinDump(1);
        priorityQueue.addToMinDump(3);
        priorityQueue.addToMinDump(2);
        priorityQueue.addToMinDump(4);
        priorityQueue.addToMinDump(5);
        priorityQueue.addToMinDump(6);
        priorityQueue.addToMinDump(7);
        */

        int[] array = new int[] {1,4,5,3,6,7,3,0};
        IntPriorityQueue priorityQueue = new IntPriorityQueue(array);
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.pollFromMinDump());
        }
    }
}
