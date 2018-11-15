#### 协变与逆变

##### PECS原则

PECS原则 - 《Effective Java》第2版 P119
1. producer - extends （协变）
2. consumer - super （逆变）

- 协变 `<? extends E>`
    + 可读 E类
    + 不可写
- 逆变 `<? super E>`
    + 可写 E及E子类
    + 可读 Object类

我的理解：
1. producer，因为在别的地方经常会调用`producer.get()`，所以用 extends
2. consumer，因为可能从producer中获取到数据后会把内容插入到自己的表中，所以用 super

---

##### 类关系

```java
class Fruit {}
class Apple extends Fruit {}
class Orange extends Fruit {}
class RedApple extends Apple {}
```

---

##### 协变 \<? extends E>

`<? extends E>`：`?` 一定为 `E` 的一个子类(或者`E`类本身)，但具体是哪个类，我们不清楚；（`?`表示编译器不知道运行时具体类型是什么，只知道它`?`是某个具体的类型）

比如：声明`List<? extends Fruit> flist`
1. 由于它可以是：`List<Fruit>`、`List<Apple>`、`List<Orange>`、`List<RedApple>`中的任意一种，
所以无法进行`flist.add(obj)`操作，因为编译器无法确定`obj`为`?`类型（编译器连`?`是哪个具体类型都不知道），这么做是为了保证类型安全。
2. 但无论`?`是哪种具体的类型，编译器可以确定的是在执行`flist.get(0)`时获取的都是`Fruit`类型，因此`Fruit f = flist.get(0);`可以成功执行。

---

##### 逆变 \<? super E>

`<? super E>`：`?`一定为`E`的一个父类（或者`E`类本身）；（需要注意：`?`表示的是某个具体的类，只是我们还不确定是哪个类，所以用`?`表示）

比如：

```java
List<? super Apple> flist = new ArrayList<Apple>(); // (1)
List<? super Apple> flist = new ArrayList<Fruit>(); // (2)
List<? super Apple> flist = new ArrayList<RedApple>(); // (3)
// (1)、(2) 是可行的，因为Fruit为Apple的父类，而Apple为限定类型本身类型
// (3) 不可行，因为RedApple为Apple的子类
```

关于`flist.add(obj)`的可行性：

```java
List<? super Apple> flist = new ArrayList<Apple>();
flist.add(new Apple()); // (1)
flist.add(new RedApple()); // (2)
/* (1)、(2) 是可行的
因为 ? 一定为 Apple 的一个父类（或Apple类本身），而Apple又为RedApple的父类，
所以可以往flist中添加RedApple对象
*/

flist.add(new Fruit()); // (3)
flist.add(new Object()); // (4)
/* (3)、(4) 不可行
因为 ? 虽然为 Apple 的一个父类（或Apple类本身），但是编译器无法确定 ? 到底是 Apple的哪个父类，
它可以是 Fruit，也可以是 Object，所以为了类型安全，不允许添加 Apple的超类对象
*/
```

关于`flist.get(0)`的读取：从`flist`中读取元素，由于无法确定获取的元素具体是什么类型，只能确定一定是Object类型的子类，
所以在`get()`后需要强制转型：`Apple ap = (Apple)flist.get(0)`
