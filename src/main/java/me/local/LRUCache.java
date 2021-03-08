package me.local;

public class LRUCache {

    static class InnerHeap {

        int[] heap = new int[1024];
        int idx = 0;

        public void insert(int value) {
            heap[idx] = value;
            int shiftIdx = idx;
            idx++;
            while (shiftIdx > 0) {
                int parentIdx = shiftIdx / 2;
                if (value > heap[parentIdx]) {
                    int temp = heap[parentIdx];
                    heap[parentIdx] = value;
                    heap[shiftIdx] = temp;
                    shiftIdx /= 2;
                } else {
                    break;
                }
            }
        }

        public int top() {
            int ret = heap[0];
            int shiftIdx = 0;
            heap[0] = heap[idx - 1];
            idx--;
            while (shiftIdx < idx) {
                int leftChildIdx = shiftIdx * 2;
                int rightChildIdx = shiftIdx * 2 + 1;
                int targetChildIdx = -1;
                if (leftChildIdx < idx && rightChildIdx < idx) {
                    if (heap[leftChildIdx] < heap[rightChildIdx]) {
                        if (heap[shiftIdx] < heap[rightChildIdx]) {
                            targetChildIdx = rightChildIdx;
                        }
                    } else if (heap[leftChildIdx] > heap[shiftIdx]) {
                        targetChildIdx = leftChildIdx;
                    }
                } else if (leftChildIdx < idx) {
                    if (heap[leftChildIdx] > heap[shiftIdx]) {
                        targetChildIdx = leftChildIdx;
                    }
                }
                if (targetChildIdx > 0) {
                    int temp = heap[shiftIdx];
                    heap[shiftIdx] = heap[targetChildIdx];
                    heap[targetChildIdx] = temp;
                    shiftIdx = targetChildIdx;
                } else {
                    break;
                }
            }
            return ret;
        }

        public int size() {
            return idx;
        }
    }


    void process() {
        int[] nums = new int[]{10, 3, 5, 193, 198, 12, 41,-100, 2, 45, 1, -10};
        InnerHeap heap = new InnerHeap();
        for (int n : nums) {
            heap.insert(n);
        }
        while (heap.size() > 0) {
            System.out.print(heap.top() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache();
        lruCache.process();
    }
}
