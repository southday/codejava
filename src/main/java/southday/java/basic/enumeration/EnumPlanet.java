package southday.java.basic.enumeration;

/**
 * <code>Planet</code>枚举类型<p>
 * 学自：Effective Java - chapter 6
 * @author southday
 * @date 2018年5月7日
 */
public enum EnumPlanet {
    // 在我看来，如果是Planet为类，那么 MERCURY, VENUS, EARTH ... 这些就是 Planet类对象
    // 参看 ClassPlanet.java 实现
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e26, 6.027e7),
    URANUS(8.682e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);
    
    private final double mass; // 质量
    private final double radius; // 半径
    private final double surfaceGravity;  // 表面重力
    private static final double G = 6.67300E-11;
    
    // 构造器
    EnumPlanet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }
    
    public double mass() {
        return mass;
    }
    
    public double radius() {
        return radius;
    }
    
    public double surfaceGravity() {
        return surfaceGravity;
    }
    
    // 通过行星表明重力，给定一个物体的质量，可以计算出该物体在行星表面上的重量
    public double surfaceWeight(double mass) {
        return mass * surfaceGravity; // F = ma
    }
    
    // 仅为了测试，正常来说不会在enum中写main方法
    public static void main(String[] args) {
        // 根据某个物体在地球上的重量，显示出该物体在所有8颗行星上的重量（单位相同）
        double earthWeight = 175.0;
        double mass = earthWeight / EnumPlanet.EARTH.surfaceGravity();
        for (EnumPlanet p : EnumPlanet.values()) {
            // C语言中用 \n，而在这里用 %n（经过测试，在java中用\n也行-JDK-10.0.1）
            System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
        }
    }
}
