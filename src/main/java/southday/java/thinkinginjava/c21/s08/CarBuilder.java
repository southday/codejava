package southday.java.thinkinginjava.c21.s08;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

class Car {
    private final int id;
    private boolean engine = false, driveTrain = false, wheels = false;
    public Car(int idn) { id = idn; }
    // empty car object
    public Car() { id = -1; }
    public synchronized int getId() { return id; }
    public synchronized void addEngine() { engine = true; }
    public synchronized void addDriveTrain() { driveTrain = true; }
    public synchronized void addWheels() { wheels = true; }
    public synchronized String toString() {
        return String.format("Car %d [ engine: %s drivenTrain: %s wheels: %s ]",
                id, engine, driveTrain, wheels);
    }
}

class CarQueue extends LinkedBlockingQueue<Car> {}

// 底盘
class ChassisBuilder implements Runnable {
    private CarQueue carQueue;
    private int counter = 0;
    public ChassisBuilder(CarQueue cq) { carQueue = cq; }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(500);
                // make chassis
                Car c = new Car(counter++);
                System.out.println("ChassisBuilder created " + c);
                // insert into queue
                carQueue.put(c);
            }
        } catch (InterruptedException e) {
            System.out.println("ChassisBuilder run interrupted");
        }
        System.out.println("ChassisBuilder off");
    }
}

// 装配器
class Assembler implements Runnable {
    private CarQueue chassisQueue, finishingQueue;
    private Car car;
    private CyclicBarrier barrier = new CyclicBarrier(4);
    private RobotPool robotPool;
    public Assembler(CarQueue cq, CarQueue fq, RobotPool rp) {
        chassisQueue = cq;
        finishingQueue = fq;
        robotPool = rp;
    }
    public Car car() { return car; }
    public CyclicBarrier barrier() { return barrier; }

    @Override
    public void run() {
       try {
           while (!Thread.interrupted()) {
               // blocks until chassis is available
               car = chassisQueue.take();
               // hire robots to perform work
               robotPool.hire(EngineRobot.class, this);
               robotPool.hire(DriveTrainRobot.class, this);
               robotPool.hire(WheelRobot.class, this);
               barrier.await(); // until the robots finish
               // put car into finishingQueue for further work
               finishingQueue.put(car);
           }
       } catch (InterruptedException e) {
           System.out.println("Assembler run interrupted");
       } catch (BrokenBarrierException e) {
           throw new RuntimeException(e);
       }
        System.out.println("Assembler off");
    }
}

class Reporter implements Runnable {
    private CarQueue carQueue;
    public Reporter(CarQueue cq) { carQueue = cq; }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted())
                System.out.println(carQueue.take());
        } catch (InterruptedException e) {
            System.out.println("Reporter run interrupted");
        }
        System.out.println("Reporter off");
    }
}

abstract class Robot implements Runnable {
    private RobotPool pool;
    public Robot(RobotPool p) { pool = p; }
    protected Assembler assembler;
    public Robot assignAssembler(Assembler assembler) {
        this.assembler = assembler;
        return this;
    }

    private boolean engage = false;
    public synchronized void engage() {
        engage = true;
        notifyAll();
    }
    // the part of run() that's different for each robot
    abstract protected void performService();

    @Override
    public void run() {
        try {
            powerDown(); // wait until needed
            while (!Thread.interrupted()) {
                performService();
                assembler.barrier().await(); // synchronize
                // we're done with that job...
                powerDown();
            }
        } catch (InterruptedException e) {
            System.out.println(this.getClass().getSimpleName() + " run interrupted");
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println(this.getClass().getSimpleName() + " off");
    }

    private synchronized void powerDown() throws InterruptedException {
        engage = false;
        assembler = null; // disconnect from the assembler
        // put ourselves back in the available pool
        pool.release(this);
        while (!engage)
            wait();
    }
    public String toString() { return getClass().getName(); }
}

class EngineRobot extends Robot {
    public EngineRobot(RobotPool pool) { super(pool); }
    @Override
    protected void performService() {
        System.out.println(this + " installing Engine");
        assembler.car().addEngine();
    }
}

class DriveTrainRobot extends Robot {
    public DriveTrainRobot(RobotPool pool) { super(pool); }
    @Override
    protected void performService() {
        System.out.println(this + " installing DriveTrain");
        assembler.car().addDriveTrain();
    }
}

class WheelRobot extends Robot {
    public WheelRobot(RobotPool pool) { super(pool); }
    @Override
    protected void performService() {
        System.out.println(this + " installing wheels");
        assembler.car().addWheels();
    }
}

class RobotPool {
    // quietly prevents identical entries
    private Set<Robot> pool = new HashSet<>();
    public synchronized void add(Robot r) {
        pool.add(r);
        notifyAll();
    }
    public synchronized void hire(Class<? extends Robot> robotType, Assembler d)
        throws InterruptedException {
        for (Robot r : pool) {
            if (r.getClass().equals(robotType)) {
                pool.remove(r); // HashSet也支持边遍历边删除？
                r.assignAssembler(d);
                r.engage(); // power it up to do the task
                return;
            }
        }
        wait(); // none available
        hire(robotType, d); // try again, recursively
    }
    public synchronized void release(Robot r) { add(r); }
}

/**
 * P746 分发工作<br/>
 * 考虑一个假想的用于汽车的机器人组装线，每辆Car都将分为多个阶段构建，
 * 从创建地盘开始，紧跟着是安装发动机、车厢和轮子
 * @author southday
 * @date 2019/4/9
 */
public class CarBuilder {
    public static void main(String[] args) throws Exception {
        CarQueue chassisQueue = new CarQueue(),
                 finishingQueue = new CarQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        RobotPool robotPool = new RobotPool();
        exec.execute(new EngineRobot(robotPool));
        exec.execute(new DriveTrainRobot(robotPool));
        exec.execute(new WheelRobot(robotPool));
        exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
        exec.execute(new Reporter(finishingQueue));
        exec.execute(new ChassisBuilder(chassisQueue));
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}

/* 输出
ChassisBuilder created Car 0 [ engine: false drivenTrain: false wheels: false ]
southday.java.thinkinginjava.c21.s08.WheelRobot installing wheels
southday.java.thinkinginjava.c21.s08.EngineRobot installing Engine
southday.java.thinkinginjava.c21.s08.DriveTrainRobot installing DriveTrain
Car 0 [ engine: true drivenTrain: true wheels: true ]
ChassisBuilder created Car 1 [ engine: false drivenTrain: false wheels: false ]
southday.java.thinkinginjava.c21.s08.EngineRobot installing Engine
southday.java.thinkinginjava.c21.s08.DriveTrainRobot installing DriveTrain
southday.java.thinkinginjava.c21.s08.WheelRobot installing wheels
Car 1 [ engine: true drivenTrain: true wheels: true ]
ChassisBuilder created Car 2 [ engine: false drivenTrain: false wheels: false ]
southday.java.thinkinginjava.c21.s08.DriveTrainRobot installing DriveTrain
southday.java.thinkinginjava.c21.s08.EngineRobot installing Engine
southday.java.thinkinginjava.c21.s08.WheelRobot installing wheels
Car 2 [ engine: true drivenTrain: true wheels: true ]
ChassisBuilder created Car 3 [ engine: false drivenTrain: false wheels: false ]
southday.java.thinkinginjava.c21.s08.EngineRobot installing Engine
southday.java.thinkinginjava.c21.s08.DriveTrainRobot installing DriveTrain
southday.java.thinkinginjava.c21.s08.WheelRobot installing wheels
Car 3 [ engine: true drivenTrain: true wheels: true ]
ChassisBuilder created Car 4 [ engine: false drivenTrain: false wheels: false ]
southday.java.thinkinginjava.c21.s08.EngineRobot installing Engine
southday.java.thinkinginjava.c21.s08.DriveTrainRobot installing DriveTrain
southday.java.thinkinginjava.c21.s08.WheelRobot installing wheels
Car 4 [ engine: true drivenTrain: true wheels: true ]
Reporter run interrupted
Reporter off
Assembler run interrupted
ChassisBuilder run interrupted
Assembler off
ChassisBuilder off
EngineRobot run interrupted
WheelRobot run interrupted
DriveTrainRobot run interrupted
EngineRobot off
WheelRobot off
DriveTrainRobot off
 */