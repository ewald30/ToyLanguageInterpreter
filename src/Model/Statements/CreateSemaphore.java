package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Exceptions.VariableNotDefinedException;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.TypeInterface;
import Model.Values.IntValue;
import Model.Values.ValueInterface;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphore implements StatementInterface{
    String variable;
    ExpressionInterface expression;
    static Lock lock = new ReentrantLock();

    public CreateSemaphore(String variable, ExpressionInterface expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public ExpressionInterface getExpression() {
        return expression;
    }

    public void setExpression(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();
        var execution_stack = state.getExecutionStack();
        var semaphoreTable = state.getSemaphoreTable();

        ValueInterface expression_value = expression.evaluate(symbolTable, heap);

        if (!expression_value.getType().equals(new IntType())) {
            lock.unlock();
            throw new InvalidTypeException("Exception when creating a new semaphore. Expression should be of type Integer!");
        }

        if (!symbolTable.isDefined(variable)) {
            lock.unlock();
            throw new VariableNotDefinedException("Exception when creating semaphore. The variable is not defined!");
        }

        if (symbolTable.lookup(variable).getType().equals(new IntType())){
            Integer location = state.getLocation();
            var int_value = (IntValue) expression_value;
            semaphoreTable.add(location, new Pair<Integer, List<Integer>>(int_value.getValue(), new ArrayList<>()));
            symbolTable.update(variable, new IntValue(location));
            //symbolTable.add(variable, new IntValue(location));
            state.setSemaphoreTable(semaphoreTable);
            state.setSymbolTable(symbolTable);
        }
        else {
            lock.unlock();
            throw new InvalidTypeException("Exception when creating semaphore. Variable should be of type IntValue!");
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
        return new CreateSemaphore(variable, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "createSemaphore(" + variable + "," + expression.toString() + ")";
    }
}
