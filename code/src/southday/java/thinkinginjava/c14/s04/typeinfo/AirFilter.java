package southday.java.thinkinginjava.c14.s04.typeinfo;

/**
 * 
 * @author southday
 * @date 2018年10月24日
 */
public class AirFilter extends Filter {
    public static class Factory implements southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory<AirFilter> {
        @Override
        public AirFilter create() {
            return new AirFilter();
        }
    }
}
