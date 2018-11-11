package southday.java.pattern.facade;

public class FacadeTestDrive {
    public static void main(String[] args) {
        HomeTheaterFacade theater = new HomeTheaterFacade(new PopcornPopper(), new DvdPlayer());
        theater.watchMovie();
        System.out.println("-----------------");
        theater.endMovie();
    }
}
