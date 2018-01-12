package mine.seandragon.other;

import mine.seandragon.other.adb.ScPhone;
import mine.seandragon.other.img.ImageCut;
import mine.seandragon.other.ocr.BaiduOcr;
import mine.seandragon.other.ocr.IOcr;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //1 拉取截图
        File scFile = ScPhone.sc();
        if (scFile == null) {
            System.err.println("获取手机截图失败");
            return;
        }

        String scFilePath = scFile.getAbsolutePath();

        //2 截图剪切
        try {
            ImageCut.cutImage(scFilePath, 0, 0, 300, 1050);
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
        String question = ocrInfo.getQuestion();
        List<String> answers = ocrInfo.getAnswers();


        //5 推举答案 或者 自己在已经弹出浏览器中查找

    }
}
