package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTListInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.TypeInterface;
import Model.Values.ValueInterface;

public class PrintStatement implements StatementInterface {
    ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression){
        //  Creates a new print statement
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        /*  Builds the output of the program
                Throws: None
                Return: state of the program after output has been updated
        */
        ADTDicionaryInterface<Integer, ValueInterface> heap = state.getHeap();
        ADTListInterface <ValueInterface> output = state.getOutput();
        ValueInterface valueExpr = this.expression.evaluate(state.getSymbolTable(), heap);
        output.add(valueExpr);

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        //  Creates a deepcopy of the print statement
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Returns the updated type table after the type check of the expression
        expression.TypeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        //  Returns a string representation of the print statement
        return "print (" + this.expression.toString() + ")";
    }
}
