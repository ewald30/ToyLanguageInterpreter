package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.TypeInterface;

public class SleepStatement implements StatementInterface {
    Integer number;

    public SleepStatement(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return "sleep(" + number + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (number > 0)
            state.getExecutionStack().push(new SleepStatement(number-1));

        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public StatementInterface deepCopy() {
        return new SleepStatement(number);
    }
}
