package southday.java.thinkinginjava.c14.s04.typeinfo;

public class OilFilter extends Filter {
    public static class Factory implements southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory<OilFilter> {
        @Override
        public OilFilter create() {
            return new OilFilter();
        }
    }
}
