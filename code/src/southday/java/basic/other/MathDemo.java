package southday.java.basic.other;

import java.util.Random;

public class MathDemo {
    public static void main(String[] args) {
        // 【1】ceil返回大于指定数据的最小整数
        double d = Math.ceil(13.4); // 14.0
        sop("d = " + d);
        d = Math.ceil(-13.4); // -13.0
        sop("d = " + d);

        // 【2】floor返回小于指定数据的最大整数
        double dd = Math.floor(12.3); // 12.0
        sop("dd = " + dd);
        dd = Math.floor(-12.3); // -13.0
        sop("dd = " + dd);

        // 【3】found 返回舍入为最接近的 long (或int) 值的参数值。
        // 四舍五入
        long l = Math.round(12.49); // 12 舍
        sop("l = " + l);
        l = Math.round(12.50); // 13 入
        sop("l = " + l);

        // 【4】pow 幂数运算
        double dl = Math.pow(2, 3); // 2 的 3次方
        sop("dl = " + dl); // 8.0
        dl = Math.pow(-2, 5);
        sop("dl = " + dl); // -32.0

        // 【5】 random 随即函数
        // 返回带正号的 double 值，该值大于等于 0.0 且小于 1.0。返回值是一个伪随机选择的数，在该范围内（近似）均匀分布
        double dp = 0.0;
        for (int x = 0; x < 10; x++) {
            // dp = Math.random(); // 返回 大于等于 0.0 且小于 1.0 的伪随机 double 值
            // sop(dp);
            // 如果要使其返回 0~10（包括两端）的随即数，可以如下写：
            dp = Math.random() * 10 + 1; // random()*10 表示 0~10（不包括10）,+1时期包括10
            sop(dp);
        }
        // 使用 Random类中的方法 nextInt() nextDouble()...
        Random r = new Random();
        int num = 0;
        for (int x = 0; x < 10; x++) {
            num = r.nextInt(10); // 返回 0~10之间的数（不包括10）
            sop(num);
            // 要使其包括10，可以写为： num = r.nextInt(10) + 1;
        }
    }

    public static void sop(Object obj) {
        System.out.println(obj);
    }

}
