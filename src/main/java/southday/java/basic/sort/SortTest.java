package southday.java.basic.sort;

import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {
        int[] dnums = {329, 4571, 657, 839, 436, 720, 355};
        RadixSort.sort(dnums, 4);
        System.out.println(Arrays.toString(dnums));
    }
}
