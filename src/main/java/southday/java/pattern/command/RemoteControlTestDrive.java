package southday.java.pattern.command;

import southday.java.pattern.command.receiver.Light;
import southday.java.pattern.command.receiver.TV;
import southday.java.pattern.command.receiver.cmdobj.Command;
import southday.java.pattern.command.receiver.cmdobj.LightOffCommand;
import southday.java.pattern.command.receiver.cmdobj.LightOnCommand;
import southday.java.pattern.command.receiver.cmdobj.TVOffCommand;
import southday.java.pattern.command.receiver.cmdobj.TVOnCommand;

public class RemoteControlTestDrive {
    public static void main(String[] args) {
        RemoteControl rc = new RemoteControl(2);
        Light light = new Light();
        TV tv = new TV();
        
        Command lightOnCmd = new LightOnCommand(light);
        Command lightOffCmd = new LightOffCommand(light);
        Command tvOnCmd = new TVOnCommand(tv);
        Command tvOffCmd = new TVOffCommand(tv);
        
        rc.setCommand(0, lightOnCmd, lightOffCmd);
        rc.setCommand(1, tvOnCmd, tvOffCmd);
        
        rc.on(0); // 电灯开
        rc.off(0); // 电灯关
        rc.on(1); // 电视开
        rc.off(1); // 电视关
    }

}
