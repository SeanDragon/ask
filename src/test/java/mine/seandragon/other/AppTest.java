package mine.seandragon.other;

import mine.seandragon.other.group.BaiduGroup;
import mine.seandragon.other.group.IGroup;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * <p>
 * Create By 2018-01-12 19:25
 */
public class AppTest {
    @Test
    public void test1() throws IOException {
        OcrInfo ocrInfo = new OcrInfo();
        ocrInfo.setQuestion("“垂死病中惊坐起” 是谁写给谁的?");
        List<String>  addressList = new ArrayList<>(3);
        addressList.add("元稹写给白居易的");
        addressList.add("杜甫写给李白的");
        addressList.add("王维写给孟浩然的");
        ocrInfo.setAnswers(addressList);

        IGroup group = new BaiduGroup();
        String result = group.group(ocrInfo);

        System.out.println(result);
    }
}
