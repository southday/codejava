package southday.java.jvm;

/**
 * 使用finalize()来逃脱GC（只能逃脱一次，finalize()至多只会被系统调用一次）
 * @author southday
 * @date 2019/3/3
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public static void main(String[] args) throws Exception {
        SAVE_HOOK = new FinalizeEscapeGC();
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (null != SAVE_HOOK) {
            System.out.println("Yes, I am still alive");
        } else {
            System.out.println("No, I am dead");
        }
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (null != SAVE_HOOK) {
            System.out.println("Yes, I am still alive");
        } else {
            System.out.println("No, I am dead");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("execute method finalize()");
        SAVE_HOOK = this;
    }
}
/* 输出
execute method finalize()
Yes, I am still alive
No, I am dead
*/
