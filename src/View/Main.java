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
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.ValueInterface;
import Repository.Repository;

import java.util.Dictionary;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static Repository repo = new Repository();
    static Controller controller = new Controller(repo);

    public static void main(String[] args) throws ListException, StatementException, EvaluationException, StackException, DictionaryException {
        Scanner input = new Scanner(System.in);

        ArrayList <StatementInterface> programList = new ArrayList<StatementInterface>(10);                 //   List containing all the programs
        ADTListInterface <ValueInterface> output = new ADTList<ValueInterface>();                                       //  List containing the output
        ADTDicionaryInterface <String , ValueInterface> symbolTable = new ADTDictionary <String, ValueInterface>();     //  Dictionary containing the symbol table
        ADTStackInterface <StatementInterface> executionStack = new ADTStack <StatementInterface>();                    //  Stack containing the execution stack




        //  First program
        StatementInterface firstProgram = new CompoundStatement(
                                            new VariableDeclarationStatement("v", new IntType()),
                                            new CompoundStatement(
                                                    new AssignStatement("v", new ValueExpression( new IntValue(2))),
                                                    new PrintStatement(new VariableExpression("v"))));


        //  Second program
        StatementInterface secondProgram = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression( new IntValue(2)),new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)),new ValueExpression(new IntValue(5)),'*'),'+')),
                                new CompoundStatement(new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"),
                                        new ValueExpression(new IntValue(1)),'+')),new PrintStatement(new VariableExpression("b"))))));


        //  Third program
        StatementInterface thirdProgram = new CompoundStatement( new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",
                new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                new PrintStatement(new VariableExpression("v"))))));




        StatementInterface fourthProgramError = new CompoundStatement( new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",
                                        new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));



        programList.add(firstProgram);
        programList.add(secondProgram);
        programList.add(thirdProgram);
        programList.add(fourthProgramError);    //  Variable a is declared twice


        //  Get the choice of the user and execute the coresponding program
        System.out.println("  Enter the number of the program that you would like to execute (Choose from 0 to 3): ");
        int choice = input.nextInt();
        try {
            ProgramState programState = new ProgramState(executionStack, symbolTable, programList.get(choice), output);
            programList.get(choice).execute(programState);


            controller.addProgram(programState);
            controller.allStepsExecution();
            System.out.println(controller.getAllSteps());
            System.out.println(programList.get(choice).toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            }
    }

}
