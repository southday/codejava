package southday.java.pattern.command;

import southday.java.pattern.command.receiver.cmdobj.Command;
import southday.java.pattern.command.receiver.cmdobj.NoCommand;

/**
 * 遥控器
 * @author southday
 * @date 2018年6月3日
 */
public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    
    public RemoteControl(int soltNum) {
        onCommands = new Command[soltNum];
        offCommands = new Command[soltNum];
        Command noCmd = new NoCommand();
        for (int i = 0; i < soltNum; i++) {
            onCommands[i] = noCmd;
            offCommands[i] = noCmd;
        }
    }
    
    public void setCommand(int soltNo, Command onCmd, Command offCmd) {
        onCommands[soltNo] = onCmd;
        offCommands[soltNo] = offCmd;
    }
    
    public void on(int soltNo) {
        onCommands[soltNo].execute();
    }
    
    public void off(int soltNo) {
        offCommands[soltNo].execute();
    }
}
