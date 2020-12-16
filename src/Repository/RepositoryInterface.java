package Repository;

import Model.ADTs.ADTList;
import Model.ADTs.ADTListInterface;
import Model.Exceptions.FileException;
import Model.Exceptions.ListException;
import Model.Exceptions.MyException;
import Model.ProgramState;

import java.util.ArrayList;


public interface RepositoryInterface {
    //ProgramState getCurrentProgramState() throws ListException;
    void logProgramState(ProgramState programState) throws MyException;
    void addProgramState(ProgramState newProgramState);
    void setLogFilePath(String logFilePath);
    String getLogFilePath();
    ArrayList<ProgramState> getProgramStates();
    void setProgramStates(ArrayList<ProgramState> programStates);
}
