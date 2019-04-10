package southday.java.thinkinginjava.c21.s09;

import com.github.southday.mindview.util.CountingIntegerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class SynchronizedArrayListTester extends ListTester {
    SynchronizedArrayListTester(int nReaders, int nWriters) {
        super("Synched ArrayList", nReaders, nWriters);
    }
    @Override
    List<Integer> containerInitializer() {
        return Collections.synchronizedList(new ArrayList<>(new CountingIntegerList(containerSize)));
    }
}

class CopyOnWriterArrayListTester extends ListTester {
    CopyOnWriterArrayListTester(int nReaders, int nWriters) {
        super("CopyOnWriteArrayList", nReaders, nWriters);
    }
    @Override
    List<Integer> containerInitializer() {
        return new CopyOnWriteArrayList<>(new CountingIntegerList(containerSize));
    }
}

/**
 * P757 ListComparisons
 * @author southday
 * @date 2019/4/10
 */
public class ListComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);
        new SynchronizedArrayListTester(10, 0);
        new SynchronizedArrayListTester(9, 1);
        new SynchronizedArrayListTester(5, 5);
        new CopyOnWriterArrayListTester(10, 0);
        new CopyOnWriterArrayListTester(9, 1);
        new CopyOnWriterArrayListTester(5, 5);
        Tester.exec.shutdown();
    }
}

/* 输出，每种类型随机抽取一条数据
Type                             Read time     Write time
Synched ArrayList 10r 0w        4150898261              0
CopyOnWriteArrayList 10r 0w      127705134              0

Synched ArrayList 9r 1w         3519635023      415430430
readTime + writeTime =          3935065453
CopyOnWriteArrayList 9r 1w        75205528        6426610
readTime + writeTime =            81632138

Synched ArrayList 5r 5w         1954409465     1851752540
readTime + writeTime =          3806162005
CopyOnWriteArrayList 5r 5w        34726597      956454909
readTime + writeTime =           991181506
 */

/* 原始输出
Type                             Read time     Write time
Synched ArrayList 10r 0w        4150898261              0
Synched ArrayList 10r 0w        4138929365              0
Synched ArrayList 10r 0w        4270352329              0
Synched ArrayList 10r 0w        4010066401              0
Synched ArrayList 10r 0w        4046166716              0
Synched ArrayList 10r 0w        3770190785              0
Synched ArrayList 10r 0w        3702896808              0
Synched ArrayList 10r 0w        3804571670              0
Synched ArrayList 10r 0w        3783791946              0
Synched ArrayList 10r 0w        3770348687              0
Synched ArrayList 9r 1w         3589305930      402608570
readTime + writeTime =          3991914500
Synched ArrayList 9r 1w         3346827285      300240100
readTime + writeTime =          3647067385
Synched ArrayList 9r 1w         3519635023      415430430
readTime + writeTime =          3935065453
Synched ArrayList 9r 1w         3449778861      382898790
readTime + writeTime =          3832677651
Synched ArrayList 9r 1w         3499758587      336915868
readTime + writeTime =          3836674455
Synched ArrayList 9r 1w         3495177947      369707518
readTime + writeTime =          3864885465
Synched ArrayList 9r 1w         3584964874      399102251
readTime + writeTime =          3984067125
Synched ArrayList 9r 1w         3564109663      379147048
readTime + writeTime =          3943256711
Synched ArrayList 9r 1w         3521425924      358389566
readTime + writeTime =          3879815490
Synched ArrayList 9r 1w         3408836070      300326892
readTime + writeTime =          3709162962
Synched ArrayList 5r 5w         1993102234     1934006079
readTime + writeTime =          3927108313
Synched ArrayList 5r 5w         1889621515     1858093454
readTime + writeTime =          3747714969
Synched ArrayList 5r 5w         2092589797     1938266904
readTime + writeTime =          4030856701
Synched ArrayList 5r 5w         1951934804     1953369421
readTime + writeTime =          3905304225
Synched ArrayList 5r 5w         1896728602     1832107670
readTime + writeTime =          3728836272
Synched ArrayList 5r 5w         1954409465     1851752540
readTime + writeTime =          3806162005
Synched ArrayList 5r 5w         1983541997     1720200848
readTime + writeTime =          3703742845
Synched ArrayList 5r 5w         2146559762     1851592083
readTime + writeTime =          3998151845
Synched ArrayList 5r 5w         2000540078     1639673660
readTime + writeTime =          3640213738
Synched ArrayList 5r 5w         2066534364     1666529916
readTime + writeTime =          3733064280
CopyOnWriteArrayList 10r 0w      140667028              0
CopyOnWriteArrayList 10r 0w       82189357              0
CopyOnWriteArrayList 10r 0w      102082568              0
CopyOnWriteArrayList 10r 0w       85671245              0
CopyOnWriteArrayList 10r 0w       74832106              0
CopyOnWriteArrayList 10r 0w      111539236              0
CopyOnWriteArrayList 10r 0w      127705134              0
CopyOnWriteArrayList 10r 0w       88802680              0
CopyOnWriteArrayList 10r 0w      102447603              0
CopyOnWriteArrayList 10r 0w       95097644              0
CopyOnWriteArrayList 9r 1w        97272546       19536196
readTime + writeTime =           116808742
CopyOnWriteArrayList 9r 1w        74639559        8924247
readTime + writeTime =            83563806
CopyOnWriteArrayList 9r 1w        57739576        9608007
readTime + writeTime =            67347583
CopyOnWriteArrayList 9r 1w        81951592        6587795
readTime + writeTime =            88539387
CopyOnWriteArrayList 9r 1w        76675886       11547696
readTime + writeTime =            88223582
CopyOnWriteArrayList 9r 1w        85823676        6463077
readTime + writeTime =            92286753
CopyOnWriteArrayList 9r 1w        60427937        8060705
readTime + writeTime =            68488642
CopyOnWriteArrayList 9r 1w        75205528        6426610
readTime + writeTime =            81632138
CopyOnWriteArrayList 9r 1w        73126169        5406623
readTime + writeTime =            78532792
CopyOnWriteArrayList 9r 1w        81645997        6029483
readTime + writeTime =            87675480
CopyOnWriteArrayList 5r 5w        36191848      946909260
readTime + writeTime =           983101108
CopyOnWriteArrayList 5r 5w        32780345      952545991
readTime + writeTime =           985326336
CopyOnWriteArrayList 5r 5w        32275638      933778891
readTime + writeTime =           966054529
CopyOnWriteArrayList 5r 5w        33097612      901448186
readTime + writeTime =           934545798
CopyOnWriteArrayList 5r 5w        36083907      891252328
readTime + writeTime =           927336235
CopyOnWriteArrayList 5r 5w        34728057      971516219
readTime + writeTime =          1006244276
CopyOnWriteArrayList 5r 5w        34726597      956454909
readTime + writeTime =           991181506
CopyOnWriteArrayList 5r 5w        34807921      947967174
readTime + writeTime =           982775095
CopyOnWriteArrayList 5r 5w        34646005      958176525
readTime + writeTime =           992822530
CopyOnWriteArrayList 5r 5w        33885666      937062760
readTime + writeTime =           970948426
 */
