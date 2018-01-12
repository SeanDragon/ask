package mine.seandragon.other.ocr;

import com.baidu.aip.ocr.AipOcr;
import mine.seandragon.other.OcrException;
import mine.seandragon.other.OcrInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import pro.tools.data.text.ToolRegex;
import pro.tools.path.ToolPath;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 百度的实现
 *
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 13:46
 */
public class BaiduOcr implements IOcr {

    /**
     * 可以有多个，进行多样性替换
     * @return
     */
    private static String getAppId() {
        return "10679609";
    }

    public static String getApiKey() {
        return "2Qg9oWKel14W9Mh3KOWCPvhy";
    }

    public static String getSecretKey() {
        return "Und6tZBnKK7gS6ArohYhGo7yMZj45RB4";
    }

    @Override
    public OcrInfo get(String path) throws OcrException {
        try {
            byte[] imageByte = ToolPath.readBytes(Paths.get(path));
            return get(imageByte);
        } catch (IOException e) {
            throw new OcrException("读取byte出错", e);
        }
    }

    @Override
    public OcrInfo get(byte[] imageByte) throws OcrException {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(getAppId(), getApiKey(), getSecretKey());

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        JSONObject res = client.basicGeneral(imageByte, new HashMap<>(0));
        if (res.has("error_code")) {
            throw new OcrException(res.getString("error_msg"));
        }

        //{"log_id":4953091631178019775,"words_result":[{"words":"11.\u201c户枢不蠹\u201d的前一句是?"}],"words_result_num":1}
        //{"log_id":1144538630132035043,"words_result":[{"words":"10.标志是\u201c雪山\u201d和\u201c雄狮\u201d的好莱坞"},{"words":"电影公司分别是?"},{"words":"米高梅,派拉蒙"},{"words":"84"},{"words":"派拉蒙,狮利"},{"words":"48"},{"words":"梦工厂,派拉蒙"},{"words":"69"}],"words_result_num":8}

        OcrInfo info = new OcrInfo();
        boolean questionEd = false;
        StringBuilder question = new StringBuilder();
        List<String> answers = new ArrayList<>(3);

        JSONArray wordsResult = res.getJSONArray("words_result");

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
                    if (answers.size() < 4) {
                        answers.add(newWords);
                    } else {
                        break;
                    }
                }
            }
        }
        info.setQuestion(question.toString())
                .setAnswers(answers);

        return info;
    }
}
