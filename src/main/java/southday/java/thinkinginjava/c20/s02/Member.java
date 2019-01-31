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
