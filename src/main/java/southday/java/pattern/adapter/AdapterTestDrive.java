package southday.java.pattern.adapter;

public class AdapterTestDrive {
    public static void main(String[] args) {
        Dog[] dogs = new Dog[2];
        dogs[0] = new DogImpl();
        dogs[1] = new DogAdapter(new CatImpl());
        
        for (Dog d : dogs) {
            d.wangwang();
            d.run();
        }
    }
}
