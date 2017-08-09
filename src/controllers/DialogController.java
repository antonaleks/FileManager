package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class DialogController {

    @FXML
    private TextField nameField;

    private String name;

    public String getName() {
        return name;
    }

    public void okButton(ActionEvent actionEvent) {
        name = nameField.getText();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    public void cancelButton(ActionEvent actionEvent) {
        name = null;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }
}
