package southday.java.pattern.command.receiver.cmdobj;

import southday.java.pattern.command.receiver.TV;

public class TVOnCommand implements Command {
    private TV tv;
    
    public TVOnCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        tv.on();
    }

}
