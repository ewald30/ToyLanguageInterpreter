package Model;

import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramCreator {
    private final ArrayList<StatementInterface> program_list;
    private final ArrayList<String> program_description_list;

    public ProgramCreator() {
        //  Creates a new program list
        this.program_list = new ArrayList<StatementInterface>();
        this.program_description_list = new ArrayList<String>();
    }

    public ArrayList<String> getProgram_description_list() {
        //  Returns the list of the descriptions of the programs
        return program_description_list;
    }

    public ArrayList<StatementInterface> getProgram_list() {
        //  Returns the list of the programs
        return program_list;
    }

    private static StatementInterface connectStatements(List<StatementInterface> staements){
        if (staements.size() == 0)
            return new NoOperationStatement();
        else if (staements.size() == 1)
            return staements.get(0);

        staements = new ArrayList<>(staements);
        Collections.reverse(staements);

        return staements.stream()
                .skip(2)
                .reduce(
                        new CompoundStatement(staements.get(1), staements.get(0)),
                        (a,b) -> new CompoundStatement(b,a)
                );
    }


    public void CreatePrograms(){
        var example1 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),   //  Declare v
                new AssignStatement("v", new ValueExpression(new IntValue(2))), //  Assign to v value 2
                new PrintStatement(new VariableExpression("v"))     //  Print v (2)
        ));
        program_description_list.add("1. Prints the value of the variable \"v\"");

        var example2 = connectStatements(List.of(
                new VariableDeclarationStatement("a", new BoolType()),  //  Declare a
                new VariableDeclarationStatement("v", new IntType()),   //  Declare b
                new IfStatement(new VariableExpression("a"), // Condition
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),     //  Then
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),    //  Else
                new PrintStatement(new VariableExpression("v")) //  v should be 3
        ));
        program_description_list.add("2. Check if a variable is true and assigns a value accordingly (2)");


        var example3 = connectStatements(List.of(
                new VariableDeclarationStatement("a", new BoolType()),  //  Declare a
                new VariableDeclarationStatement("v", new IntType()),   //  Declare b
                new IfStatement(new VariableExpression("a"), // Condition
                        new AssignStatement("v", new ValueExpression(new BoolValue(true))),     //  Raise Error
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),        //  Else
                new PrintStatement(new VariableExpression("v")) //  v should be 3
        ));
        program_description_list.add("3. Same as \"2\" but tries to assign a bool value, type checker will raise an error");



        var example4 = connectStatements(List.of(
                new VariableDeclarationStatement("varf", new StringType()),              //  Declare variable to hold the file name
                new AssignStatement("varf", new ValueExpression(new StringValue("test.txt"))),   //  Assign it the file name
                new OpenRFileStatement(new VariableExpression("varf")),                  //  Open the file
                new VariableDeclarationStatement("varc", new IntType()),                 //  Declare variable to hold the integers
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the first integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the first integer
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the second integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the second integer
                new CloseRFileStatement(new VariableExpression("varf"))                  //  Close the file
        ));
        program_description_list.add("4. Reads 2 integers from a file and prints the value to the output");



        var example5 = connectStatements(List.of(
                new VariableDeclarationStatement("varf", new StringType()),              //  Declare variable to hold the file name
                new AssignStatement("varf", new ValueExpression(new StringValue("testEmpty.in"))),   //  Assign it the file name
                new OpenRFileStatement(new VariableExpression("varf")),                  //  Open the file
                new VariableDeclarationStatement("varc", new IntType()),                 //  Declare variable to hold the integers
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the first integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the first integer
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the second integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the second integer
                new CloseRFileStatement(new VariableExpression("varf"))                  //  Close the file
        ));
        program_description_list.add("5. Tries to read values from an empty file.");



        var example6 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(4))),
                new WhileStatement(new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)), ">"),    //condition
                        connectStatements(List.of(
                                new PrintStatement(new VariableExpression("v")),    // print v
                                new AssignStatement("v",    //  V=
                                        new ArithmeticExpression
                                                (new VariableExpression("v"), new ValueExpression(new IntValue(1)),'-'))
                        ))),
                new PrintStatement(new VariableExpression("v"))
                //v-1
        ));
        program_description_list.add("6. Contains a while loop and prints the modified value of \"v\" afterwards");


        var example7 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),            //  Add a new reference pf type int
                new NewStatement("v", new ValueExpression(new IntValue(20))),                 //  Allocate a new integer of value 20 at address v
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),     // ref ref int a
                new NewStatement("a", new VariableExpression("v")),                         //  Allocate a new reference to the v reference
                new NewStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        ));
        program_description_list.add("7. Creates two reference type variables and reads from the heap");



        var example8 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),                       //  int v
                new VariableDeclarationStatement("a", new ReferenceType(new IntType())),    //  ref int a
                new AssignStatement("v", new ValueExpression(new IntValue(10))),            //  v=10
                new NewStatement("a", new ValueExpression(new IntValue(22))),         //  new(a,22)
                new ForkStatement(connectStatements(List.of(
                        new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                ))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        ));
        program_description_list.add("8. Contains a fork statement and prints some values in two different threads");




        var example9 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(0))),
                new RepeatUntilStatement(connectStatements(List.of(
                        new PrintStatement(new VariableExpression("v")),
                        new AssignStatement("v",    // v=
                                new ArithmeticExpression(   // v+1
                                        new VariableExpression("v"), new ValueExpression(new IntValue(1)),'+')))),
                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), '*'))
        ));
        program_description_list.add("9. Contains a repeat until loop");


        program_list.add(example1);
        program_list.add(example2);
        program_list.add(example3);
        program_list.add(example4);
        program_list.add(example5);
        program_list.add(example6);
        program_list.add(example7);
        program_list.add(example8);
        program_list.add(example9);

    }
}
