package mine.seandragon.other;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 13:58
 */
public class OcrException extends Exception {
    public OcrException() {
        super();
    }

    public OcrException(String errMsg) {
        super(errMsg);
    }

    public OcrException(String errMsg, Exception e) {
        super(errMsg, e);
    }
}
