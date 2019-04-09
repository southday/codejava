package southday.java.thinkinginjava.c21.s02;

import java.util.concurrent.Callable;

/**
 * P658 测试 Callable 接口，带返回值
 * @author southday
 * @date 2019/4/5
 */
public class TaskWithResult implements Callable<String> {
    private int id;
    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() {
        return "result of TaskWithResult " + id;
    }
}
