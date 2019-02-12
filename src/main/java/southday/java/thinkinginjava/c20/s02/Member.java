package southday.java.thinkinginjava.c20.s02;

import southday.java.thinkinginjava.c20.s02.dbanno.Constraints;
import southday.java.thinkinginjava.c20.s02.dbanno.DBTable;
import southday.java.thinkinginjava.c20.s02.dbanno.SQLInteger;
import southday.java.thinkinginjava.c20.s02.dbanno.SQLString;

/**
 * 一个简单的Bean
 * Author southday
 * Date   2019/2/1
 */
@DBTable(name = "MEMBER")
public class Member {
    /* 书写快捷方式
    如果注解中定义了名为value的元素，并且在应用该注解的时候，
    如果该元素是唯一需要赋值的一个元素，那么此时无需使用<名,值>对的语法，而只需在括号内给出value元素所需的值即可，如下：
     */
    @SQLString(30) String firstName;
    @SQLString(50) String lastName;
    @SQLInteger Integer age;
    @SQLString(value = 30, constraints = @Constraints(primaryKey = true)) String handle;
    static int memberCount;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getHandle() {
        return handle;
    }

    @Override
    public String toString() {
        return handle;
    }
}
