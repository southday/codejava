package southday.java.acm.other;

import java.util.ArrayList;
import java.util.List;

/* 
 * 输入：int[] array，包含正负整数
 * 输出：最大子数组的和，以及起始位，结束位
 * 如：
 * 输入：[-1, 2, 3, -4]
 * 输出：5 1 2
 * 
 * 包括有多组最大子数组
 */
public class MaxSubArraySumSolution {
    
    private static class Node {
        int sum = 0;
        int start = 0;
        int end = 0;
        
        Node(int sum, int start, int end) {
            this.sum = sum;
            this.start = start;
            this.end = end;
        }
    }
    
    public List<Node> maxSubArraySum(int[] array) {
        if (array.length == 0) {
            return null; 
        }
        int i = -1;
        Node maxNode = new Node(Integer.MIN_VALUE, -1, -1);
        List<Node> result = new ArrayList<Node>();
        while (++i < array.length && array[i] <= 0) {
            if (array[i] > maxNode.sum) {
                maxNode.sum = array[i];
            }
        }
        if (i == array.length) {
            result.add(maxNode);
            return result;
        }
        
        int sum = 0, left = -1;
        for (; i < array.length; i++) {
            if (sum == 0 && array[i] <= 0) {
                continue;
            }
            if (left == -1) {
                left = i;
            }
            sum += array[i];
            if (sum <= 0) {
                sum = 0;
                left = -1;
            } else if (sum > maxNode.sum) {
                maxNode.sum = sum;
                maxNode.start = left;
                maxNode.end = i;
                result.clear();
                Node node = new Node(sum, left, i);
                result.add(node);
            } else if (sum == maxNode.sum) {
                Node node = new Node(sum, left, i);
                result.add(node);
            }
                
        }
        return result;
    }
    
    public static void main(String[] args) {
        MaxSubArraySumSolution o = new MaxSubArraySumSolution();
        int array[] = {-1,2,3,-4,-5,2,3,-1,7};
//        int array[] = {-1,2,3,-4,-2,-3,7};
//        int array[] = {-1,2,-5,3,-4};
//        int array[] = {-1,20,-5,30,-4};
//        int array[] = {-2,-3,-5,-1,-9};
        for (Node n : o.maxSubArraySum(array)) {
            System.out.println(n.sum + ", " + n.start + ", " + n.end);
        }
    }
}
