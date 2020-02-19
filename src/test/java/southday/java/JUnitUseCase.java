package southday.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Author southday
 * Date   2019/2/11
 */
public class JUnitUseCase {

    @Before
    public void loo() {
        System.out.println("before");
    }

    @Test
    public void assertFoo() {
        System.out.println("assertFoo");
        assert 1 == 1 : "What a surprise";
    }

    @Test
    public void soo() {
        System.out.println("soo");
        assert 1 == 2 : "error";
    }

    @After
    public void too() {
        System.out.println("after");
    }

    public static void main(String[] args) {
        List<String> lst = null;
        lst = new ArrayList<>();
        lst.add("1");
        lst.add("2");
    }
}
