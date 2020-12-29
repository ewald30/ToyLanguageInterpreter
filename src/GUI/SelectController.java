package GUI;

import Controller.Controller;
import Model.ADTs.ADTDictionary;
import Model.ADTs.ADTHeap;
import Model.ADTs.ADTList;
import Model.ADTs.ADTStack;
import Model.Exceptions.MyException;
import Model.ProgramCreator;
import Model.ProgramState;
import Model.Statements.StatementInterface;
import Model.Types.TypeInterface;
import Model.Values.StringValue;
import Model.Values.ValueInterface;
import Repository.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SelectController implements Initializable {
    ProgramCreator programCreator;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programCreator = new ProgramCreator();
        programCreator.CreatePrograms();
        loadData();
    }


    //  ------------ Program selection and displaying data  --------------  //
    @FXML
    private ListView <String> program_list;

    @FXML
    public Label program_label;

    @FXML
    public void handleSelectedItem(MouseEvent mouseEvent) {
        /*  Gets the selected program description and prints the program string representation
                Throws: None
                Return: None
        */
        int programNumber = program_list.getSelectionModel().getSelectedIndex();                //  Get the selected item's index
        program_label.setText(programCreator.getProgram_list().get(programNumber).toString());  //  Display the program
        run_button.setDisable(false);                                                           //  Enable the button
    }
    // ----------------------------------------------------------------------//



    //  ------------ Running the program and creating the log file  --------------  //
    @FXML
    public Button run_button;

    public void handleRunButtonPressed(ActionEvent event) throws MyException, InterruptedException, IOException {
        System.out.println(program_list.getSelectionModel().getSelectedIndex());
        int programNumber = program_list.getSelectionModel().getSelectedIndex();                //  Get the selected item's index
        StatementInterface program = programCreator.getProgram_list().get(programNumber);



        var typeEnv = new ADTDictionary<String, TypeInterface>();
        var output = new ADTList<ValueInterface>();                                           //  List containing the output
        var symbolTable = new ADTDictionary <String, ValueInterface>();         //  Dictionary containing the symbol table
        var fileTable = new ADTDictionary<StringValue, BufferedReader>();   //  Dictionary containing files names and buffered readers
        var executionStack = new ADTStack<StatementInterface>();                        //  Stack containing all the statements that have to be executed
        var heap = new ADTHeap<Integer, ValueInterface>();
        var ProgramState = new ProgramState(executionStack, symbolTable, program, output, fileTable, heap);
        var repository = new Repository("logFileGUI.txt");
        var controller = new Controller(repository);


        try {
            typeEnv = (ADTDictionary<String, TypeInterface>) program.TypeCheck(typeEnv);
            controller.addProgram(ProgramState);

            TextInputDialog dialog = new TextInputDialog("eg. logFile.txt");
            dialog.setTitle("File Input");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter a file name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(controller::SetRepositoryFile);

            Parent root = FXMLLoader.load(getClass().getResource("ExecutionWindow.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("asdasd");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            controller.allStepsExecution();
            run_button.setDisable(true);

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println("asdasd" + e.getMessage());     // add an error window
        }
    }

    // ----------------------------------------------------------------------------//


    private void loadData(){
        programCreator.getProgram_description_list().stream()
                .forEach(d -> program_list.getItems().add(d));
    }



}
