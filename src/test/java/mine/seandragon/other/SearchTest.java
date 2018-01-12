package mine.seandragon.other;

import mine.seandragon.other.search.BaiduSearch;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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


        String question = info.getQuestion();

        //搜索
        long countQuestion = 1;
        int numOfAnswer = answers.size() > 3 ? 4 : answers.size();
        long[] countQA = new long[numOfAnswer];
        long[] countAnswer = new long[numOfAnswer];

        int maxIndex = 0;

        ISearch[] searchQA = new BaiduSearch[numOfAnswer];
        ISearch[] searchAnswers = new BaiduSearch[numOfAnswer];
        FutureTask[] futureQA = new FutureTask[numOfAnswer];
        FutureTask[] futureAnswers = new FutureTask[numOfAnswer];
        FutureTask futureQuestion = new FutureTask<Long>(new BaiduSearch(question));
        new Thread(futureQuestion).start();
        for (int i = 0; i < numOfAnswer; i++) {
            searchQA[i] = new BaiduSearch(question + " " + answers.get(i));
            searchAnswers[i] = new BaiduSearch(answers.get(i));

            futureQA[i] = new FutureTask<Long>(searchQA[i]);
            futureAnswers[i] = new FutureTask<Long>(searchAnswers[i]);
            new Thread(futureQA[i]).start();
            new Thread(futureAnswers[i]).start();
        }
        try {
            while (!futureQuestion.isDone()) {
            }
            countQuestion = (Long) futureQuestion.get();
            for (int i = 0; i < numOfAnswer; i++) {
                while (true) {
                    if (futureAnswers[i].isDone() && futureQA[i].isDone()) {
                        break;
                    }
                }
                countQA[i] = (Long) futureQA[i].get();
                countAnswer[i] = (Long) futureAnswers[i].get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        float[] ans = new float[numOfAnswer];
        for (int i = 0; i < numOfAnswer; i++) {
            ans[i] = (float) countQA[i] / (float) (countQuestion * countAnswer[i]);
            maxIndex = (ans[i] > ans[maxIndex]) ? i : maxIndex;
        }
        //根据pmi值进行打印搜索结果
        int[] rank = rank(ans);
        for (int i : rank) {
            System.out.print(answers.get(i));
            System.out.print(" countQA:" + countQA[i]);
            System.out.print(" countAnswer:" + countAnswer[i]);
            System.out.println(" ans:" + ans[i]);
        }

        System.out.println("--------最终结果-------");
        System.out.println(answers.get(maxIndex));
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
        String path = "http://www.baidu.com/s?tn=ichuner&lm=-1&word=" +
                URLEncoder.encode("标志是 “雪山” 和“雄狮”的好莱坞电影公司分别是？", "gb2312") + "&rn=1";
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
}
