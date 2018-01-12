package mine.seandragon.other.ocr;

import mine.seandragon.other.OcrException;
import mine.seandragon.other.OcrInfo;

/**
 * 图像识别接口
 */
public interface IOcr {
    OcrInfo get(String imagePath) throws OcrException;

    OcrInfo get(byte[] imageByte) throws OcrException;
}
