package southday.java.thinkinginjava.c21.s08;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Read-only objects don't require synchronization
class Customer {
    private final int serviceTime;
    public Customer(int tm) { serviceTime = tm; }
    public int getServiceTime() { return serviceTime; }
    public String toString() {
        return "[" + serviceTime + "]";
    }
}

// Teach the customer line to display itself
class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int capacity) {
        super(capacity);
    }
    public String toString() {
        if (this.size() == 0)
            return "[Empty]";
        StringBuilder result = new StringBuilder();
        for (Customer c : this)
            result.append(c);
        return result.toString();
    }
}

// Randomly add cutomers to a queue
class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static Random rand = new Random(47);
    public CustomerGenerator(CustomerLine cq) {
        customers = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                customers.add(new Customer(rand.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator terminating");
    }
}

class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;
    // Customers served during this shift
    private int customersServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;
    public Teller(CustomerLine cq) {
        customers = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer c = customers.take();
                TimeUnit.MILLISECONDS.sleep(c.getServiceTime());
                synchronized (this) {
                    customersServed++;
                    while (!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "terminating");
    }

    // used by priority queue
    @Override
    public int compareTo(Teller o) {
        return customersServed < o.customersServed ? -1 : customersServed > o.customersServed ? 1 : 0;
    }

    public synchronized void doSomeThingElse() {
        customersServed = 0; // clear
        servingCustomerLine = false;
    }
    public synchronized void serveCustomerLine() {
        assert !servingCustomerLine : "already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    public String toString() { return "Teller " + id + " "; }
    public String shortString() { return "T" + id; }
}

class TellerManager implements Runnable {
    private ExecutorService exec;
    private CustomerLine customers;
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedList<>();
    private int adjustmentPeriod; // 动态调整间隔时间
    private static Random rand = new Random(47);

    public TellerManager(ExecutorService e, CustomerLine cq, int adjustmentPeriod) {
        exec = e;
        customers = cq;
        this.adjustmentPeriod = adjustmentPeriod;
        // Start with a single teller
        Teller teller = new Teller(customers);
        exec.execute(teller);
        workingTellers.add(teller);
    }

    // 动态调整服务器数量，这里的服务器特指：出纳员
    public void adjustTellerNumber() {
        if (customers.size() / workingTellers.size() > 2) {
            // if tellers are on break or doing another job, bring one back
            // 出纳员（线程）并为停止工作，只是在不同的任务（servingCustomerLine or other thing）间切换
            if (tellersDoingOtherThings.size() > 0) {
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomerLine(); // 设置 servingCustomerLine = true
                workingTellers.offer(teller);
                return;
            }
            // else create (hire) a new teller
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTellers.offer(teller);
            return;
        }
        // if line is short enough, remove a teller
        if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2)
            reassignOneTeller();
        // if there is no line, we only need one teller
        if (customers.size() == 0)
            while (workingTellers.size() > 1)
                reassignOneTeller();
    }

    // give a teller a different job or a task
    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomeThingElse();
        tellersDoingOtherThings.add(teller);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber(); // 动态调整出纳员数量
                System.out.print(customers + " { ");
                for (Teller teller : workingTellers)
                    System.out.print(teller.shortString() + " ");
                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "terminating");
    }

    public String toString() { return "TellerManager "; }
}

/**
 * P740 银行出纳员仿真
 * @author southday
 * @date 2019/4/9
 */
public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000; // MILLISECOND

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
        TimeUnit.SECONDS.sleep(6);
        exec.shutdownNow();
    }
}

/* 输出，模拟了6秒
[200][207] { T1 T0 }
[258][140][322] { T0 }
[140][322][383][575][342][804][826][896][984] { T1 T0 }
[896][984][810][141][12][689][992][976][368][395][354] { T2 T0 T1 }
[12][689][992][976][368][395][354][222][687][634][317][242][698][899] { T3 T2 T1 T0 }
CustomerGenerator interrupted
CustomerGenerator terminating
TellerManager interrupted
Teller 3 interrupted
Teller 0 interrupted
Teller 2 interrupted
Teller 1 interrupted
TellerManager terminating
Teller 0 terminating
Teller 3 terminating
Teller 2 terminating
Teller 1 terminating
 */