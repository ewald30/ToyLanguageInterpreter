package Repository;

import Model.Exceptions.ListException;
import Model.ProgramState;



public interface RepositoryInterface {
    ProgramState getCurrentProgramState() throws ListException;
    void addProgramState(ProgramState newProgramState);
}
