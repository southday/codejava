package southday.java.thinkinginjava.c21.s09;

import com.github.southday.mindview.util.CountingGenerator;
import com.github.southday.mindview.util.MapData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SynchronizedHashMapTester extends MapTester {
    SynchronizedHashMapTester(int nReaders, int nWriters) {
        super("Synched HashMap", nReaders, nWriters);
    }
    @Override
    Map<Integer, Integer> containerInitializer() {
        return Collections.synchronizedMap(
                new HashMap<>(
                        MapData.map(
                            new CountingGenerator.Integer(),
                            new CountingGenerator.Integer(),
                            containerSize)
                )
        );
    }
}

class ConcurrentHashMapTester extends MapTester {
    ConcurrentHashMapTester(int nReaders, int nWriters) {
        super("ConcurrentHashMap", nReaders, nWriters);
    }
    @Override
    Map<Integer, Integer> containerInitializer() {
        return new ConcurrentHashMap<>(
                MapData.map(
                        new CountingGenerator.Integer(),
                        new CountingGenerator.Integer(),
                        containerSize )
        );
    }
}

/**
 * P759
 * @author southday
 * @date 2019/4/10
 */
public class MapComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);
        new SynchronizedHashMapTester(10, 0);
        new SynchronizedHashMapTester(9, 1);
        new SynchronizedHashMapTester(5, 5);
        new ConcurrentHashMapTester(10, 0);
        new ConcurrentHashMapTester(9, 1);
        new ConcurrentHashMapTester(5, 5);
        Tester.exec.shutdown();
    }
}

/* 输出，各种类型随机抽取一条数据
Type                             Read time     Write time
Synched HashMap 10r 0w          4553634465              0
ConcurrentHashMap 10r 0w         299977900              0

Synched HashMap 9r 1w           4312268434      528394073
readTime + writeTime =          4840662507
ConcurrentHashMap 9r 1w          343553988       27048799
readTime + writeTime =           370602787

Synched HashMap 5r 5w           2085319700     2908399557
readTime + writeTime =          4993719257
ConcurrentHashMap 5r 5w          102000881      432931027
readTime + writeTime =           534931908
 */


/* 原始输出
Type                             Read time     Write time
Synched HashMap 10r 0w          6013873208              0
Synched HashMap 10r 0w          5996329580              0
Synched HashMap 10r 0w          4812863213              0
Synched HashMap 10r 0w          4286213362              0
Synched HashMap 10r 0w          4786528440              0
Synched HashMap 10r 0w          4208308171              0
Synched HashMap 10r 0w          4367436341              0
Synched HashMap 10r 0w          4553634465              0
Synched HashMap 10r 0w          4949200858              0
Synched HashMap 10r 0w          4260225762              0
Synched HashMap 9r 1w           4274839254      502560725
readTime + writeTime =          4777399979
Synched HashMap 9r 1w           3951959600      520589368
readTime + writeTime =          4472548968
Synched HashMap 9r 1w           4153813080      399298444
readTime + writeTime =          4553111524
Synched HashMap 9r 1w           4188411679      521921149
readTime + writeTime =          4710332828
Synched HashMap 9r 1w           4108489485      532802590
readTime + writeTime =          4641292075
Synched HashMap 9r 1w           4342703574      517366034
readTime + writeTime =          4860069608
Synched HashMap 9r 1w           3770054031      522187360
readTime + writeTime =          4292241391
Synched HashMap 9r 1w           4312268434      528394073
readTime + writeTime =          4840662507
Synched HashMap 9r 1w           4230436089      529348419
readTime + writeTime =          4759784508
Synched HashMap 9r 1w           3750976954      515896042
readTime + writeTime =          4266872996
Synched HashMap 5r 5w           2354773116     2924220112
readTime + writeTime =          5278993228
Synched HashMap 5r 5w           2496972129     2825476481
readTime + writeTime =          5322448610
Synched HashMap 5r 5w           2631678600     3077646641
readTime + writeTime =          5709325241
Synched HashMap 5r 5w           2053201599     2973012466
readTime + writeTime =          5026214065
Synched HashMap 5r 5w           2085319700     2908399557
readTime + writeTime =          4993719257
Synched HashMap 5r 5w           1895316592     2894660549
readTime + writeTime =          4789977141
Synched HashMap 5r 5w           2826895782     3068398931
readTime + writeTime =          5895294713
Synched HashMap 5r 5w           2357533316     3021540066
readTime + writeTime =          5379073382
Synched HashMap 5r 5w           2322757853     3005478465
readTime + writeTime =          5328236318
Synched HashMap 5r 5w           2603523387     3063084571
readTime + writeTime =          5666607958
ConcurrentHashMap 10r 0w         236345958              0
ConcurrentHashMap 10r 0w         201028595              0
ConcurrentHashMap 10r 0w         259086893              0
ConcurrentHashMap 10r 0w         345291285              0
ConcurrentHashMap 10r 0w         280266298              0
ConcurrentHashMap 10r 0w         177627607              0
ConcurrentHashMap 10r 0w         262369300              0
ConcurrentHashMap 10r 0w         299977900              0
ConcurrentHashMap 10r 0w         224563051              0
ConcurrentHashMap 10r 0w         457312617              0
ConcurrentHashMap 9r 1w          162167347       46427457
readTime + writeTime =           208594804
ConcurrentHashMap 9r 1w          173601996       34737538
readTime + writeTime =           208339534
ConcurrentHashMap 9r 1w          171617815       38089601
readTime + writeTime =           209707416
ConcurrentHashMap 9r 1w          265863587       41013175
readTime + writeTime =           306876762
ConcurrentHashMap 9r 1w          247656620       39569804
readTime + writeTime =           287226424
ConcurrentHashMap 9r 1w          192673232       38479800
readTime + writeTime =           231153032
ConcurrentHashMap 9r 1w          343553988       27048799
readTime + writeTime =           370602787
ConcurrentHashMap 9r 1w          201331997       27654883
readTime + writeTime =           228986880
ConcurrentHashMap 9r 1w          246999846       36204978
readTime + writeTime =           283204824
ConcurrentHashMap 9r 1w          264330871       34901276
readTime + writeTime =           299232147
ConcurrentHashMap 5r 5w          122077155      457566794
readTime + writeTime =           579643949
ConcurrentHashMap 5r 5w          106173457      430731692
readTime + writeTime =           536905149
ConcurrentHashMap 5r 5w           90926164      399389976
readTime + writeTime =           490316140
ConcurrentHashMap 5r 5w           97901606      410025631
readTime + writeTime =           507927237
ConcurrentHashMap 5r 5w           83295408      402628993
readTime + writeTime =           485924401
ConcurrentHashMap 5r 5w          102000881      432931027
readTime + writeTime =           534931908
ConcurrentHashMap 5r 5w           83420125      388392934
readTime + writeTime =           471813059
ConcurrentHashMap 5r 5w           82094544      396212591
readTime + writeTime =           478307135
ConcurrentHashMap 5r 5w           76136172      412708155
readTime + writeTime =           488844327
ConcurrentHashMap 5r 5w          107189431      411306357
readTime + writeTime =           518495788
 */