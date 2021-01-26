package View;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.*;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.ValueInterface;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.*;

public class Interpreter {
    //static Repository repo = new Repository();
   // static Controller controller = new Controller(repo);

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

    public static void main(String[] args) throws MyException{
        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));


        //  First program example
        var example1 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),   //  Declare v
                new AssignStatement("v", new ValueExpression(new IntValue(2))), //  Assign to v value 2
                new PrintStatement(new VariableExpression("v"))     //  Print v (2)
        ));
        var typeEnv1 = new ADTDictionary<String, TypeInterface>();
        try {
            typeEnv1 = (ADTDictionary<String, TypeInterface>) example1.TypeCheck(typeEnv1);
            var output1 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable1 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable1 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack1 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap1 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState1 = new ProgramState(executionStack1, symbolTable1, example1, output1, fileTable1, heap1);
            var repository1 = new Repository("logFile1.txt");
            var controller1 = new Controller(repository1);
            controller1.addProgram(ProgramState1);
            textMenu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
        } catch (MyException e){
            System.out.println(e.getMessage());
        }





        //Second program example
        var example2 = connectStatements(List.of(
                new VariableDeclarationStatement("a", new BoolType()),  //  Declare a
                new VariableDeclarationStatement("v", new IntType()),   //  Declare b
                new IfStatement(new VariableExpression("a"), // Condition
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),     //  Then
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),    //  Else
                new PrintStatement(new VariableExpression("v")) //  v should be 3
        ));
        var typeEnv2 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv2 = (ADTDictionary<String, TypeInterface>)example2.TypeCheck(typeEnv2);
            var output2 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable2 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable2 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack2 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap2 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState2 = new ProgramState(executionStack2, symbolTable2, example2, output2, fileTable2, heap2);
            var repository2 = new Repository("logFile2.txt");
            var controller2 = new Controller(repository2);
            controller2.addProgram(ProgramState2);
            textMenu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
        } catch (MyException e){
            System.out.println(e.getMessage());
        }





        //Third program example     Error is raised because of invalid type
        var example3 = connectStatements(List.of(
                new VariableDeclarationStatement("a", new BoolType()),  //  Declare a
                new VariableDeclarationStatement("v", new IntType()),   //  Declare b
                new IfStatement(new VariableExpression("a"), // Condition
                        new AssignStatement("v", new ValueExpression(new BoolValue(true))),     //  Raise Error
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),        //  Else
                new PrintStatement(new VariableExpression("v")) //  v should be 3
        ));
        var typeEnv3 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv3 = (ADTDictionary<String, TypeInterface>)example3.TypeCheck(typeEnv3);
            var output3 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable3 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable3 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack3 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap3 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState3 = new ProgramState(executionStack3, symbolTable3, example3, output3, fileTable3, heap3);
            var repository3 = new Repository("logFile3.txt");
            var controller3 = new Controller(repository3);
            controller3.addProgram(ProgramState3);
            textMenu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }







        //  Fourth program example using files
        var example4 = connectStatements(List.of(
                new VariableDeclarationStatement("varf", new StringType()),              //  Declare variable to hold the file name
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),   //  Assign it the file name
                new OpenRFileStatement(new VariableExpression("varf")),                  //  Open the file
                new VariableDeclarationStatement("varc", new IntType()),                 //  Declare variable to hold the integers
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the first integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the first integer
                new ReadFileStatement(new VariableExpression("varf"), "varc"),  //  Read the second integer
                new PrintStatement(new VariableExpression("varc")),                      //  Print the second integer
                new CloseRFileStatement(new VariableExpression("varf"))                  //  Close the file
        ));
        var typeEnv4 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv4 = (ADTDictionary<String, TypeInterface>)example4.TypeCheck(typeEnv4);
            var output4 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable4 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable4 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack4 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap4 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState4 = new ProgramState(executionStack4, symbolTable4, example4, output4, fileTable4, heap4);
            var repository4 = new Repository("logFile4.txt");
            var controller4 = new Controller(repository4);
            controller4.addProgram(ProgramState4);
            textMenu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));
        } catch (MyException e){
            System.out.println(e.getMessage());
        }






        //  Fifth program example using files   Error is raised because of an empty file
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
        var typeEnv5 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv5 = (ADTDictionary<String, TypeInterface>)example5.TypeCheck(typeEnv5);
            var output5 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable5 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable5 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack5 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap5 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState5 = new ProgramState(executionStack5, symbolTable5, example5, output5, fileTable5, heap5);
            var repository5 = new Repository("logFile5.txt");
            var controller5 = new Controller(repository5);
            controller5.addProgram(ProgramState5);
            textMenu.addCommand(new RunExampleCommand("5", example5.toString() + "EMPTY FILE", controller5));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }







        //  Sixth program example using the while loop
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
        var typeEnv6 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv6 = (ADTDictionary<String, TypeInterface>)example6.TypeCheck(typeEnv6);
            var output6 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable6 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable6 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack6 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap6 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState6 = new ProgramState(executionStack6, symbolTable6, example6, output6, fileTable6, heap6);
            var repository6 = new Repository("logFile6.txt");
            var controller6 = new Controller(repository6);
            controller6.addProgram(ProgramState6);
            textMenu.addCommand(new RunExampleCommand("6", example6.toString(), controller6));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }








        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);   output: 20
        var example7 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),            //  Add a new reference pf type int
                new NewStatement("v", new ValueExpression(new IntValue(20))),                 //  Allocate a new integer of value 20 at address v
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),     // ref ref int a
                new NewStatement("a", new VariableExpression("v")),                         //  Allocate a new reference to the v reference
                new NewStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        ));
        var typeEnv7 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv7 = (ADTDictionary<String, TypeInterface>)example7.TypeCheck(typeEnv7);
            var output7 = new ADTList<ValueInterface>();
            var symbolTable7 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable7 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack7 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap7 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState7 = new ProgramState(executionStack7, symbolTable7, example7, output7, fileTable7, heap7);
            var repository7 = new Repository("logFile7.txt");
            var controller7 = new Controller(repository7);
            controller7.addProgram(ProgramState7);
            textMenu.addCommand(new RunExampleCommand("7", example7.toString(), controller7));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }






        //int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
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
        var typeEnv8 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv8 = (ADTDictionary<String, TypeInterface>)example8.TypeCheck(typeEnv8);
            var output8 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable8 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable8 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack8 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap8 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState8 = new ProgramState(executionStack8, symbolTable8, example8, output8, fileTable8, heap8);
            var repository8 = new Repository("logfile8.txt");
            var controller8 = new Controller(repository8);
            controller8.addProgram(ProgramState8);
            textMenu.addCommand(new RunExampleCommand("8", example8.toString(), controller8));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }



        var example9 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(0))),
                new RepeatUntilStatement(connectStatements(List.of(
                        new PrintStatement(new VariableExpression("v")),
                        new AssignStatement("v",    // v=
                                new ArithmeticExpression(   // v+1
                                        new VariableExpression("v"), new ValueExpression(new IntValue(0)),'+')))),
                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "=")),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), '*'))
        ));
        var typeEnv9 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv9 = (ADTDictionary<String, TypeInterface>)example9.TypeCheck(typeEnv9);
            var output9 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable9 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable9 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack9 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap9 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState9 = new ProgramState(executionStack9, symbolTable9, example9, output9, fileTable9, heap9);
            var repository9 = new Repository("logfile9.txt");
            var controller9 = new Controller(repository9);
            controller9.addProgram(ProgramState9);
            textMenu.addCommand(new RunExampleCommand("9", example9.toString(), controller9));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }





        var example10 = connectStatements(List.of(
                new VariableDeclarationStatement("b", new BoolType()),
                new VariableDeclarationStatement("c", new IntType()),
                new AssignStatement("b", new ValueExpression(new BoolValue(true))),
                new ConditionalAssignmentStatement("c",new VariableExpression("b"),
                        new ValueExpression(new IntValue(100)),
                        new ValueExpression(new IntValue(200))),
                new PrintStatement(new VariableExpression("c")),
                new ConditionalAssignmentStatement("c",new ValueExpression(new BoolValue(false)),
                        new ValueExpression(new IntValue(100)),
                        new ValueExpression(new IntValue(200))),
                new PrintStatement(new VariableExpression("c"))
        ));
        var typeEnv10 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv10 = (ADTDictionary<String, TypeInterface>)example10.TypeCheck(typeEnv10);
            var output10 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable10 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable10 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack10 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap10 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState10 = new ProgramState(executionStack10, symbolTable10, example10, output10, fileTable10, heap10);
            var repository10 = new Repository("logfile10.txt");
            var controller10 = new Controller(repository10);
            controller10.addProgram(ProgramState10);
            textMenu.addCommand(new RunExampleCommand("10", example10.toString(), controller10));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }





        var example11 = connectStatements(List.of(
                new VariableDeclarationStatement("v", new IntType()),
                new ForLoopStatement("v",
                        new ValueExpression(new IntValue(3)),
                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)),">"),
                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-'),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new ValueExpression(new StringValue("asd")))))
        ));
        var typeEnv11 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv11 = (ADTDictionary<String, TypeInterface>)example11.TypeCheck(typeEnv11);
            var output11 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable11 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable11 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack11 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap11 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState11 = new ProgramState(executionStack11, symbolTable11, example11, output11, fileTable11, heap11);
            var repository11 = new Repository("logfile11.txt");
            var controller11 = new Controller(repository11);
            controller11.addProgram(ProgramState11);
            textMenu.addCommand(new RunExampleCommand("11", example10.toString(), controller11));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }








        var example12 = connectStatements(List.of(
                new VariableDeclarationStatement("a", new IntType()),
                new VariableDeclarationStatement("b", new IntType()),
                new VariableDeclarationStatement("c", new IntType()),
                new AssignStatement("a", new ValueExpression(new IntValue(1))),
                new AssignStatement("b", new ValueExpression(new IntValue(2))),
                new AssignStatement("c", new ValueExpression(new IntValue(5))),
                new SwitchStatement(new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(10)),'*'),
                        new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), '*'),    // case1
                        new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),    // stmt1
                        new ValueExpression(new IntValue(10)),  // case 2
                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),  // stmt2
                        new PrintStatement(new ValueExpression(new IntValue(300)))), // default
                new PrintStatement(new ValueExpression(new IntValue(300)))
        ));
        var typeEnv12 = new ADTDictionary<String, TypeInterface>();
        try{
            typeEnv12 = (ADTDictionary<String, TypeInterface>)example12.TypeCheck(typeEnv12);
            var output12 = new ADTList<ValueInterface>();                                           //  List containing the output
            var symbolTable12 = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
            var fileTable12 = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
            var executionStack12 = new ADTStack <StatementInterface>();                        //  Stack containing all the statements that have to be executed
            var heap12 = new ADTHeap<Integer, ValueInterface>();
            var ProgramState12 = new ProgramState(executionStack12, symbolTable12, example12, output12, fileTable12, heap12);
            var repository12 = new Repository("logfile12.txt");
            var controller12 = new Controller(repository12);
            controller12.addProgram(ProgramState12);
            textMenu.addCommand(new RunExampleCommand("12", example12.toString(), controller12));
        } catch (MyException e){
            System.out.println(e.getMessage());

        }


        textMenu.show();


    }
}
