package Model.DesignPattern;


import Model.ProgramState;

import java.util.ArrayList;

public interface Subject {
    public void register(MyObserver newObserver);
    public void unregister(MyObserver observer);
    public void notifyObservers(ArrayList<ProgramState> currentProgramStates);
}
