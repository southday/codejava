package southday.java.thinkinginjava.c21.s07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta; // 延迟时长
    private final long trigger; // 触发时刻
    protected static List<DelayedTask> sequence = new ArrayList<>(); // 保存任务的创建顺序

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        if (id == 20) {
            trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(1000, TimeUnit.MILLISECONDS);
        } else {
            trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        }
        sequence.add(this);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask)o;
        return trigger < that.trigger ? -1 : trigger > that.trigger ? 1: 0;
    }

    @Override
    public void run() {
        System.out.print(this + " ");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    public String toString() {
        return String.format("[%d:Task-%d]", delta, id);
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;

        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }

        public void run() {
            // 按任务的创建顺序打印
            for (DelayedTask pt: sequence)
                System.out.print(pt.summary() + " ");
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted())
                q.take().run(); // run task with the current thread
        } catch (InterruptedException e) {
            System.out.println("DelayedTaskCosumer run interrupted");
        }
    }
}

/**
 * P727
 * @author southday
 * @date 2019/4/8
 */
public class DelayQueueDemo {
    public static void main(String[] args) {
        Random rand = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i = 0; i < 20; i++)
            queue.put(new DelayedTask(rand.nextInt(5000)));
        // set the stopping point, add(e) 和 put(e) 无区别，内部都是调用 offer(e)
        queue.put(new DelayedTask(1));
        queue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }
}
