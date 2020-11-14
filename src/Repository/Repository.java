package Repository;

import Model.ADTs.ADTList;
import Model.ADTs.ADTListInterface;
import Model.Exceptions.FileException;
import Model.Exceptions.ListException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements RepositoryInterface {
    ADTListInterface<ProgramState> programStates;
    String logFilePath;

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
        this.programStates = new ADTList<ProgramState>();
    }

    public String getLogFilePath() {
        //  Getter for log file path
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        //  Setter for log file path
        this.logFilePath = logFilePath;
    }

    public Repository() {
        //  Constructor of the repository
        this.programStates = new ADTList<ProgramState>();
    }

    @Override
    public ProgramState getCurrentProgramState() throws ListException {
        //  Returns the current program state
        return this.programStates.pop();
    }

    @Override
    public void logProgramState() throws FileException, ListException{
        /*  Logs a state of the program to a log file
                Throws: -   FileException if when writing into a file an error occurred
                        -   ListException if there were no programs loaded yet
                Return: None
        */
        try {
            try (var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))){
                logFile.println(programStates.pop().toString());
                }
                catch (ListException exception){
                    throw new ListException("No programs loaded yet!");
                }
                }
                catch (IOException expcetion){
                throw new FileException("Logging program state returned an error: " + expcetion.getMessage());
                }
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        //  Adds a new program state to the list of program states
        this.programStates.add(newProgramState);
    }
}
