package southday.java.pattern.command.receiver.cmdobj;

import southday.java.pattern.command.receiver.Light;

public class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        light.on();
    }

}
