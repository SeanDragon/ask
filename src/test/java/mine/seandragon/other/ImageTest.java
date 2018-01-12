package mine.seandragon.other;

import org.junit.Test;
import pro.tools.data.image.ToolImageTailor;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 14:10
 */
public class ImageTest {
    @Test
    public void test1() throws IOException {
        String srcPath = "D:\\SeanDragon\\Pictures\\OCR\\timg2.jpg";
        String newPath = "D:\\SeanDragon\\Pictures\\OCR\\timg3.jpg";
        //100, 300, 900, 900
        int x = 100, y = 300, width = 900, height = 900;
        height = 1050;
        ToolImageTailor.cut(srcPath, newPath, x, y, width, height);
    }

    @Test
    public void test2() {
        Iterator<ImageReader> png = ImageIO.getImageReadersByFormatName("png");
        System.out.println(png);
    }
}
