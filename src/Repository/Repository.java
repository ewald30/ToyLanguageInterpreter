package Repository;

import Model.ADTs.ADTList;
import Model.ADTs.ADTListInterface;
import Model.Exceptions.ListException;
import Model.ProgramState;

public class Repository implements RepositoryInterface {
    ADTListInterface<ProgramState> programStates;

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
    public void addProgramState(ProgramState newProgramState) {
        //  Adds a new program state to the list of program states
        this.programStates.add(newProgramState);
    }
}
