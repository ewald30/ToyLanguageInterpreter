package View;

import Controller.Controller;
import Model.Exceptions.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            Scanner scannerObj = new Scanner(System.in);
            System.out.println("Enter the path of the log file or hit enter to skip.\n");
            String logFilePath = scannerObj.nextLine();

            if (!logFilePath.equals("")) {
                controller.SetRepositoryFile(logFilePath);
            }
            ExecutorService executor = Executors.newFixedThreadPool(2);
            controller.setExecutor(executor);
            controller.allStepsExecution();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
