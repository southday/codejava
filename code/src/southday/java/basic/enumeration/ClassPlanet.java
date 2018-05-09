package southday.java.basic.enumeration;

import java.util.ArrayList;
import java.util.List;

public class ClassPlanet {
    // 对照 EnumPlanet.java
    public static final ClassPlanet MERCURY = new ClassPlanet("MERCURY", 3.302e+23, 2.439e6);
    public static final ClassPlanet VENUS = new ClassPlanet("VENUS", 4.869e+24, 6.052e6);
    public static final ClassPlanet EARTH = new ClassPlanet("EARTH", 5.975e+24, 6.378e6);
    public static final ClassPlanet MARS = new ClassPlanet("MARS", 6.419e+23, 3.393e6);
    public static final ClassPlanet JUPITER = new ClassPlanet("JUPITER", 1.899e+27, 7.149e7);
    public static final ClassPlanet SATURN = new ClassPlanet("SATURN", 5.685e26, 6.027e7);
    public static final ClassPlanet URANUS = new ClassPlanet("URANUS", 8.682e+25, 2.556e7);
    public static final ClassPlanet NEPTUNE = new ClassPlanet("NEPTUNE", 1.024e+26, 2.477e7);

    private final String name; // 行星名称
    private final double mass; // 质量
    private final double radius; // 半径
    private final double surfaceGravity;  // 表面重力
    private static final double G = 6.67300E-11;
    private static final List<ClassPlanet> planets = new ArrayList<ClassPlanet>();
    
    // 构造器
    ClassPlanet(String name, double mass, double radius) {
        this.name = name;
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
    
    // 这里为了方便模拟，没有考虑到安全性（并发安全）等问题，本来不应把类中私有的planets直接发布到外部的（可以返回拷贝）
    public static List<ClassPlanet> values() {
        if (planets.isEmpty()) {
            planets.add(MERCURY);
            planets.add(VENUS);
            planets.add(EARTH);
            planets.add(MARS);
            planets.add(JUPITER);
            planets.add(SATURN);
            planets.add(URANUS);
            planets.add(NEPTUNE);
        }
        return planets;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    // 测试
    public static void main(String[] args) {
        // 根据某个物体在地球上的重量，显示出该物体在所有8颗行星上的重量（单位相同）
        double earthWeight = 175.0;
        double mass = earthWeight / ClassPlanet.EARTH.surfaceGravity();
        for (ClassPlanet p : ClassPlanet.values()) {
            // C语言中用 \n，而在这里用 %n
            System.out.printf("Weight on %s is %f%n", p.toString(), p.surfaceWeight(mass));
        }
    }
}
