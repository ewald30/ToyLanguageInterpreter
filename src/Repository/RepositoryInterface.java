package Repository;

import Model.ADTs.ADTList;
import Model.ADTs.ADTListInterface;
import Model.Exceptions.FileException;
import Model.Exceptions.ListException;
import Model.ProgramState;

import java.util.ArrayList;


public interface RepositoryInterface {
    //ProgramState getCurrentProgramState() throws ListException;
    void logProgramState(ProgramState programState) throws FileException;
    void addProgramState(ProgramState newProgramState);

    ArrayList<ProgramState> getProgramStates();
    void setProgramStates(ArrayList<ProgramState> programStates);
}
