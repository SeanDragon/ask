package mine.seandragon.other.img;

import pro.tools.data.image.ToolImageTailor;

import java.io.IOException;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 15:50
 */
public class ImageCut {
    /**
     * 剪切图片至指定大小和位置
     * 会替换原有文件
     *
     * @param imagePath 图片的路径
     * @param x 切割的x坐标
     * @param y 切割的y坐标
     * @param width 切割的宽度
     * @param height 切割的高度
     * @throws IOException 抛出异常，外面统一处理
     */
    public static void cutImage(String imagePath, int x, int y, int width, int height) throws IOException {
        // int x = 100, y = 300, width = 900, height = 900;
        // height = 1050;
        ToolImageTailor.cut(imagePath, imagePath, x, y, width, height);
    }
}
