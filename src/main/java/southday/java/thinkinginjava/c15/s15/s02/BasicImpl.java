package southday.java.thinkinginjava.c15.s15.s02;

public class BasicImpl implements Basic {
    private String value;
    
    @Override
    public void set(String val) {
        value = val;
    }

    @Override
    public String get() {
        return value;
    }

}
