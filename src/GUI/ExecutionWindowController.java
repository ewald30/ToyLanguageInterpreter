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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionWindowController implements Initializable {
    public AnchorPane panel2;
    public AnchorPane panel1;
    public Button button1;
    public Button button2;
    public Button oneStepButton;


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

        TextInputDialog dialog = new TextInputDialog("eg. logFile.txt");
        dialog.setTitle("File Input");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter a file name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(controller::SetRepositoryFile);
    }

    public void handleButton1(ActionEvent event) {
        panel2.setDisable(true);
        panel2.setOpacity(0);
        panel1.setDisable(false);
        panel1.setOpacity(1);
    }

    public void handleButton2(ActionEvent event) {
        panel1.setDisable(true);
        panel1.setOpacity(0);
        panel2.setDisable(false);
        panel2.setOpacity(1);
    }

    public void handleOneStep(ActionEvent event) throws InterruptedException, MyException {
        try{
//            ExecutorService executor = Executors.newFixedThreadPool(2);
//            ArrayList<ProgramState> programs = (ArrayList<ProgramState>) controller.removeCompletedPrograms(controller.getRepository().getProgramStates());
//
//            while (programs.size() > 0){
//                System.out.println(programs);
//                controller.singleStepForAllPrograms(programs);
//                programs = (ArrayList<ProgramState>)controller.removeCompletedPrograms(programs);
//            }
//            executor.shutdownNow();
//            controller.getRepository().setProgramStates(programs);
            controller.allStepsExecution();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println("asdasd" + e.getMessage());
        }

    }
}
