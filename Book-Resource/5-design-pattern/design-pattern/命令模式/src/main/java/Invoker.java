public class Invoker {//发出这个命令
    private Command command;

    public void SetCommand(Command command){
        this.command=command;
    }

    public void ExecuteCommand(){
        command.Execute();;
    }
}
