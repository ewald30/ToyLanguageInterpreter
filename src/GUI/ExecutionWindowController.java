package GUI;

import Controller.Controller;
import Model.ADTs.ADTDictionary;
import Model.ADTs.ADTHeap;
import Model.ADTs.ADTList;
import Model.ADTs.ADTStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.StatementInterface;
import Model.Types.TypeInterface;
import Model.Values.StringValue;
import Model.Values.ValueInterface;
import Repository.Repository;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import Model.DesignPattern.MyObserver;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionWindowController implements Initializable, MyObserver {
    public AnchorPane panel1;
    public Button oneStepButton;
    public ListView ProgramIDsListView;
    public TableView SymbolTableGUI;
    public TableColumn VarNameColumnGUI;
    public TableColumn ValueColumnGUI;
    public ListView OutputGUI;
    public ListView ExeStackGUI;
    public ListView FIleTableGUI;
    public TableView HeapTableGUI;
    public TableColumn HeapAddrGUI;
    public TableColumn HeapValueGUI;


    StatementInterface program;
    ADTDictionary<String, TypeInterface> typeEnv;
    ADTList<ValueInterface> output;
    private ADTDictionary<String, ValueInterface> symbolTable;
    private ADTDictionary<StringValue, BufferedReader> fileTable;
    private ADTStack<StatementInterface> executionStack;
    private ADTHeap<Integer, ValueInterface> heap;
    public ProgramState programState;
    private Repository repository;
    private Controller controller;

    public void setProgram(StatementInterface program) {
        this.program = program;
        programState = new ProgramState(executionStack, symbolTable, program, output, fileTable, heap);
        programState.register(this);
        controller.addProgram(programState);

    }

    @Override
    public void update(ArrayList<ProgramState> currentProgramStates) {
        System.out.println("HOPAAAAA ceva s-a intamplat");
        System.out.println(currentProgramStates.get(0).getFileTable().toString());

        //      Update the execution stack list
        ExeStackGUI.getItems().clear();
        Arrays.stream(currentProgramStates.get(0).getExecutionStack().getContent().toArray())
                .forEach(p -> ExeStackGUI.getItems().add(p.toString()));


        //      Update the file table list
        FIleTableGUI.getItems().clear();
        currentProgramStates.get(0).getFileTable().getContent().keySet().stream()
                .forEach(f -> FIleTableGUI.getItems().add(f));



        //      Update the output list
        OutputGUI.getItems().clear();
        currentProgramStates.get(0).getOutput().getContent().stream()
                .forEach(o -> OutputGUI.getItems().add(o.toString()));





        //      Update the symbol table
        var symTable = currentProgramStates.get(0).getSymbolTable().getContent();

        ObservableList<String> values = FXCollections.observableArrayList(symTable.values().toString());
        ObservableList<String> keys = FXCollections.observableArrayList(symTable.keySet());
        ObservableList<String> data = FXCollections.observableArrayList(values);
        data.addAll(keys);
        SymbolTableGUI.setItems(data);
//        for(String key :symTable.keySet()){
//            SymbolTableGUI,
//
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeEnv = new ADTDictionary<String, TypeInterface>();
        output = new ADTList<ValueInterface>();                                           //  List containing the output
        symbolTable = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
        fileTable = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
        executionStack = new ADTStack<StatementInterface>();                        //  Stack containing all the statements that have to be executed
        heap = new ADTHeap<Integer, ValueInterface>();
        repository = new Repository("logFileGUI.txt");
        controller = new Controller(repository);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        controller.setExecutor(executor);

        TextInputDialog dialog = new TextInputDialog("eg. logFile.txt");
        dialog.setTitle("File Input");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter a file name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(controller::SetRepositoryFile);


        TableColumn<String, String> tableColumn = new TableColumn<>("VarName");
        TableColumn<String, String> tableColumn2 = new TableColumn<>("Value");
        tableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        tableColumn2.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        SymbolTableGUI.getColumns().add(tableColumn);
        SymbolTableGUI.getColumns().add(tableColumn2);
    }


    public void handleOneStep(ActionEvent event) throws InterruptedException, MyException {
        try{
            ArrayList<ProgramState> programs = (ArrayList<ProgramState>) controller.removeCompletedPrograms(controller.getRepository().getProgramStates());

            if(programs.size() > 0){
                controller.singleStepForAllPrograms(programs);

                ArrayList<ProgramState> finalPrograms = programs;
                programs.forEach(p -> p.notifyObservers(finalPrograms));
                programs = (ArrayList<ProgramState>)controller.removeCompletedPrograms(programs);
            }
            else {
                controller.getExecutor().shutdownNow();
                controller.getRepository().setProgramStates(programs);
            }
//          controller.allStepsExecution();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println("asdahbghjgjhgjhkjkghkjhghjkghkgjhkgjhjgksd" + e.getMessage());
        }

    }
}
