package southday.java.pattern.facade;

public class HomeTheaterFacade {
    private PopcornPopper popper;
    private DvdPlayer dvdPlayer;
    
    public HomeTheaterFacade(PopcornPopper popper, DvdPlayer dvdPlayer) {
        this.popper = popper;
        this.dvdPlayer = dvdPlayer;
    }
    
    public void watchMovie() {
        // 实际应该有更多操作，这里为了简便，才举了关于爆米花机和DVD播放器的操作
        dvdPlayer.on();
        dvdPlayer.play();
        popper.on();
        popper.pop();
    }
    
    public void endMovie() {
        dvdPlayer.off();
        popper.off();
    }
}
