package southday.java.pattern.command.receiver.cmdobj;

import southday.java.pattern.command.receiver.Light;

public class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        light.off();
    }

}
