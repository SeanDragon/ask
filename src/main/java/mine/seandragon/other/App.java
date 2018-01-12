package mine.seandragon.other;

import mine.seandragon.other.adb.ScPhone;
import mine.seandragon.other.group.BaiduGroup;
import mine.seandragon.other.group.BingGroup;
import mine.seandragon.other.group.IGroup;
import mine.seandragon.other.img.ImageCut;
import mine.seandragon.other.ocr.BaiduOcr;
import mine.seandragon.other.ocr.IOcr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        log.info("回车一次，干一次！");
        while (true) {
            String str = bf.readLine();
            if ("exit".equals(str)) {
                break;
            } else {
                if (str.length() == 0) {
                    try {
                        run();
                    } catch (Throwable ignore) {
                    }
                } else {
                    Thread.sleep(10);
                }
            }
        }
    }


    public static void run() {
        //1 拉取截图
        long begin = System.currentTimeMillis();
        File scFile = ScPhone.sc();
        if (scFile == null) {
            System.err.println("获取手机截图失败");
            return;
        }
        log.info("获取手机截图成功");
        log.info(System.currentTimeMillis() - begin + "");
        // File scFile = new File("D:\\Administrator\\Pictures\\ocr\\1.png");

        String scFilePath = scFile.getAbsolutePath();

        //2 截图剪切
        try {
            int x = 100, y = 300, width = 900, height = 1050;
            ImageCut.cutImage(scFilePath, x, y, width, height);
            log.info("图片剪切成功");
            log.info(System.currentTimeMillis() - begin + "");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("图片剪切失败");
            return;
        }

        //3 图片识别出OcrInfo(question，answers)
        begin = System.currentTimeMillis();
        IOcr ocr = new BaiduOcr();
        OcrInfo ocrInfo;
        try {
            ocrInfo = ocr.get(scFilePath);
            log.info("文本识别成功");
            log.info(ocrInfo + "");
            log.info(System.currentTimeMillis() - begin + "");
        } catch (OcrException e) {
            e.printStackTrace();
            System.err.println("文本识别失败");
            return;
        }

        //4 进行查询关联性
        begin = System.currentTimeMillis();
        IGroup bingGroup = new BingGroup();
        try {
            //5 推举答案
            String result = bingGroup.group(ocrInfo);
            log.info("必应答案是:\t" + result);
            log.info(System.currentTimeMillis() - begin + "");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("搜索引擎出问题");
            return;
        }

        begin = System.currentTimeMillis();
        IGroup baiduGroup = new BaiduGroup();
        try {
            //5 推举答案
            String result = baiduGroup.group(ocrInfo);
            log.info("百度答案是:\t" + result);
            log.info(System.currentTimeMillis() - begin + "");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("搜索引擎出问题");
            return;
        }
    }
}
