package GUI;

import Controller.Controller;
import GUI.WrapperClasses.ReferenceWrapper;
import GUI.WrapperClasses.VariableWrapper;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    //  GUI items
    public AnchorPane panel1;
    public Button oneStepButton;
    public ListView OutputGUI;
    public ListView ExeStackGUI;
    public ListView FIleTableGUI;

    //  Symbol table
    public TableView<VariableWrapper> SymbolTableGUI;
    public TableColumn<VariableWrapper, String> VarNameColumnGUI;
    public TableColumn<VariableWrapper, String> ValueColumnGUI;
    ObservableList<VariableWrapper> variableList = FXCollections.observableArrayList();

    //  Heap table
    public TableView<ReferenceWrapper> HeapTableGUI;
    public TableColumn<ReferenceWrapper, String> HeapAddrGUI;
    public TableColumn<ReferenceWrapper, String> HeapValueGUI;
    ObservableList<ReferenceWrapper> referenceList = FXCollections.observableArrayList();


    //  Those are used for executing the program
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
        //  Sets the program that will be executed
        this.program = program;
        programState = new ProgramState(executionStack, symbolTable, program, output, fileTable, heap);
        programState.register(this);
        controller.addProgram(programState);
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

        //  Setting the executor for the controller
        ExecutorService executor = Executors.newFixedThreadPool(2);
        controller.setExecutor(executor);

        //  Dialog used for writing the file name
        TextInputDialog dialog = new TextInputDialog("eg. logFile.txt");
        dialog.setTitle("File Input");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter a file name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(controller::SetRepositoryFile);


        //  Symbol table
        VarNameColumnGUI.setCellValueFactory(new PropertyValueFactory<VariableWrapper, String>("Name"));
        ValueColumnGUI.setCellValueFactory(new PropertyValueFactory<VariableWrapper, String>("Value"));
        SymbolTableGUI.setItems(variableList);

        //  Heap table
        HeapAddrGUI.setCellValueFactory(new PropertyValueFactory<ReferenceWrapper, String>("Address"));
        HeapValueGUI.setCellValueFactory(new PropertyValueFactory<ReferenceWrapper, String>("Value"));
        HeapTableGUI.setItems(referenceList);


    }

    @Override
    public void update(ArrayList<ProgramState> currentProgramStates) {
        //  Will update with the given program states the GUI
        System.out.println(currentProgramStates.get(0).toString());
        updateExeListGUI(currentProgramStates);
        updateFileTableListGUI(currentProgramStates);
        updateOutputListGUI(currentProgramStates);
        updateSymbolTableGUI(currentProgramStates);
        updateHeapTableGUI(currentProgramStates);

    }

    private void updateExeListGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the execution stack list
        ExeStackGUI.getItems().clear();
        Arrays.stream(currentProgramStates.get(0).getExecutionStack().getContent().toArray())
                .forEach(p -> ExeStackGUI.getItems().add(p.toString()));
    }

    private void updateFileTableListGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the file table list
        FIleTableGUI.getItems().clear();
        currentProgramStates.get(0).getFileTable().getContent().keySet().stream()
                .forEach(f -> FIleTableGUI.getItems().add(f));
    }

    private void updateOutputListGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the output list
        OutputGUI.getItems().clear();
        currentProgramStates.get(0).getOutput().getContent().stream()
                .forEach(o -> OutputGUI.getItems().add(o.toString()));
    }

    private void updateSymbolTableGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the symbol table
        var symTable = currentProgramStates.get(0).getSymbolTable().getContent();
        SymbolTableGUI.getItems().clear();
        symTable.keySet().stream()
                .forEach(p -> variableList.add(new VariableWrapper(p, symTable.get(p).toString())));
        SymbolTableGUI.setItems(variableList);
    }

    private void updateHeapTableGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the heap table
        var heapTable = currentProgramStates.get(0).getHeap().getContent();
        HeapTableGUI.getItems().clear();
        heapTable.keySet().stream()
                .forEach(r -> referenceList.add(new ReferenceWrapper(r.toString(),heapTable.get(r).toString())));
        HeapTableGUI.setItems(referenceList);
    }



    public void handleOneStep(ActionEvent event) throws InterruptedException, MyException {
        try{
            ArrayList<ProgramState> programs = (ArrayList<ProgramState>) controller.removeCompletedPrograms(controller.getRepository().getProgramStates());

            if(programs.size() > 0){
                controller.singleStepForAllPrograms(programs);

                ArrayList<ProgramState> finalPrograms = programs;
                programs = (ArrayList<ProgramState>)controller.removeCompletedPrograms(programs);
            }
            else {
                controller.getExecutor().shutdownNow();
                controller.getRepository().setProgramStates(programs);
            }

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println(e.getMessage());
        }

    }
}
