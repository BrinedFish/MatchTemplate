/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv_demo;

import org.opencv.core.Core;


/**
 *
 * @author Christian
 */
public class OpenCV_Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String[] argsx = new String[] {"C:\\Temp\\a.jpeg", "C:\\Temp\\a2.jpeg"};
        new RunDemo().run(argsx);
    }
    
}
