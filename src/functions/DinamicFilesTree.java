package functions;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import structures.MyFile;

  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Anton
 */

class MyFileNameFilter implements FilenameFilter {

    private String ext;

    public MyFileNameFilter(String ext){
        this.ext = ext.toLowerCase();
    }
    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ext);
    }
}

public class DinamicFilesTree {
    /**
     * Создаем динамичное дерево каталогов.
     * Создаем корень. Каждый узел, имеющий внутри папку, будет являтся корнем для внутренних.
     */
//
    public TreeItem<File> createNode(File f) {

        return new TreeItem<File>(new MyFile(f.getPath())) {
            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<File>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;

                    //File[] filesArray = f.listFiles(new MyFileNameFilter(""));
                    isLeaf = f.isFile();
                   /* if (filesArray != null && filesArray.length != 0)
                        isLeaf = !(filesArray[0].isDirectory());
                    else isLeaf = true;*/
                }
                return isLeaf;
            }


            private ObservableList<TreeItem<File>> buildChildren(
                    TreeItem<File> TreeItem) {
                File f = TreeItem.getValue();
                if (f == null) {
                    return FXCollections.emptyObservableList();
                }
                if (f.isFile()) {
                    return FXCollections.emptyObservableList();
                }
                File[] allfiles = f.listFiles();
                ArrayList<File> files = new ArrayList<>();
                for (File elem : allfiles) {
                    if (elem.isDirectory()) files.add(elem);
                }
                if (files != null) {
                    ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
                    for (File childFile : files) {
                        children.add(createNode(childFile));
                    }
                    return children;
                }
                return FXCollections.emptyObservableList();
            }
        };
    }

}


