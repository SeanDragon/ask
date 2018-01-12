package mine.seandragon.other.group;

import mine.seandragon.other.OcrInfo;

import java.io.IOException;

/**
 * 选举答案
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 18:05
 */
public interface IGroup {
    String group(OcrInfo ocrInfo) throws IOException;
}
