package southday.java.pattern.command.receiver.cmdobj;

import southday.java.pattern.command.receiver.TV;

public class TVChangeChannelCommand implements Command {
    private TV tv;
    
    public TVChangeChannelCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        tv.changeChannel();
    }

}
