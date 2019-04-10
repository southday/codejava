package southday.java.thinkinginjava.c21.s09;

import java.util.List;

/**
 * P757 ListTester
 * @author southday
 * @date 2019/4/10
 */
public abstract class ListTester extends Tester<List<Integer>> {
    ListTester(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {
        long result = 0;
        @Override
        void test() {
            for (long i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    result += testContanier.get(index);
        }
        @Override
        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        @Override
        void test() {
            for (long i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    testContanier.set(index, writeData[index]);
        }
        @Override
        void putResults() {
            writeTime += duration;
        }
    }

    @Override
    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++)
            exec.execute(new Reader());
        for (int i = 0; i < nWriters; i++)
            exec.execute(new Writer());
    }
}
