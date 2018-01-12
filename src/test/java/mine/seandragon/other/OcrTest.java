package mine.seandragon.other;

import mine.seandragon.other.ocr.BaiduOcr;
import mine.seandragon.other.ocr.IOcr;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class OcrTest {

    @Test
    public void testBaiduOcr() {
        String path = "D:\\SeanDragon\\Pictures\\OCR\\";
        path += "timg1.jpg";
        IOcr ocr = new BaiduOcr();
        try {
            OcrInfo s = ocr.get(path);
            System.out.println(s);
        } catch (OcrException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOpen() {
        OpenQuestion.search("为什么太阳那么大");
    }
}
