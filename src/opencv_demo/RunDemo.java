package opencv_demo;

import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Christian
 */
public class RunDemo {

    JLabel imgDisplay = new JLabel(), imgDisplayModel = new JLabel(), resultDisplay = new JLabel();

    private void createJFrame() {
        String title = "Teste Christian";
        JFrame frame = new JFrame(title);
        frame.setLayout(new GridLayout(3, 2));
        
        frame.add(imgDisplayModel);
        frame.add(imgDisplay);
        frame.add(resultDisplay);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void run(String[] args) {

        /// Carregar imagem
        Mat imgObj = Imgcodecs.imread(args[0], Imgcodecs.IMREAD_COLOR);
        /// Carregar modelo - referencia
        Mat imgObjRef = Imgcodecs.imread(args[1], Imgcodecs.IMREAD_COLOR);

        MatchTemplateImage obj = new MatchTemplateImage(imgObj, imgObjRef);
        obj.matchingMethod();
        
        // mostra em tela imagem modelo
        Image tmpref = HighGui.toBufferedImage(imgObjRef);
        ImageIcon iconRef = new ImageIcon(tmpref);
        imgDisplayModel.setIcon(iconRef);        
        
        // após o match mostra o resultado
        Image tmpImg = HighGui.toBufferedImage(obj.GetImageDisplay());
        ImageIcon icon = new ImageIcon(tmpImg);
        imgDisplay.setIcon(icon);

        // após o match mostra o resultado (RX)
        tmpImg = HighGui.toBufferedImage(obj.GetImageProcess());
        icon = new ImageIcon(tmpImg);
        resultDisplay.setIcon(icon);

        createJFrame();
    }

}
