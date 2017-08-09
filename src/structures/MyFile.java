package structures;

import java.io.File;

public class MyFile extends File {
    public MyFile(String pathname) {
        super(pathname);
    }

    @Override
    public String toString(){
        if(!this.equals(new File("C:\\")))
            return this.getName();
        else return this.getPath();
    }
}
