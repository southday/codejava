package southday.java.pattern.command.receiver.cmdobj;

import southday.java.pattern.command.receiver.TV;

public class TVOffCommand implements Command {
    private TV tv;
    
    public TVOffCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        tv.off();
    }

}
