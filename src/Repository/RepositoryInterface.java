package Repository;

import Model.Exceptions.FileException;
import Model.Exceptions.ListException;
import Model.ProgramState;



public interface RepositoryInterface {
    ProgramState getCurrentProgramState() throws ListException;
    void logProgramState() throws FileException, ListException;
    void addProgramState(ProgramState newProgramState);
}
