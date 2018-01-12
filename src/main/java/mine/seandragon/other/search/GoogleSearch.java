package mine.seandragon.other.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pro.tools.http.okhttp.ToolHttpBuilder;
import pro.tools.http.okhttp.ToolSendHttp;
import pro.tools.http.pojo.HttpReceive;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;

/**
 * @author Administrator
 * <p>
 * Create By 2018-01-12 22:21
 */
public class GoogleSearch implements ISearch {
    @Override
    public Long search(String text) throws IOException {
        String path = "https://www.google.com/search?q=" + URLEncoder.encode(text, "UTF-8");

        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost",1080));
        ToolHttpBuilder.getDefaultBuilder().proxy(proxy);
        HttpReceive httpReceive = ToolSendHttp.get(path);

        // Connection connection = Jsoup.connect(path).proxy(proxy).timeout(99999);
        // Document document = connection.get();
        Document document = Jsoup.parse(httpReceive.getResponseBody());
        String content = document.getElementById("resultStats").text();

        if (content.contains("条结果")) {
            int index = content.lastIndexOf("条结果");
            content = content.substring(3, index - 2).replace(",", "");
            return Long.valueOf(content);
        }
        return null;
    }
}
