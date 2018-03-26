package southday.java.basic.exception;
interface Shape {
    void getArea();
}

class NovalueException extends RuntimeException {
    NovalueException (String message) {
        super(message);
    }
}

class sec implements Shape {
    private int len;
    private int wid;

    sec(int len,int wid) {
        if (len <= 0 || wid <= 0)
            throw new NovalueException("出现非法值");
        this.len = len;
        this.wid = wid;
    }
    public void getArea() {
        System.out.println(len*wid);
    }
}

class cir implements Shape
{
    private int radius;
    public static final double PI = 3.14;
    cir(int radius) {
        if (radius <= 0)
            throw new NovalueException("非法半径");
        this.radius = radius;
    }
    public void getArea() {
        System.out.println(radius*radius*PI);
    }
}

class Finall
{
    public static void main(String[] args) {
        sec s = new sec(-3,4);
        s.getArea();
        
        cir c = new cir(4);
        c.getArea();
    }
}
