package southday.java.thinkinginjava.c21.s07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;
    public Horse(CyclicBarrier b) { barrier = b; }
    public synchronized int getStrides() { return strides; }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += rand.nextInt(3); // 0 | 1 | 2
                }
                barrier.await();
            }
        } catch (InterruptedException e) {
            System.out.println("Horse run() interrupted");
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++)
            s.append("*");
        s.append(id);
        return s.toString();
    }
}

/**
 * P725 CyclicBarrier 示例，赛马游戏
 * @author southday
 * @date 2019/4/8
 */
public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public HorseRace(int nHorse, final int pause) {
        barrier = new CyclicBarrier(nHorse, () -> {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < FINISH_LINE; i++)
                s.append("="); // The fence on the racetrack
            System.out.println(s);
            for (Horse horse : horses)
                System.out.println(horse.tracks());
            for (Horse horse : horses) {
                if (horse.getStrides() >= FINISH_LINE) {
                    System.out.println(horse + "won!");
                    exec.shutdownNow();
                    return;
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException e) {
                System.out.println("Barrier-action sleep interrupted");
            }
        });
        for (int i = 0; i < nHorse; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorse = 7;
        int pause = 200;
        new HorseRace(nHorse, pause);
    }
}
