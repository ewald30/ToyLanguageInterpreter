package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ExecutionWindowController{
    public AnchorPane panel2;
    public AnchorPane panel1;
    public Button button1;
    public Button button2;



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
}
