package southday.java.acm;

import java.text.DecimalFormat;

public class AcmUtil {
    public static final DecimalFormat TWO_DECIMALS_FMT = new DecimalFormat("#.00");
    public static final String PINFV_STR = "∞";
    public static final String NINFV_STR = "-∞";
    
    public static void printMatrix(int[][] matrix) {
        System.out.println("------------------------");
        for (int[] arr : matrix) {
            for (int x : arr)
                System.out.print(x + " ");
            System.out.println();
        }
        System.out.println("------------------------");
    }
    
    public static void printMatrix(double[][] matrix, DecimalFormat df) {
        System.out.println("------------------------");
        for (double[] arr : matrix) {
            for (double x : arr) {
                if (x == Double.MAX_VALUE)
                    System.out.print(PINFV_STR + " ");
                else 
                    System.out.print(df.format(x) + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
    
    // 有时间再来写
    public static boolean isINFV(Object o, Class<? extends Number> type) {
        return true;
    }
}
