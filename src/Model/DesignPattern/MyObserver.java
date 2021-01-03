package Model.DesignPattern;

import Model.ProgramState;

import java.util.ArrayList;

public interface MyObserver {
    public void update(ArrayList<ProgramState> currentProgramStates);
}
