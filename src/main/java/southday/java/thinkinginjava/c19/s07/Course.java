package southday.java.thinkinginjava.c19.s07;

import com.github.southday.mindview.util.Enums;

/**
 * P598 一道菜
 * @author southday
 * @date 2019/4/9
 */
public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);
    private Food[] values;

    Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public Food randomSelection() {
        return Enums.random(values);
    }
}
