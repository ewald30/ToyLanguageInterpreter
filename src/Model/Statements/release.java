package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Exceptions.VariableNotDefinedException;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.TypeInterface;
import Model.Values.IntValue;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class release implements StatementInterface{
    String variable;
    Lock lock = new ReentrantLock();

    public release(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();
        var semaphoreTable = state.getSemaphoreTable();

        if (!symbolTable.isDefined(variable)){
            lock.unlock();
            throw new VariableNotDefinedException("Variable " + variable + "is not defined!");
        }

        if (!symbolTable.lookup(variable).getType().equals(new IntType())){
            lock.unlock();
            throw new InvalidTypeException("Variable should be of type IntType!");
        }

        var variable_value = (IntValue)symbolTable.lookup(variable);
        Integer foundIndex = variable_value.getValue();

        Integer N1 = semaphoreTable.lookup(foundIndex).getKey();
        List<Integer> threadList = semaphoreTable.lookup(foundIndex).getValue();
        Integer NL = threadList.size();

        if (threadList.contains(state.getId())){
            threadList.remove(state.getId());
            state.getSemaphoreTable().add(foundIndex, new Pair<>(N1, threadList));
        }

        lock.unlock();
        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public StatementInterface deepCopy() {
        return new release(variable);
    }

    @Override
    public String toString(){
        return "release(" + variable + ")";
    }




}
