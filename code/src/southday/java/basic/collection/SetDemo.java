package southday.java.basic.collection;

/*
  |——Set: 元素书无序的（存入和取出顺序不一定一致），元素是不重复的
      |--HashSet: 底层数据结构是哈希表
          HashSet是如何保证元素的唯一性呢？
          答：是通过两个方法：hashCode()和equals()来完成，如果元素的hashCode值相同，才会判断equals是否为true.
            如果元素的hashCode值不同，则不会调用equals。        //ArrayList依赖的是equals！！！
          注意：对于判断元素是否存在以及删除等操作，依赖的方法是元素的hashCode和equals方法。先判断hashCode，有了才判断equals
      |--TreeSet: 可以对Set集合中的元素进行排序--比如按字母的自然顺序(其ASCII码大小从小到大）排序
          底层数据结构是：二叉树
        保证元素唯一性依据：compareTo()方法来确定元素是否相同.return 0表示相同
        
    （1）TreeSet排序的第一种方式：  这种方式也称为元素的自然顺序（默认顺序）
        让元素自身具备比较性，元素所属类需要实现Comparable接口，覆盖compareTo方法
    （2）TreeSet排序的第二种方式：
        当元素自身不具备比较性时，或者具备的比较性不是所需要的，这时需要让【集合自身具备比较性】
        在集合初始化时就有了比较方式
 */

class TPerson {
    private String name;
    private int age;

    TPerson(String name,int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    
    public boolean equals(Object obj) {    //在复写hashCode()方法前，equals方法几乎是不被访问的（如果被访问到则说明出现了哈希碰撞情况）
        if(!(obj instanceof TPerson))    //而在复习hashCode()后，对象的hash值由name.hashCode()+age来确定，就更容易引起碰撞，
            return false;                //其实也就是名字和年龄相同时，hash值一样，这么以来就会调用到equals方法，来判断两个对象是否相同
        
        TPerson p = (TPerson)obj;
        System.out.println(p.name+"..equals.."+p.age);    //只有在对象出现hash碰撞（二者hash值一样时）equals方法被调用
        return (this.name.equals(p.name) && this.age==p.age);
    }
    /* 即使我们定义了类对象相同的规则，执行的是覆写过的语句，但我们锁认为的重复对象("li4",2)并没有被去除
     * 因为他们的哈希值不同，所以根本就不用执行equals进行比较。
     * 要解决这个问题，需要我们对hashCode()这个函数进行复写，根据我们定义的规则来得到对象的哈希值，从而实现相同对象的丢弃。
     */
    public int hashCode() {
        System.out.println(this.name+"---hashCode");
        return (this.name.hashCode()+this.age);    //根据字符串name的哈希值加age得到
    }
}

class TStudent implements Comparable { //实现该接口，强制让学生对象具备比较性
    private String name;
    private int age;
    
    TStudent(String name, int age) {
        this.age = age;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        return this.age;
    }
    
    //比较此对象与指定对象的顺序
    //如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
    public int compareTo(Object obj) {
        if(!(obj instanceof TStudent))
            throw new RuntimeException("The object is not TStudent");
        TStudent s = (TStudent)obj;
        
        System.out.println(this.name+"...compare to..."+s.name);
        System.out.println("----------------------------");
        if(this.age>s.age)
            return 1;
        else if(this.age==s.age) //String类中实现了compareTo方法,按字符串中字符的Unicode值升序排序
            return this.name.compareTo(s.name);
        else
            return -1;
    }
}
