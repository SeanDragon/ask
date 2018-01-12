package mine.seandragon.other;

import mine.seandragon.other.group.BaiduGroup;
import mine.seandragon.other.group.BingGroup;
import mine.seandragon.other.group.GoogleGroup;
import mine.seandragon.other.group.IGroup;
import mine.seandragon.other.ocr.BaiduOcr;
import mine.seandragon.other.ocr.IOcr;
import mine.seandragon.other.search.GoogleSearch;
import mine.seandragon.other.search.ISearch;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 16:14
 */
public class SearchTest {
    @Test
    public void test1() throws IOException {
        // ISearch search = new BaiduSearch("");

        OcrInfo info = new OcrInfo();
        // info.setQuestion("强者越强，弱者越弱” 的现象，社会学家称之为什么？");
        info.setQuestion("标志是 “雪山” 和“雄狮”的好莱坞电影公司分别是？");
        // info.setQuestion("几年前流行的 “冰桶挑战” 是为了呼吁社会关爱 “渐冻人症患者”，请问“世界渐冻人日” 是哪一天？");
        List<String> answers = new ArrayList<>(4);
        // answers.add("马太效应");
        // answers.add("二八定则");
        // answers.add("蝴蝶效应");
        // answers.add("皮格马利翁效应");
        answers.add("米高梅，派拉蒙");
        answers.add("派拉蒙，狮利");
        answers.add("梦工场，派拉蒙");
        answers.add("派拉蒙，米高梅");
        // answers.add("4 月 14 日");
        // answers.add("6 月 21 日");
        // answers.add("3 月 25 日");
        info.setAnswers(answers);

        IGroup group = new BaiduGroup();
        String a = group.group(info);
        System.out.println(a);
    }


    /**
     * @param floats
     *         pmi值
     *
     * @return 返回排序的rank
     */
    private static int[] rank(float[] floats) {
        int[] rank = new int[floats.length];
        float[] f = Arrays.copyOf(floats, floats.length);
        Arrays.sort(f);
        for (int i = 0; i < floats.length; i++) {
            for (int j = 0; j < floats.length; j++) {
                if (f[i] == floats[j]) {
                    rank[i] = j;
                }
            }
        }
        return rank;
    }

    @Test
    public void test2() throws IOException {
        String path = "http://www.baidu.com/s?word=" +
                URLEncoder.encode("标志是 “雪山” 和“雄狮”的好莱坞电影公司分别是？", "gb2312");
        // HttpReceive httpReceive = ToolSendHttp.post(path);
        // String responseBody = httpReceive.getResponseBody();
        // boolean result = responseBody.contains("百度为您找到相关结果");
        // System.out.println(result);

        Connection connect = Jsoup.connect(path);
        Elements nums = connect.get().getElementsByClass("nums");
        Element element = nums.get(0);
        String text = element.text();

        if (text.contains("百度为您找到相关结果约")) {
            int start = text.indexOf("百度为您找到相关结果约") + 11;
            text = text.substring(start);
            int end = text.indexOf("个");
            text = text.substring(0, end);
        }
    }

    @Test
    public void test3() {

        String question = "";
        List<String> answers = new ArrayList<>(4);
        answers.add("米高梅，派拉蒙");
        answers.add("派拉蒙，狮利");
        answers.add("梦工场，派拉蒙");
        answers.add("派拉蒙，米高梅");

        //搜索
        long countQ = 1;
        int numOfAnswer = answers.size() > 3 ? 4 : answers.size();
        long[] countQA = new long[numOfAnswer];
        long[] countA = new long[numOfAnswer];

        int maxIndex = 0;


        //TODO
        countQ = 106000;

        countQA[0] = 103000;
        countA[0] = 649000;

        countQA[1] = 102000;
        countA[1] = 411000;

        countQA[2] = 103000;
        countA[2] = 411000;

        countQA[3] = 105000;
        countA[3] = 61500;


        float[] ans = new float[numOfAnswer];
        for (int i = 0; i < numOfAnswer; i++) {
            ans[i] = (float) countQA[i] / (float) (countQ * countA[i]);
            maxIndex = (ans[i] > ans[maxIndex]) ? i : maxIndex;
        }
        //根据pmi值进行打印搜索结果
        int[] rank = rank(ans);
        for (int i : rank) {
            System.out.print(answers.get(i));
            System.out.print(" countQA:" + countQA[i]);
            System.out.print(" countA:" + countA[i]);
            System.out.println(" ans:" + ans[i]);
        }

        System.out.println("--------最终结果-------");
        System.out.println(answers.get(maxIndex));
    }

    @Test
    public void test4() throws Exception {
        String path = "https://www.baidu.com/s?ie=UTF-8&wd=" + URLEncoder.encode("标志是 “雪山” 和“雄狮”的好莱坞电影公司分别是?", "UTF-8");
        System.out.println(path);
        Connection connect = Jsoup.connect(path);
        Elements nums = connect.get().getElementsByClass("nums");
        Element element = nums.get(0);
        String content = element.text();

        if (content.contains("百度为您找到相关结果约")) {
            int start = content.indexOf("百度为您找到相关结果约") + 11;
            content = content.substring(start);
            int end = content.indexOf("个");
            content = content.substring(0, end).replace(",", "");
            System.out.println(content);
        }
    }

    @Test
    public void test5() throws IOException {
        String scFilePath = "D:\\Administrator\\Pictures\\ocr\\1.png";
        //3 图片识别出OcrInfo(question，answers)
        long begin = System.currentTimeMillis();
        IOcr ocr = new BaiduOcr();
        OcrInfo ocrInfo;
        try {
            ocrInfo = ocr.get(scFilePath);
            System.out.println("文本识别成功");
            System.out.println(ocrInfo);
            System.out.println(System.currentTimeMillis() - begin);
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
            System.out.println("必应答案是:\t" + result);
            System.out.println(System.currentTimeMillis() - begin);
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
            System.out.println("百度答案是:\t" + result);
            System.out.println(System.currentTimeMillis() - begin);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("搜索引擎出问题");
            return;
        }

        begin = System.currentTimeMillis();
        IGroup googleGroup = new GoogleGroup();
        try {
            //5 推举答案
            String result = googleGroup.group(ocrInfo);
            System.out.println("谷歌答案是:\t" + result);
            System.out.println(System.currentTimeMillis() - begin);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("搜索引擎出问题");
            return;
        }
    }

    @Test
    public void test6() throws IOException {
        ISearch search = new GoogleSearch();
        Long a = search.search("a");
        System.out.println(a);
    }
}
