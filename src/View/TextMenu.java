package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commandMap;

    public TextMenu(){
        //  Constructor of Text Menu class
        this.commandMap = new HashMap<>();
    }

    public void addCommand(Command command){
        //  Adds a new command
        commandMap.put(command.getKey(), command);
    }

    private void printMenu() {
        //  Prints the menu consisting of the currently stored commands
        for (Command com : commandMap.values()) {
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        /*  Runs the text menu
                Shows all the commands and gets the user's choice
                Throws: None
                Return: None
        */
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commandMap.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
            break;
        }
    }
}
