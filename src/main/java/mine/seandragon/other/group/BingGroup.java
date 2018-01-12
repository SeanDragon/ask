package mine.seandragon.other.group;

import mine.seandragon.other.OcrInfo;
import mine.seandragon.other.search.BingSearch;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Administrator
 * <p>
 * Create By 2018-01-12 21:29
 */
public class BingGroup implements IGroup {
    @Override
    public String group(OcrInfo ocrInfo) throws IOException {

        String question = ocrInfo.getQuestion();
        List<String> answers = ocrInfo.getAnswers();

        Long countQ;
        int numOfAnswer = answers.size();
        long[] countQA = new long[numOfAnswer];
        long[] countA = new long[numOfAnswer];

        FutureTask<Long>[] futureQA = new FutureTask[numOfAnswer];
        FutureTask<Long>[] futureAnswers = new FutureTask[numOfAnswer];
        FutureTask<Long> futureQuestion = new FutureTask(() -> new BingSearch().search(question));

        new Thread(futureQuestion).start();
        for (int i = 0; i < numOfAnswer; i++) {
            String answer = answers.get(i);
            futureQA[i] = new FutureTask<>(() -> new BingSearch().search(question + " " + answer));
            futureAnswers[i] = new FutureTask<>(() -> new BingSearch().search(answer));
            new Thread(futureQA[i]).start();
            new Thread(futureAnswers[i]).start();
        }
        try {
            while (!futureQuestion.isDone()) {
            }
            countQ = futureQuestion.get();
            for (int i = 0; i < numOfAnswer; i++) {
                while (!futureQA[i].isDone()) {
                }
                countQA[i] = futureQA[i].get();
                while (!futureAnswers[i].isDone()) {
                }
                countA[i] = futureAnswers[i].get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("线程执行失败");
            return null;
        }

        float[] ans = new float[numOfAnswer];
        int maxIndex = 0;
        int minIndex = 0;
        for (int i = 0; i < numOfAnswer; i++) {
            ans[i] = (float) countQA[i] / (float) (countQ * countA[i]);
            maxIndex = (ans[i] > ans[maxIndex]) ? i : maxIndex;
            minIndex = (ans[i] < ans[minIndex]) ? i : minIndex;
        }
        if (question.contains("不是")) {
            return answers.get(minIndex);
        } else {
            return answers.get(maxIndex);
        }
    }
}
