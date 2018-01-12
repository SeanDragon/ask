package mine.seandragon.other;

import pro.tools.system.ToolOS;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLEncoder;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 15:11
 */
public class OpenQuestion {

    public static void search(String question) {
        try {
            String path = "http://www.baidu.com/s?tn=ichuner&lm=-1&word=" +
                    URLEncoder.encode(question, "gb2312") + "&rn=20";
            openUrl(path);
            path = "https://www.google.com/search?q=" + URLEncoder.encode(question, "gb2312");
            openUrl(path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void openUrl(String url) {
        try {
            //获取操作系统的名字
            String osName = ToolOS.getOsName();
            if (osName.startsWith("Mac OS")) {
                //苹果的打开方式
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", String.class);
                openURL.invoke(null, url);
            } else if (osName.startsWith("Windows")) {
                //windows的打开方式。
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (osName.startsWith("Linux")) {
                //Linux的打开方式
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(URI.create(url));
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
