package southday.java.thinkinginjava.c14.s04.typeinfo;

public class CabinAirFilter extends Filter {
    public static class Factory implements southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory<CabinAirFilter> {
        @Override
        public CabinAirFilter create() {
            return new CabinAirFilter();
        }
    }
}
