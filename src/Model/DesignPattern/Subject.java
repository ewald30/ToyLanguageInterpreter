package Model.DesignPattern;

public interface Subject {
    public void register(Observer newObserver);
    public void unregister(Observer observer);
    public void notifyObservers();
}
