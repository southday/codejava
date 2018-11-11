package southday.java.acm.nowcoder.jzoffer;

/* 二维数组中的查找 
在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
*/

public class JZOffer_01_Solution_01 {
    public boolean Find(int target, int [][] array) {
        int r = array.length;
        int c = array[0].length;
        int x = r - 1, y = 0;
        while (x >= 0 && y < c) {
            if (target == array[x][y]) {
                return true;
            } else if (target < array[x][y]) {
                x--;
            } else {
                y++;
            }
        }
        return false;
    }
}
