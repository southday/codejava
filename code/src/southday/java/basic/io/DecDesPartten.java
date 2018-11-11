package southday.java.basic.io;
/*   装饰摸式与继承extends
 （1）继承extends：
 Computer   （父类）  现在需要对其进行功能扩展，如：可以：自定义屏幕背景
   |--AcerComputer
         |--ScreenAcerComputer
   |--LenovoComputer
         |--ScreenLenovoComputer
   |--AsusComputer
         |--ScreenAsusComputer
   ........
如果采用extends实现，则Computer下的每个子类，又需要额外的子类来实现功能扩展，
单纯得为了添加：自定义屏幕背景 功能就创建了一个子类，而其他功能则没有任何改动，这样显得没必要。
也就是说，在Computer子类中添加相同功能时，采用extends会显得臃肿，浪费多余空间。

（2）采用装饰模式：
因为添加的功能是相同的，所以可以把Acer,Lenovo,Asus...组合起来统一实现：
———— 因为是对电脑功能进行扩展，所以扩展下来还是Computer，则要extends Computer
class ScreenComputer extends Computer {
    private Computer comp;
    ScreenComputer(Computer com) {
        this.com = com;  //多态，这样就避免了每个子类都要创建相应的构造函数
    }
    public void Screen() {...}   //这是功能扩展部分
}

(3)总结下来：
Ⅰ.装饰模式比继承要灵活，避免了继承体系的臃肿，而且降低了类与类之间的关系；
Ⅱ.装饰类因为增强已有对象，具备的功能和已有是相同的，只不过提供了更强的功能，
————所以装饰类与被装饰类通常处于一个体系中
*/

class Person {
    public void eat() {
        System.out.println("吃饭");
    }
}

class SuperMan {
    private Person p;   
    SuperMan(Person p) {
        this.p = p;        //传Person的对象引用，对eat功能进行扩展
    }
    public void supereat() {
        System.out.println("开胃酒");
        p.eat();
        System.out.println("饭后甜点");
    }
}
public class DecDesPartten {
    public static void main(String[] args) {
        Person per = new Person();
        //per.eat();
        SuperMan sup = new SuperMan(per);
        sup.supereat();
    }
}
