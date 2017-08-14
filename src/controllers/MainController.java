package controllers;

import functions.DinamicFilesTree;
import functions.WindowEvents;
import functions.WindowsFiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TreeView<File> treeView;

    @FXML
    private FlowPane window;

    @FXML
    private TextField pathField;

    private TreeItem<File> rootItem;

    private WindowsFiles file;

    private WindowEvents event;

    private String newFileName;


    private Parent fxmlEdit;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private DialogController editDialogController;
    private Stage editDialogStage;

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public void refresh(){
        if(pathField.getText()!=null){
            window.getChildren().clear();
            file = new WindowsFiles();
            file.getFiles(pathField.getText(),window,pathField);
            treeView.setRoot(new DinamicFilesTree().createNode(new File("C:\\")));
        }
    }

    @FXML
    public void showFilesInWindow(MouseEvent mouseEvent) {
        TreeItem<File> rootPath= treeView.getSelectionModel().getSelectedItem();
        if(rootPath!=null){
            file = new WindowsFiles();
            pathField.setText(rootPath.getValue().getPath());
            file.getFiles(rootPath.getValue().getPath(),window,pathField);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootItem = new DinamicFilesTree().createNode(new File("C:\\"));

        treeView.setRoot(rootItem);


        try {

            fxmlLoader.setLocation(getClass().getResource("../fxml/newNameForm.fxml"));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(ActionEvent actionEvent) throws IOException {

        if(editDialogStage==null){
            Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
            editDialogStage = new Stage();
            editDialogStage.setTitle("Edit name");
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(parentWindow);
        }
        editDialogStage.showAndWait();

    }

    @FXML
    public void renameButton(ActionEvent actionEvent) throws IOException {
        showDialog(actionEvent);
        newFileName = editDialogController.getName();
        event = new WindowEvents(file.getSelectedFile());
        event.rename(newFileName);
        refresh();
    }
    @FXML
    public void newFolderButton(ActionEvent actionEvent) throws IOException {
        showDialog(actionEvent);
        newFileName = editDialogController.getName();
        event = new WindowEvents(new File(pathField.getText()));
        event.insert(newFileName);
        refresh();
    }
    @FXML
    public void pasteButton(ActionEvent actionEvent) throws IOException {
        event.setFile(new File(pathField.getText()));
        event.paste();
        refresh();
    }
    @FXML
    public void copyButton(ActionEvent actionEvent) {
            event = new WindowEvents(file.getSelectedFile());
            event.copy();
    }
    @FXML
    public void deleteButton(ActionEvent actionEvent) {
        event = new WindowEvents(file.getSelectedFile());
        event.delete();
        refresh();
    }
}
