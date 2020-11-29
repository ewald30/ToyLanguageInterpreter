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
import java.util.ArrayList;

public class Repository implements RepositoryInterface {
    ArrayList<ProgramState> programStates;
    String logFilePath;

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<ProgramState>();
    }

    public String getLogFilePath() {
        //  Getter for log file path
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        //  Setter for log file path
        this.logFilePath = logFilePath;
    }

    public ArrayList<ProgramState> getProgramStates() {
        //  Returns the program state list
        return programStates;
    }

    public void setProgramStates(ArrayList<ProgramState> programStates) {
        //  Sets the program state list
        this.programStates = programStates;
    }

//    @Override
//    public ProgramState getCurrentProgramState() throws ListException {
//        //  Returns the current program state
//        return this.programStates.pop();
//    }

    @Override
    public void logProgramState(ProgramState programState) throws FileException{
        /*  Logs the state of a given program to a log file
                Throws: -   FileException if when writing into a file an error occurred
                Return: None
        */

        try{
            var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
        } catch (IOException exception){
            throw new FileException("Logging program state returned an error: " + exception.getMessage());
        }
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        //  Adds a new program state to the list of program states
        this.programStates.add(newProgramState);
    }
}
