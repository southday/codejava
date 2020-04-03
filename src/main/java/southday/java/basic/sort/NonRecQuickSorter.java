package southday.java.basic.sort;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 非递归快速排序
 * @author southday
 * @email chaoxi.li@ai-ways.com
 * @date 2020/4/3
 */
public class NonRecQuickSorter extends Sorter {
    @Override
    public void sort(double[] arr) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(0, arr.length - 1));
        while (!queue.isEmpty()) {
            Node node= queue.poll();
            int p = partition(arr, node.left, node.right);
            if (node.left < p - 1) {
                queue.add(new Node(node.left, p - 1));
            }
            if (p + 1 < node.right) {
                queue.add(new Node(p + 1, node.right));
            }
        }
    }

    private static class Node {
        int left, right;

        Node(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    private int partition(double[] arr, int left, int right) {
        double pivot = arr[right];
        int i = left - 1, j = left;
        for (; j < right; j++) {
            if (arr[j] < pivot) {
                double temp = arr[j];
                arr[j] = arr[++i];
                arr[i] = temp;
            }
        }
        arr[j] = arr[++i];
        arr[i] = pivot;
        return i;
    }

    @Override
    public void sort(int[] arr) {
        throw new UnsupportedOperationException("unsupported sor(int[] arr){}");
    }
}
