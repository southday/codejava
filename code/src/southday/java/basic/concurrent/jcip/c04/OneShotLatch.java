package southday.java.basic.concurrent.jcip.c04;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 程序清单 14-14 使用<code>AbstractQueuedSynchronizer</code>实现的二元闭锁<p>
 * 关闭（0），打开（1），将功能委托给私有AQS子类
 * @author southday
 * @date 2018年5月1日
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }
    
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }
    
    // 此外还可通过Sync继续扩展如：限时请求、检查闭锁状态等方法
    
    private class Sync extends AbstractQueuedSynchronizer {
        // 参数 ignored 是方便开发者扩展功能而添加的
        protected int tryAcquireShared(int ignored) {
            // 如果闭锁是开的（state == 1），那么这个操作将成功，否则将失败
            return (getState() == 1) ? 1 : -1;
        }
        
        protected boolean tryReleaseShared(int ignored) {
            setState(1); // 现在打开闭锁
            return true;
        }
    }
}
