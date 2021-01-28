package GUI;

import Controller.Controller;
import GUI.WrapperClasses.*;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.DesignPattern.MyObserver;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionWindowController implements Initializable, MyObserver {

    //  GUI items
    public AnchorPane panel1;
    public Button oneStepButton;
    public ListView OutputGUI;
    public ListView ExeStackGUI;
    public ListView FIleTableGUI;
    public ListView ProgramIDs;
    public TextField NumberProgramStates;

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
    ADTDictionary<Integer, Pair<Integer, List<Integer>>> semTable;
    public ProgramState programState;
    private Repository repository;
    private Controller controller;


    //  Used to see individual program states when multithreading
    int program_id;
    ArrayList<ProgramState> current_programStates;



    public void setProgram(StatementInterface program) {
        //  Sets the program that will be executed
        this.program = program;
        programState = new ProgramState(executionStack, symbolTable, program, output, fileTable, heap, semTable);
        controller.register(this);
        controller.addProgram(programState);
        ExeStackGUI.getItems().add(program.toString());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeEnv = new ADTDictionary<String, TypeInterface>();
        output = new ADTList<ValueInterface>();                                           //  List containing the output
        symbolTable = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
        fileTable = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
        executionStack = new ADTStack<StatementInterface>();                        //  Stack containing all the statements that have to be executed
        semTable = new ADTDictionary<Integer, Pair<Integer, List<Integer>>>();
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
        //System.out.println(currentProgramStates.get(0).toString());
        updateExeListGUI(currentProgramStates);
        updateFileTableListGUI(currentProgramStates);
        updateOutputListGUI(currentProgramStates);
        updateSymbolTableGUI(currentProgramStates);
        updateHeapTableGUI(currentProgramStates);
        updateProgramStatesGUI(currentProgramStates);
        updateProgramIDsGUI(currentProgramStates);

    }

    private void updateProgramIDsGUI(ArrayList<ProgramState> currentProgramStates){
        //  Updates the program states ids list
        ProgramIDs.getItems().clear();
        currentProgramStates.stream().forEach(p -> ProgramIDs.getItems().add(p.getId()));

    }

    private void updateProgramStatesGUI(ArrayList<ProgramState> currentProgramStates){
        //  Update the number of program states
        NumberProgramStates.clear();
        NumberProgramStates.setText("Number of program states: " + currentProgramStates.size());
    }

    private void updateExeListGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the execution stack list

//        System.out.println("_________________________");
//        currentProgramStates.stream().forEach(p -> System.out.println(p.getExecutionStack()));
//        System.out.println("_________________________");

        ProgramState state = currentProgramStates.stream()
                .filter(p -> p.getId() == program_id)
                .findAny()
                .orElse(null);

       ExeStackGUI.getItems().clear();
        Arrays.stream(state.getExecutionStack().getContent().toArray())
                .forEach(p -> ExeStackGUI.getItems().add(p.toString()));

    }

    private void updateFileTableListGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the file table list
        ProgramState state = currentProgramStates.stream()
                .filter(p -> p.getId() == program_id)
                .findAny()
                .orElse(null);

        FIleTableGUI.getItems().clear();
        state.getFileTable().getContent().keySet().stream()
                .forEach(f -> FIleTableGUI.getItems().add(f));
    }

    private void updateOutputListGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the output list
        ProgramState state = currentProgramStates.stream()
                .filter(p -> p.getId() == program_id)
                .findAny()
                .orElse(null);

        OutputGUI.getItems().clear();
        state.getOutput().getContent().stream()
                .forEach(o -> OutputGUI.getItems().add(o.toString()));
    }

    private void updateSymbolTableGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the symbol table
        ProgramState state = currentProgramStates.stream()
                .filter(p -> p.getId() == program_id)
                .findAny()
                .orElse(null);
        var symTable = state.getSymbolTable().getContent();
        SymbolTableGUI.getItems().clear();
        symTable.keySet().stream()
                .forEach(p -> variableList.add(new VariableWrapper(p, symTable.get(p).toString())));
        SymbolTableGUI.setItems(variableList);
    }

    private void updateHeapTableGUI(ArrayList<ProgramState> currentProgramStates){
        //      Update the heap table
        ProgramState state = currentProgramStates.stream()
                .filter(p -> p.getId() == program_id)
                .findAny()
                .orElse(null);
        var heapTable = state.getHeap().getContent();
        HeapTableGUI.getItems().clear();
        heapTable.keySet().stream()
                .forEach(r -> referenceList.add(new ReferenceWrapper(r.toString(),heapTable.get(r).toString())));
        HeapTableGUI.setItems(referenceList);
    }



    public void handleOneStep(ActionEvent event) throws InterruptedException, MyException {
        try{
            ArrayList<ProgramState> programs = (ArrayList<ProgramState>) controller.removeCompletedPrograms(controller.getRepository().getProgramStates());
            System.out.println(programs.size());

            if(programs.size() > 0){
                controller.singleStepForAllPrograms(programs);

                ArrayList<ProgramState> finalPrograms = programs;
                programs = (ArrayList<ProgramState>)controller.removeCompletedPrograms(programs);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("The execution stack is empty!");
                alert.showAndWait();
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


    public void handleSelectedProgramState(MouseEvent inputMethodEvent) {
        String index_string = ProgramIDs.getSelectionModel().getSelectedItem().toString();
        program_id = Integer.parseInt(index_string);
        ArrayList<ProgramState> programs = (ArrayList<ProgramState>) controller.removeCompletedPrograms(controller.getRepository().getProgramStates());
        update(programs);

    }
}
