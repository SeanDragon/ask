package mine.seandragon.other.search;

import java.io.IOException;

public interface ISearch {
    /**
     * 直接显示推荐答案
     * @param text 查询的文本
     * @return 答案
     * @throws IOException 异常
     */
    Long search(String text) throws IOException;
}
