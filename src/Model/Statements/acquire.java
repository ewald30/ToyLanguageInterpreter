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

public class acquire implements StatementInterface{
    String variable;
    static Lock lock = new ReentrantLock();

    public acquire(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public StatementInterface deepCopy() {
        return new acquire(variable);
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();
        var semaphoreTable = state.getSemaphoreTable();

        if (!symbolTable.isDefined(variable)){
            lock.unlock();
            throw new VariableNotDefinedException("Variable " + variable + " is not defined!");
        }

        if (!symbolTable.lookup(variable).getType().equals(new IntType())){
            lock.unlock();
            throw new InvalidTypeException("Variable " + variable + " should be of type IntType");
        }

        var variable_value = (IntValue) symbolTable.lookup(variable);
        Integer foundIndex = variable_value.getValue();

        Integer N1 = semaphoreTable.lookup(foundIndex).getKey();
        List<Integer> threadList = semaphoreTable.lookup(foundIndex).getValue();
        Integer NL = threadList.size();

        if (N1 > NL){
            if( !threadList.contains(state.getId())){
                threadList.add(state.getId());
                semaphoreTable.update(foundIndex, new Pair<>(N1, threadList));
            }
        }
        else
        {

            var execution_stack = state.getExecutionStack();
            execution_stack.push(this);
            state.setExecutionStack(execution_stack);
        }

        lock.unlock();
        return null;

    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "acquire(" + variable + ")";
    }
}
