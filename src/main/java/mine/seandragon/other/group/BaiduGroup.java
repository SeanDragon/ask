package mine.seandragon.other.group;

import mine.seandragon.other.OcrInfo;
import mine.seandragon.other.search.BaiduSearch;
import mine.seandragon.other.search.ISearch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 18:05
 */
public class BaiduGroup implements IGroup {
    @Override
    public String group(OcrInfo ocrInfo) throws IOException {

        String question = ocrInfo.getQuestion();
        List<String> answers = ocrInfo.getAnswers();

        ISearch search = new BaiduSearch();

        Long countQ = search.search(question);
        int numOfAnswer = answers.size();
        long[] countQA = new long[numOfAnswer];
        long[] countA = new long[numOfAnswer];

        int maxIndex = 0;

        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            Long newCountQA = search.search(question + " " + answer);
            Long newCountA = search.search(answer);
            countQA[i]=newCountQA;
            countA[i]=newCountA;
        }

        float[] ans = new float[numOfAnswer];
        for (int i = 0; i < numOfAnswer; i++) {
            ans[i] = (float) countQA[i] / (float) (countQ * countA[i]);
            maxIndex = (ans[i] > ans[maxIndex]) ? i : maxIndex;
        }
        // //根据pmi值进行打印搜索结果
        // int[] rank = rank(ans);
        // for (int i : rank) {
        //     System.out.print(answers.get(i));
        //     System.out.print(" countQA:" + countQA[i]);
        //     System.out.print(" countA:" + countA[i]);
        //     System.out.println(" ans:" + ans[i]);
        // }
        //
        // System.out.println("--------最终结果-------");
        // System.out.println(answers.get(maxIndex));
        return answers.get(maxIndex);
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
}
