package southday.java.thinkinginjava.c20.s02.dbanno;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author southday
 * Date   2019/2/1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    String name() default "";
    /* 由于在@Constraints注解类型之后，没有在括号中指明@Constraints中的元素的值，
     * 因此，constraints()元素的默认值实际上就是一个所有元素都为默认值的@Constraints注解；
     */
    Constraints constraints() default @Constraints;
}
/*
如果要令@Constraints注解中的unique()元素为true，并以此作为constraints()元素的默认值，则需要如下定义：
然后直接使用Uniqueness注解即可；

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique=true);
}
 */
