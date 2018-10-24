package southday.java.thinkinginjava.c14.s04.typeinfo;

public class FanBelt extends Belt {
    public static class Factory implements southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory<FanBelt> {
        @Override
        public FanBelt create() {
            return new FanBelt();
        }
    }
}
