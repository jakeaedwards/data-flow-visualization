package Examples;

import java.io.File;

/**
 * Created by Jake on 7/14/2015.
 */
public class PathTesting {

    public static void main(String[] args) throws Exception{

        File file = new File(".\\test");


        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getPath());

    }
}
