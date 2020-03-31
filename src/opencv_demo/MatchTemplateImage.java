package opencv_demo;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Christian
 */
public class MatchTemplateImage {

    private Boolean use_mask = false;
    private Mat imgObj = new Mat();
    private Mat imgObjRef = new Mat();
    private Mat mask = new Mat();
    private MatchMethodType match_method = MatchMethodType.SQDIFF;
    private Mat img_display;
    private Mat result = new Mat();

    public MatchTemplateImage(Mat _image, Mat _imageModel) {
        /// Carregar imagem e modelo
        this.setImgObj(_image);
        this.setImgObjRef(_imageModel);

        if (imgObj.empty() || imgObjRef.empty() || (use_mask && mask.empty())) {
            System.out.println("Não consegue ler uma das imagens");
            System.exit(-1);
        }
    }

    public Mat GetImageDisplay() {
        return img_display;
    }
    
    public Mat GetImageProcess() {
        return result;
    }
    
    public Mat getImgObj() {
        return imgObj;
    }

    public void setImgObj(Mat imgObj) {
        this.imgObj = imgObj;
    }

    public Mat getImgObjRef() {
        return imgObjRef;
    }

    public void setImgObjRef(Mat imgObjRef) {
        this.imgObjRef = imgObjRef;
    }

    public Mat getMask() {
        return mask;
    }

    public void setMask(Mat mask) {
        this.mask = mask;
        this.use_mask = false;
        if (!this.mask.empty()) {
            this.use_mask = true;
        }
    }

    public MatchMethodType getMatch_method() {
        return match_method;
    }

    public void setMatch_method(MatchMethodType match_method) {
        this.match_method = match_method;
    }

    public Mat matchingMethod() {
        result = new Mat();

        /// Imagem a ser exibida
        img_display = new Mat();
        imgObj.copyTo(img_display);

        /// Crie a matriz de resultados
        int result_cols = imgObj.cols() - imgObjRef.cols() + 1;
        int result_rows = imgObj.rows() - imgObjRef.rows() + 1;
        result.create(result_rows, result_cols, CvType.CV_32FC1);

        /// Faz a correspondência e normalize
        Boolean method_accepts_mask = (Imgproc.TM_SQDIFF == match_method.getValor() || match_method.getValor() == Imgproc.TM_CCORR_NORMED);
        if (use_mask && method_accepts_mask) {
            Imgproc.matchTemplate(imgObj, imgObjRef, result, match_method.getValor(), mask);
        } else {
            Imgproc.matchTemplate(imgObj, imgObjRef, result, match_method.getValor());
        }

        // [normalize]
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        /// Localizando a melhor correspondência com o minMaxLoc
        Point matchLoc;
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        /// Para SQDIFF e SQDIFF_NORMED, as melhores correspondências são valores mais baixos.
        /// Para todos os outros métodos, quanto maior, melhor
        if (match_method.getValor() == Imgproc.TM_SQDIFF || match_method.getValor() == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        /// Mostra o que conseguiu - Desenha um retangulo 
        Imgproc.rectangle(img_display, matchLoc, new Point(matchLoc.x + imgObjRef.cols(), matchLoc.y + imgObjRef.rows()),
                new Scalar(0, 0, 0), 2, 8, 0);
        Imgproc.rectangle(result, matchLoc, new Point(matchLoc.x + imgObjRef.cols(), matchLoc.y + imgObjRef.rows()),
                new Scalar(0, 0, 0), 2, 8, 0);
                
        result.convertTo(result, CvType.CV_8UC1, 255.0);
        return result;
    }
}

