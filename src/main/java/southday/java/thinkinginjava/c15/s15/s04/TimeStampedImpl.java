package southday.java.thinkinginjava.c15.s15.s04;

import java.util.Date;

public class TimeStampedImpl implements TimeStamped {
    private final long timeStamp;
    
    public TimeStampedImpl() {
        timeStamp = new Date().getTime();
    }
    
    @Override
    public long getStamp() {
        return timeStamp;
    }

}
