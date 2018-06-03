package southday.java.pattern.adapter;

public class DogAdapter implements Dog {
    private Cat cat;
    
    public DogAdapter(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void wangwang() {
        // TODO Auto-generated method stub
        cat.miaomiao();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // 小狗跑一次相当于小猫跑5次
        for (int i = 0; i < 5; i++) {
            cat.run();
        }
    }

}
