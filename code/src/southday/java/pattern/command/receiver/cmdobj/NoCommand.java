package southday.java.pattern.command.receiver.cmdobj;

/**
 * 空命令对象
 * @author southday
 * @date 2018年6月3日
 */
public class NoCommand implements Command {

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }
    
    /* 如果没有空命令对象，那么在RemoteControl.java的代码中就需要这样写：
     * if (OnCommand[slot] != null) {
     *     onCommand[slot].execute();
     * }
     * 
     * 当有空命令对象时，可以直接写：onCommand[slot].execute();
     * 当你不想返回一个有意义的对象时，空对象就很有用。客户可以将处理null的责任转移给空对象。
     */

}
