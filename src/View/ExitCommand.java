package View;

public class ExitCommand extends Command{

    public ExitCommand(String key, String description){
        //  Passes the key and description to the parent class
        super(key, description);
    }

    @Override
    public void execute() {
        /*  Executes an exit command (stops the execution of the program)
                Throws: None
                Return: None
        */
        System.exit(0);
    }
}
