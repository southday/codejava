package southday.java.thinkinginjava.c20.s05;

import com.github.southday.mindview.atunit.ClassNameFinder;
import com.github.southday.mindview.util.*;
import com.sun.jdi.InvocationException;
import southday.java.thinkinginjava.c20.s05.anno.Test;
import southday.java.thinkinginjava.c20.s05.anno.TestObjectCleanup;
import southday.java.thinkinginjava.c20.s05.anno.TestObjectCreate;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.github.southday.mindview.util.Print.print;
import static com.github.southday.mindview.util.Print.printnb;

/**
 * Author southday
 * Date   2019/2/11
 */
public class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<>();
    static long testsRun = 0;
    static long failures = 0;

    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true); // enable asserts
        new ProcessFiles(new AtUnit(), "class").start(args);
        if (failures == 0)
            print("OK (" + testsRun + " tests)");
        else {
            print("(" + testsRun + " tests)");
            print("\n>>> " + failures + " FAILURE" + (failures > 1 ? "S" : "") + " <<<");
            for (String failed : failedTests)
                print("  " + failed);
        }
    }

    @Override
    public void process(File cFile) {
        try {
            String cName = ClassNameFinder.thisClass(BinaryFile.read(cFile));
            if (!cName.contains("."))
                return; // ignore unpackaged classes
            testClass = Class.forName(cName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TestMethods testMethods = new TestMethods();
        Method creator = null;
        Method cleanup = null;
        for (Method m : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(m);
            if (creator == null)
                creator = checkForCreatorMethod(m);
            if (cleanup == null)
                cleanup = checkForCleanupMethod(m);
        }
        if (testMethods.size() > 0) {
            if (creator == null) {
                try {
                    if (!Modifier.isPublic(testClass.getDeclaredConstructor().getModifiers())) {
                        print("Error: " + testClass + " default cosntructor must be public");
                        System.exit(1);
                    }
                } catch (NoSuchMethodException e) {
                    // Synthesized（合成/综合） default constructor; ok
                }
            }
            print(testClass.getName());
        }
        for (Method m : testMethods) {
            printnb(" . " + m.getName() + " ");
            try {
                // 测试每个方法都要重新创建一个实例
                Object testObject = createTestObject(creator);
                boolean success = false;
                try {
                    if (m.getReturnType().equals(boolean.class))
                        success = (Boolean) m.invoke(testObject);
                    else {
                        m.invoke(testObject);
                        success = true; // if no assert fails
                    }
                } catch (Exception e) {
                    // actual exception is inside e;
                    print(e.getCause());
                }
                print(success ? "" : "(failed)");
                testsRun++;
                if (!success) {
                    failures++;
                    failedTests.add(testClass.getName() + ": " + m.getName());
                }
                if (cleanup != null)
                    cleanup.invoke(testObject, testObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method m) {
            if (m.getAnnotation(Test.class) == null)
                return;
            if (!(m.getReturnType().equals(boolean.class)
                || m.getReturnType().equals(void.class)))
                throw new RuntimeException("@Test method must return boolean or void.");
            m.setAccessible(true); // in case it's private, etc.
            add(m);
        }
    }

    private static Method checkForCreatorMethod(Method m) {
        if (m.getAnnotation(TestObjectCreate.class) == null)
            return null;
        if (!m.getReturnType().equals(testClass))
            throw new RuntimeException("@TestObjectCreate must return instance of Class to be tested.");
        if ((m.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCreate must be static.");
        m.setAccessible(true);
        return m;
    }

    private static Method checkForCleanupMethod(Method m) {
        if (m.getAnnotation(TestObjectCleanup.class) == null)
            return null;
        if (!m.getReturnType().equals(void.class))
            throw new RuntimeException("@TestObjectCleanup must return void.");
        if ((m.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCleanup must be static.");
        if (m.getParameterTypes().length == 0 || m.getParameterTypes()[0] != testClass)
            throw new RuntimeException("@TestObjectCleanup must take an argument of the tested type.");
        m.setAccessible(true);
        return m;
    }

    private static Object createTestObject(Method creator) {
        if (creator != null) {
            try {
                return creator.invoke(testClass);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't run @TestObject (creator) method.");
            }
        } else { // use the default constructor;
            try {
                return testClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("@Couldn't create a test Object. Try using a @TestObject method.");
            }
        }
    }
}
