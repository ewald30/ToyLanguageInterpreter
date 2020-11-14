package View;

import Controller.Controller;
import Model.Exceptions.*;

public class RunExampleCommand extends Command{
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller){
        //  Passes the key and description to the parent class and sets the controller
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        /*  Runs a program example
                Throws: None
                Return: None
        */
        try{
            controller.allStepsExecution();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
