package functions;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.File;

public class WindowsFiles {


    private Label label;
    private ImageView icon;
    private VBox vbox;
    private File selectedFile;

    public File getSelectedFile() {
        return selectedFile;
    }

    private void clearSelected(FlowPane flowPane){
        for(int i=0;i<flowPane.getChildren().size();i++)
        flowPane.getChildren().get(i).setStyle("-fx-background-color:none; border:none");
        selectedFile = null;
    }

    public void getFiles(String path,FlowPane flowPane){
        if(!"".equals(path)){
            File[] files = new File(path).listFiles();
            for(File elem:files){
                ImageView imv = new ImageView();
                Image image2 = new Image(String.valueOf(getClass().getResource("../images/" + ((elem.isDirectory())?"closedFolder.png":"file.png"))));
                imv.setImage(image2);
                VBox vbox = new VBox();
                vbox.setPrefHeight(120);
                vbox.setPrefWidth(50);
                vbox.paddingProperty().setValue(new Insets(20,20,20,20));
                Label label = new Label(elem.getName());
                label.setFont(Font.font(12));
                label.setMaxWidth(50);
                label.setMaxHeight(50);
                label.setWrapText(true);
                vbox.alignmentProperty().setValue(Pos.TOP_CENTER);
                vbox.getChildren().addAll(imv,label);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                        clearSelected(flowPane);
                        vbox.setStyle("-fx-background-color:#6DB6DD;-fx-border-color:blue;-fx-border:5px;");
                        selectedFile = elem;
                    }
                });
                flowPane.getChildren().add(vbox);

            }
        }
    }
}
