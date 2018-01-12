package mine.seandragon.other.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 16:13
 */
public class BaiduSearch implements ISearch {

    @Override
    public Long search(String text) throws IOException {
        String path = "http://www.baidu.com/s?ie=UTF-8&word=" + URLEncoder.encode(text, "UTF-8");

        Document document = Jsoup.parse(new URL(path), 2000);
        String content = document.getElementsByClass("nums").get(0).text();

        if (content.contains("百度为您找到相关结果约")) {
            int start = content.indexOf("百度为您找到相关结果约") + 11;
            int end = content.lastIndexOf("个");
            content = content.substring(start, end).replace(",", "");
            return Long.valueOf(content);
        }
        return null;
    }
}
