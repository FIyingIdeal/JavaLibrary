package test.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.Hashtable;

/**
 * Created by Administrator on 2016/7/26.
 */
//生成二维码
public class ZxingDemo {

    public static void main(String[] args) throws Exception{
        String text = "www.baidu.com";
        int width = 300;
        int height = 300;
        String format = "png";

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        File outputFile = new File("F:" + File.separator + "zxing.png");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    }
}
