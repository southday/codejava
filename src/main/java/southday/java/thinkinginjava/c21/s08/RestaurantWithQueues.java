package southday.java.thinkinginjava.c21.s08;

import southday.java.thinkinginjava.c19.s07.Course;
import southday.java.thinkinginjava.c19.s07.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/*
1）总共有9种对象：Order，Plate，Customer，WaitPerson，Chef，Restaurant，RestaurantWithQueues，Food，Course；其中 Food，Course 两个类来自 【第19章 19.7 使用接口组织枚举】；

2）各类简介：
    * Order：订单类，主要包含：Customer，WaitPerson，Food；
    * Plate：由厨师提供的内容（当厨师准备好食物后会生产该类对象），主要包含：Order，Food；
    * Customer：顾客类，主要包含：WaitPerson，placeSetting（SynchronousQueue<Plate>一个没有内部容量的阻塞队列，用以强调：任何时刻只能上一道菜的概念）；
        * deliver(plate)方法：从服务员中接过plate，表示服务员给该顾客上了一道菜；
    * WaitPerson：服务员类，主要包含：Restaurant，filledOrders（BlockingQueue<Plate>保存从厨师那里拿到的plate）；
        * placeOrder(cust, food)方法：根据顾客的要求生成一份订单，后期会被厨师拿取；
    * Chef：厨师类，主要包含：Restaurant；
    * Restaurant：饭店类，主要包含：waitPersons（List<WaitPerson>），chefs（List<Chef>），orders（BlockingQueue<Order>）；
    * RestaurantWithQueues：饭店仿真测试驱动类；
    * Food：食物类，里面有多个枚举类型子类，表示不同的菜（每道菜中又有不同的内容）；
    * Course：一道菜（类），是个枚举类，与Food关联使用；
        * randomSelection()方法：随机选取一道菜；
 */

// This is given to the waiter, who gives it to the chef
class Order { // (A data-transfer object)
    private static int counter = 0;
    private final int id = counter++;
    private final Customer2 customer;
    private final WaitPerson waitPerson;
    private final Food food;
    public Order(Customer2 cust, WaitPerson wp, Food f) {
        customer = cust;
        waitPerson = wp;
        food = f;
    }
    public Food item() { return food; }
    public Customer2 getCustomer() { return customer; }
    public WaitPerson getWaitPerson() { return waitPerson; }
    public String toString() {
        return String.format("Order: %d item: %s for: %s served by: %s",
                id, food.toString(), customer.toString(), waitPerson.toString());
    }
}

// This is what comes back from the chef
class Plate {
    private final Order order;
    private final Food food;
    public Plate(Order ord, Food f) {
        order = ord;
        food = f;
    }
    public Order getOrder() { return order; }
    public Food getFood() { return food; }
    public String toString() { return food.toString(); }
}

class Customer2 implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final WaitPerson waitPerson;
    // Only one course at a time can be received
    // Java SE5的 SynchronousQueue，是一种没有内部容量的阻塞队列，因此每个put()都必须等待一个take()，反之亦然；
    private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();
    public Customer2(WaitPerson w) { waitPerson = w; }

    public void deliver(Plate p) throws InterruptedException {
        // only blocks if customer is still eating the previous course
        placeSetting.put(p);
    }

    @Override
    public void run() {
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                waitPerson.placeOrder(this, food);
                // blocks until course has been delivered
                System.out.println(this + "eating " + placeSetting.take());
            } catch (InterruptedException e) {
                System.out.println(this + "waiting for " + course + " interrupted");
                break;
            }
        }
        System.out.println(this + " finished meal, leaving");
    }
    public String toString() {
        return "Customer " + id + " ";
    }
}

class WaitPerson implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<>();
    public WaitPerson(Restaurant rest) { restaurant = rest; }

    public void placeOrder(Customer2 cust, Food food) {
        try {
            // shouldn't actually block because this is a LinkedBlockingQueue with no size limit
            restaurant.orders.put(new Order(cust, this, food));
        } catch (InterruptedException e) {
            System.out.println(this + " placeOrder interrupted");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // blocks until a course is ready
                Plate plate = filledOrders.take();
                System.out.println(this + "received " + plate + " delivering to "
                    + plate.getOrder().getCustomer());
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " off duty");
    }
    public String toString() {
        return "WaitPerson " + id + " ";
    }
}

class Chef implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    private static Random rand = new Random(47);
    public Chef(Restaurant rest) { restaurant = rest; }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // blocks until an order appears
                Order order = restaurant.orders.take();
                Food requestedItem = order.item();
                // time to prepare order
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order, requestedItem);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " off duty");
    }
    public String toString() {
        return "Chef " + id + " ";
    }
}

class Restaurant implements Runnable {
    private List<WaitPerson> waitPersons = new ArrayList<>();
    private List<Chef> chefs = new ArrayList<>();
    private ExecutorService exec;
    private static Random rand = new Random(47);
    BlockingQueue<Order> orders = new LinkedBlockingQueue<>();
    public Restaurant(ExecutorService e, int nWaitPersons, int nChefs) {
        exec = e;
        for (int i = 0; i < nWaitPersons; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }
        for (int i = 0; i < nChefs; i++) {
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // a new customer arrives; assign a waitPerson
                WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
                Customer2 c = new Customer2(wp);
                exec.execute(c);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Restaurant interrupted");
        }
        System.out.println("Restaurant closing");
    }
}
/**
 * P743 饭店仿真
 * @author southday
 * @date 2019/4/9
 */
public class RestaurantWithQueues {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec, 5, 2);
        exec.execute(restaurant);
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}

/* 输出（模拟了3秒）
WaitPerson 0 received SPRING_ROLLS delivering to Customer 1
Customer 1 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer 0
Customer 0 eating SPRING_ROLLS
WaitPerson 3 received SOUP delivering to Customer 2
Customer 2 eating SOUP
WaitPerson 0 received VINDALOO delivering to Customer 1
Customer 1 eating VINDALOO
WaitPerson 3 received BURRITO delivering to Customer 0
Customer 0 eating BURRITO
WaitPerson 1 received SPRING_ROLLS delivering to Customer 3
Customer 3 eating SPRING_ROLLS
WaitPerson 3 received HUMMOUS delivering to Customer 2
Customer 2 eating HUMMOUS
WaitPerson 4 received SALAD delivering to Customer 5
Customer 5 eating SALAD
WaitPerson 1 received SPRING_ROLLS delivering to Customer 4
Customer 4 eating SPRING_ROLLS
WaitPerson 3 received SOUP delivering to Customer 6
Customer 6 eating SOUP
WaitPerson 0 received FRUIT delivering to Customer 1
Customer 1 eating FRUIT
WaitPerson 0 received SALAD delivering to Customer 7
Customer 7 eating SALAD
WaitPerson 2 received SALAD delivering to Customer 8
Customer 8 eating SALAD
WaitPerson 3 received CREME_CARAMEL delivering to Customer 0
Customer 0 eating CREME_CARAMEL
WaitPerson 2 received SPRING_ROLLS delivering to Customer 9
Customer 9 eating SPRING_ROLLS
WaitPerson 3 received SOUP delivering to Customer 10
Customer 10 eating SOUP
WaitPerson 3 received SOUP delivering to Customer 11
Customer 11 eating SOUP
WaitPerson 1 received SPRING_ROLLS delivering to Customer 12
Customer 12 eating SPRING_ROLLS
WaitPerson 1 received HUMMOUS delivering to Customer 3
Customer 3 eating HUMMOUS
WaitPerson 3 received FRUIT delivering to Customer 2
Customer 2 eating FRUIT
WaitPerson 1 received LASAGNE delivering to Customer 4
Customer 4 eating LASAGNE
WaitPerson 4 received SOUP delivering to Customer 13
Customer 13 eating SOUP
WaitPerson 4 received HUMMOUS delivering to Customer 5
Customer 5 eating HUMMOUS
Restaurant interrupted
Restaurant closing
WaitPerson 3  interrupted
WaitPerson 4  interrupted
Chef 1  interrupted
WaitPerson 4  off duty
WaitPerson 3  off duty
WaitPerson 0  interrupted
WaitPerson 0  off duty
WaitPerson 1  interrupted
WaitPerson 1  off duty
Chef 1  off duty
WaitPerson 2  interrupted
WaitPerson 2  off duty
Customer 23 waiting for APPETIZER interrupted
Customer 17 waiting for APPETIZER interrupted
Customer 4 waiting for DESSERT interrupted
Customer 16 waiting for APPETIZER interrupted
Customer 16  finished meal, leaving
Customer 4  finished meal, leaving
Customer 8 waiting for MAINCOURSE interrupted
Customer 8  finished meal, leaving
Chef 0  interrupted
Customer 14 waiting for APPETIZER interrupted
Customer 14  finished meal, leaving
Customer 1 waiting for COFFEE interrupted
Customer 1  finished meal, leaving
Customer 7 waiting for MAINCOURSE interrupted
Customer 7  finished meal, leaving
Customer 11 waiting for MAINCOURSE interrupted
Customer 29 waiting for APPETIZER interrupted
Customer 29  finished meal, leaving
Customer 10 waiting for MAINCOURSE interrupted
Customer 5 waiting for DESSERT interrupted
Customer 25 waiting for APPETIZER interrupted
Customer 25  finished meal, leaving
Customer 9 waiting for MAINCOURSE interrupted
Customer 17  finished meal, leaving
Customer 23  finished meal, leaving
Customer 13 waiting for MAINCOURSE interrupted
Customer 27 waiting for APPETIZER interrupted
Customer 19 waiting for APPETIZER interrupted
Customer 19  finished meal, leaving
Customer 24 waiting for APPETIZER interrupted
Customer 26 waiting for APPETIZER interrupted
Customer 26  finished meal, leaving
Customer 24  finished meal, leaving
Customer 27  finished meal, leaving
Customer 13  finished meal, leaving
Customer 9  finished meal, leaving
Customer 5  finished meal, leaving
Customer 10  finished meal, leaving
Customer 15 waiting for APPETIZER interrupted
Customer 12 waiting for MAINCOURSE interrupted
Customer 20 waiting for APPETIZER interrupted
Customer 20  finished meal, leaving
Customer 22 waiting for APPETIZER interrupted
Customer 11  finished meal, leaving
Customer 3 waiting for DESSERT interrupted
Customer 2 waiting for COFFEE interrupted
Customer 21 waiting for APPETIZER interrupted
Customer 0 waiting for COFFEE interrupted
Customer 6 waiting for MAINCOURSE interrupted
Customer 6  finished meal, leaving
Customer 28 waiting for APPETIZER interrupted
Customer 18 waiting for APPETIZER interrupted
Chef 0  off duty
Customer 18  finished meal, leaving
Customer 28  finished meal, leaving
Customer 0  finished meal, leaving
Customer 21  finished meal, leaving
Customer 2  finished meal, leaving
Customer 3  finished meal, leaving
Customer 22  finished meal, leaving
Customer 12  finished meal, leaving
Customer 15  finished meal, leaving
 */