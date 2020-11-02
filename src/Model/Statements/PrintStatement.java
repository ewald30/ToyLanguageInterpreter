package Model.Statements;

import Model.ADTs.ADTListInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StatementException;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Values.ValueInterface;

public class PrintStatement implements StatementInterface {
    ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression){
        //  Creates a new print statement
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*  Builds the output of the program
                Throws: None
                Return: state of the program after output has been updated
        */
        ADTListInterface <ValueInterface> output = state.getOutput();
        ValueInterface valueExpr = this.expression.evaluate(state.getSymbolTable());
        output.add(valueExpr);

        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        //  Creates a deepcopy of the print statement
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        //  Returns a string representation of the print statement
        return "print (" + this.expression.toString() + ")";
    }
}
