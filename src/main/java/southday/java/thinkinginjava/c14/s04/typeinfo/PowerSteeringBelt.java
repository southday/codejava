package southday.java.thinkinginjava.c14.s04.typeinfo;

public class PowerSteeringBelt extends Belt {
    public static class Factory implements southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory<PowerSteeringBelt> {
        @Override
        public PowerSteeringBelt create() {
            return new PowerSteeringBelt();
        }
    }
}
