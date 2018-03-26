package southday.java.basic.exception;
class Cacu {
    int dec(int a,int b)throws FushuException {
        if(b < 0)
            throw new FushuException("除数小于0了");
        return a/b;    
    }
}
/*
因为父类Throwable已经有此函数： 即
class Throwable {
    private String message;
    Throwable(String message) {
       this.message = message;
    }
    public String getMessage() {
       return message;
    }
}  
而Exception extends Throwable
所以子类直接用就行，还可以进行覆写
FushuException(String msg, int num) {
   super(msg);
   this.num = num;
}
*/
class FushuException extends RuntimeException {
    //private int num;
    FushuException(String msg) {
        super(msg);
        //this.num = num;
    }
    //public int getNum()
    //{
    //    return num;
    //}
}
class Exce {
    public static void main(String[] args) {
        Cacu k = new Cacu();
        int x = k.dec(5, -5);
        System.out.println("x = "+x);
        //System.out.println("错误的负数是"+FushuException.getNum());
        /*
        try {
           int x = k.dec(5, -4);
           System.out.println("x = "+x);
        }
        catch (FushuException e) {
            System.out.println(e.toString());
            System.out.println("错误的负数是"+e.getNum());
        }
        System.out.println("over");
        */
    }
}
