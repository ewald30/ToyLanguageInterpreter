package View;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.*;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.ValueInterface;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.*;

public class Interpreter {
    static Repository repo = new Repository();
    static Controller controller = new Controller(repo);

    private static StatementInterface connectStatements(StatementInterface[] staements){
        if(staements.length == 1)
            return staements[0];
        return Arrays.stream(staements).skip(2).reduce(new CompoundStatement(staements[0], staements[1]), CompoundStatement::new, CompoundStatement::new);
    }

    public static void main(String[] args) throws ListException, StatementException, EvaluationException, StackException, DictionaryException {

        ArrayList <StatementInterface> programList = new ArrayList<StatementInterface>(10);                     //   List containing all the programs
        ADTListInterface <ValueInterface> output = new ADTList<ValueInterface>();                                           //  List containing the output
        ADTDicionaryInterface <String , ValueInterface> symbolTable = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
        ADTDicionaryInterface <StringValue, BufferedReader> fileTable = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
        ADTStackInterface <StatementInterface> executionStack = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));





        //  First program example
        var example1 = connectStatements(new StatementInterface[]{
                new VariableDeclarationStatement("v", new IntType()),   //  Declare v
                new AssignStatement("v", new ValueExpression(new IntValue(2))), //  Assign to v value 2
                new PrintStatement(new VariableExpression("v"))     //  Print v (2)
        });
        var ProgramState1 = new ProgramState(executionStack, symbolTable, example1, output, fileTable);
        var repository1 = new Repository("logFile1.txt");
        var controller1 = new Controller(repository1);
        //example1.execute(ProgramState1);
        //executionStack.push(example1);
        controller1.addProgram(ProgramState1);
        textMenu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));





        //Second program example
        var example2 = connectStatements(new StatementInterface[]{
                new VariableDeclarationStatement("a", new BoolType()),  //  Declare a
                new VariableDeclarationStatement("v", new IntType()),   //  Declare b
                new IfStatement(new VariableExpression("a"), // Condition
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),     //  Then
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),    //  Else
                new PrintStatement(new VariableExpression("v")) //  v should be 3
        });
        var ProgramState2 = new ProgramState(executionStack, symbolTable, example2, output, fileTable);
        var repository2 = new Repository("logFile2.txt");
        var controller2 = new Controller(repository2);
        controller2.addProgram(ProgramState2);
        textMenu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));





        //Third program example     Error is raised because of invalid type
        var example3 = connectStatements(new StatementInterface[]{
                new VariableDeclarationStatement("a", new BoolType()),  //  Declare a
                new VariableDeclarationStatement("v", new IntType()),   //  Declare b
                new IfStatement(new VariableExpression("a"), // Condition
                        new AssignStatement("v", new ValueExpression(new BoolValue(true))),     //  Raise Error
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),        //  Else
                new PrintStatement(new VariableExpression("v")) //  v should be 3
        });
        var ProgramState3 = new ProgramState(executionStack, symbolTable, example3, output, fileTable);
        var repository3 = new Repository("logFile3.txt");
        var controller3 = new Controller(repository3);
        controller3.addProgram(ProgramState3);
        textMenu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));





        //  Fourth program example using files
        var example4 = connectStatements(new StatementInterface[]{
                new VariableDeclarationStatement("varf", new StringType()),              //  Declare variable to hold the file name
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),   //  Assign it the file name
                new OpenRFileStatement(new VariableExpression("varf")),                  //  Open the file
                new VariableDeclarationStatement("varc", new IntType()),                 //  Declare variable to hold the integers
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the first integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the first integer
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the second integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the second integer
                new CloseRFileStatement(new VariableExpression("varf"))                  //  Close the file
        });
        var ProgramState4 = new ProgramState(executionStack, symbolTable, example4, output, fileTable);
        var repository4 = new Repository("logFile4.txt");
        var controller4 = new Controller(repository4);
        controller4.addProgram(ProgramState4);
        textMenu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));





        //  Fifth program example using files   Error is raised because of an empty file
        var example5 = connectStatements(new StatementInterface[]{
                new VariableDeclarationStatement("varf", new StringType()),              //  Declare variable to hold the file name
                new AssignStatement("varf", new ValueExpression(new StringValue("testEmpty.in"))),   //  Assign it the file name
                new OpenRFileStatement(new VariableExpression("varf")),                  //  Open the file
                new VariableDeclarationStatement("varc", new IntType()),                 //  Declare variable to hold the integers
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the first integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the first integer
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the second integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the second integer
                new CloseRFileStatement(new VariableExpression("varf"))                  //  Close the file
        });
        var ProgramState5 = new ProgramState(executionStack, symbolTable, example5, output, fileTable);
        var repository5 = new Repository("logFile5.txt");
        var controller5 = new Controller(repository5);
        controller5.addProgram(ProgramState5);
        textMenu.addCommand(new RunExampleCommand("5", example5.toString() + "EMPTY FILE", controller5));
        textMenu.show();
    }
}
