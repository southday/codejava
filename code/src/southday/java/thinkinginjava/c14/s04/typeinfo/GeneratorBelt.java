package southday.java.thinkinginjava.c14.s04.typeinfo;

public class GeneratorBelt extends Belt {
    public static class Factory implements southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory<GeneratorBelt> {
        @Override
        public GeneratorBelt create() {
            return new GeneratorBelt();
        }
    }
}
