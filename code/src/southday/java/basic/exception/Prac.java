package southday.java.basic.exception;
//毕老师用电脑讲课
class Teacher {
    private String teacher;
    private Computer compt;
    Teacher(String teacher) {
        this.teacher = teacher;
        compt = new Computer();
    }
    public String getTeacher() {
        return teacher;
    }
    void lecture() throws NoPlanException {
        try {
            compt.run();
        } catch (LanPException e) {
            compt.rest();
            e.getMessage();
        } catch (MaoYException e) {
            System.out.println(e.toString());
            throw new NoPlanException("课时无法按计划完成");
        }
    }
}
class NoPlanException extends Exception {
    private String msg;
    NoPlanException(String msg) {
        super(msg);
    }
}
class LanPException extends Exception {
    private String msg;
    LanPException(String msg) {
        super(msg);
    }
}
class MaoYException extends Exception {
    private String msg;
    MaoYException(String msg) {
        super(msg);
    }
}
class Computer {
    private int state = 3;
    void run()throws LanPException,MaoYException {
        if(state==2)
            throw new LanPException("电脑蓝屏了");
        if(state==3)
            throw new MaoYException("电脑冒烟了");
        System.out.println("电脑运行");
    }
    
    void rest() {
        state = 1;
        System.out.println("电脑重启");
    }
}

class Prac {
    public static void main(String[] args) { //throws NoPlanException
        Teacher t = new Teacher("毕老师");
        try {
            t.lecture();
            System.out.println(t.getTeacher()+"讲课");
        } catch (NoPlanException e) {
            System.out.println(e.toString());
            System.out.println("换老师或者放假");
        }
        System.out.println("over");
        
    }
}
