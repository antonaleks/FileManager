package functions;

import controllers.MainController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowEvents {
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    private File copy;

    public WindowEvents(File file) {
        this.file = file;
    }

    public boolean insert(String name) throws IOException {
        new File(file.getPath()+File.separator+File.separator+name).mkdir();
        return true;
    }

    public boolean rename(String name){
        StringBuffer strPath = new StringBuffer(file.getPath());
        strPath.delete(strPath.lastIndexOf(File.separator),strPath.length());
        System.out.println(strPath);
        file.renameTo(new File(strPath+File.separator+File.separator+name));
        return true;
    }

    public void delete(){
        file.delete();
    }

    public boolean copy(){
        copy = file;
        return true;
    }

    public File getFile() {
        return file;
    }

    public File getCopy() {
        return copy;
    }

    public boolean paste() throws IOException {
        Files.copy(copy.toPath(),
                new File(file.getPath()+File.separator+File.separator+copy.getName()).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return true;
    }
    private boolean copyFile(final String src, final String dst) {
        return true;
    }

}
