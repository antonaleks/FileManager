package functions;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.*;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

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

    public void getFiles(String path, FlowPane flowPane, TextField textField){
        if(!"".equals(path)){
            flowPane.getChildren().clear();
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

                vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                        clearSelected(flowPane);
                        vbox.setStyle("-fx-background-color:#6DB6DD;-fx-border-color:blue;-fx-border:5px;");
                        selectedFile = elem;
                        //DoubleClick
                        //Ленивая загрузка
                       if(event.getClickCount() > 1){
                            Thread t = new Thread(new Runnable(){
                                public void run() {
                                    try {
                                        vbox.setCursor(Cursor.WAIT);
                                        Image image3 = new Image(String.valueOf(getClass().getResource("../images/loading.png")));
                                        imv.setImage(image3);
                                        final Timeline timeline = new Timeline();
                                        timeline.setCycleCount(2);
                                        timeline.setAutoReverse(false);
                                        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000),
                                                new KeyValue(imv.rotateProperty(), 500)));
                                        timeline.play();
                                        Thread.currentThread().sleep(2000);
                                        Platform.runLater(() -> {
                                            getFiles(elem.getPath(),flowPane,textField);
                                            textField.setText(elem.getPath());
                                            clearSelected(flowPane);
                                        });

                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }
                            });
                            t.start();

                        }
                    }
                });
                flowPane.getChildren().add(vbox);

            }
        }
    }

}
