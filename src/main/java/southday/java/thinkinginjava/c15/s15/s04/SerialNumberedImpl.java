package southday.java.thinkinginjava.c15.s15.s04;

public class SerialNumberedImpl implements SerialNumbered {
    private static long counter = 1;
    private final long serialNumber = counter++;

    @Override
    public long getSerialNumber() {
        return serialNumber;
    }

}
