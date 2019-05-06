package southday.java;

import java.util.HashSet;
import java.util.Set;

class Person {
    private int id;
    public Person(int id) { this.id = id; }
    public int getId() { return id; }
    public String toString() {
        return "Person-" + id;
    }
}

/**
 * 在遍历Set的同时remove元素
 * @author southday
 * @date 2019/4/10
 */
public class SetTravelRemoveTest {
    public static void main(String[] args) {
        Set<Person> pset = new HashSet<>();
        for (int i = 0; i < 50; i++)
            pset.add(new Person(i));
        for (Person p : pset)
            if (p.getId() % 2 == 0)
                pset.remove(p);
        for (Person p : pset)
            System.out.println(p);
    }
}
