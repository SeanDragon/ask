package mine.seandragon.other;

import mine.seandragon.other.group.BaiduGroup;
import mine.seandragon.other.group.IGroup;
import mine.seandragon.other.img.ImageCut;
import mine.seandragon.other.ocr.BaiduOcr;
import mine.seandragon.other.ocr.IOcr;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //1 拉取截图
        // File scFile = ScPhone.sc();
        // if (scFile == null) {
        //     System.err.println("获取手机截图失败");
        //     return;
        // }
        long begin = System.currentTimeMillis();
        File scFile = new File("D:\\SeanDragon\\Pictures\\OCR\\timg2.jpg");

        String scFilePath = scFile.getAbsolutePath();

        //2 截图剪切
        try {
            int x = 100, y = 300, width = 900, height = 1050;
            ImageCut.cutImage(scFilePath, x, y, width, height);
        } catch (IOException e) {
            System.err.println("图片剪切失败");
            return;
        }

        //3 图片识别出OcrInfo(question，answers)
        IOcr ocr = new BaiduOcr();
        OcrInfo ocrInfo;
        try {
            ocrInfo = ocr.get(scFilePath);
        } catch (OcrException e) {
            System.err.println("文本识别失败");
            return;
        }

        //4 进行查询关联性
        IGroup baiduGroup = new BaiduGroup();
        try {
            //5 推举答案
            String result = baiduGroup.group(ocrInfo);
            System.out.println(result);
        } catch (IOException e) {
            System.err.println("搜索引擎出问题");
            return;
        }

        System.out.println(System.currentTimeMillis() - begin);
        //5 推举答案 或者 自己在已经弹出浏览器中查找

    }
}
