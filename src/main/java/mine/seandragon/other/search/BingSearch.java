package mine.seandragon.other.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 17:05
 */
public class BingSearch implements ISearch {
    @Override
    public Long search(String text) throws IOException {
        String path = "https://www.bing.com/search?q=" + URLEncoder.encode(text, "UTF-8");

        Document document = Jsoup.parse(new URL(path), 2345);
        String content = document.getElementsByClass("sb_count").get(0).text();

        if (content.contains("条结果")) {
            int index = content.lastIndexOf("条结果");
            content = content.substring(0, index - 1).replace(",", "");
            return Long.valueOf(content);
        }
        return null;
    }
}
