package mine.seandragon.other;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import pro.tools.data.text.ToolRegex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 14:05
 */
public class JsonTest {
    @Test
    public void test1() {
        String json = "{\"log_id\":4953091631178019775,\"words_result\":[{\"words\":\"11.\\u201c户枢不蠹\\u201d的前一句是?\"}],\"words_result_num\":1}";
        json = "{\"log_id\":1144538630132035043,\"words_result\":[{\"words\":\"10.标志是\\u201c雪山\\u201d和\\u201c雄狮\\u201d的好莱坞\"},{\"words\":\"电影公司分别是?\"},{\"words\":\"米高梅,派拉蒙\"},{\"words\":\"84\"},{\"words\":\"派拉蒙,狮利\"},{\"words\":\"48\"},{\"words\":\"梦工厂,派拉蒙\"},{\"words\":\"69\"}],\"words_result_num\":8}";
        // JSONObject jsonObject = new JSONObject(json);
        // JSONArray wordsResult = jsonObject.getJSONArray("words_result");
        // JSONObject wordsResult1 = wordsResult.getJSONObject(0);
        // String words = wordsResult1.getString("words");

        OcrInfo info = new OcrInfo();
        boolean questionEd = false;
        StringBuilder question = new StringBuilder();
        List<String> answers = new ArrayList<>(4);

        JSONArray wordsResult = new JSONObject(json).getJSONArray("words_result");
        int wordsResultLen = wordsResult.length();
        for (int i = 0; i < wordsResultLen; i++) {
            String newWords = wordsResult.getJSONObject(i).getString("words");
            if (!ToolRegex.isInteger(newWords)) {
                if (!questionEd) {
                    question.append(newWords);
                    if (newWords.endsWith("?")) {
                        questionEd = true;
                    }
                } else {
                    answers.add(newWords);
                }
            }
        }
        info.setQuestion(question.toString())
                .setAnswers(answers);

        System.out.println(question);
        System.out.println(answers);
    }
}
