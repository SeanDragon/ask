package mine.seandragon.other.search;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 16:13
 */
public class BaiduSearch implements ISearch {

    public String question;

    public BaiduSearch(String question) {
        this.question = question;
    }

    @Override
    public long search(String question) throws IOException {
        String path = "http://www.baidu.com/s?tn=ichuner&lm=-1&word=" +
                URLEncoder.encode(question, "UTF-8") +
                // question +
                "&rn=1";

        Connection connect = Jsoup.connect(path);
        Elements nums = connect.get().getElementsByClass("nums");
        Element element = nums.get(0);
        String text = element.text();

        if (text.contains("百度为您找到相关结果约")) {
            int start = text.indexOf("百度为您找到相关结果约") + 11;
            text = text.substring(start);
            int end = text.indexOf("个");
            text = text.substring(0, end).replace(",", "");
            return Long.valueOf(text);
        }
        return 0;

        // boolean findIt = false;
        // String line = null;
        // while (!findIt) {
        //     URL url = new URL(path);
        //     BufferedReader breaded = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        //     while ((line = breaded.readLine()) != null) {
        //         if (line.contains("百度为您找到相关结果约")) {
        //             findIt = true;
        //             int start = line.indexOf("百度为您找到相关结果约") + 11;
        //
        //             line = line.substring(start);
        //             int end = line.indexOf("个");
        //             line = line.substring(0, end);
        //             break;
        //         }
        //     }
        //     //FIXME我加的
        //     breaded.close();
        // }
        // line = line.replace(",", "");
        // return Long.valueOf(line);
    }

    @Override
    public Object call() throws Exception {
        return search(question);
    }
}
