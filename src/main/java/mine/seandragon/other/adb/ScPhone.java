package mine.seandragon.other.adb;

import java.io.File;
import java.io.IOException;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 16:03
 */
public class ScPhone {
    /**
     * ABD_PATH此处应更改为自己的adb目录
     * HERO_PATH更改自己存放图片的地址
     */
    private static final String ADB_PATH = "D:\\Game\\微信跳一跳\\adb  ";
    private static final String HERO_PATH = "D:\\Administrator\\Pictures\\ocr";
    private static final Long MIN_IMAGE_SIZE = 1000L;

    public static File sc() {
        File curPhoto = new File(HERO_PATH, System.currentTimeMillis() + ".png");
        //截屏存到手机本地
        try {
            while (!curPhoto.exists() || curPhoto.length() < MIN_IMAGE_SIZE) {
                Process process = Runtime.getRuntime().exec(ADB_PATH
                        + " shell /system/bin/screencap -p /sdcard/screenshot.png");
                process.waitFor();
                // 将截图放在电脑本地
                process = Runtime.getRuntime().exec(ADB_PATH
                        + " pull /sdcard/screenshot.png " + curPhoto.getAbsolutePath());
                process.waitFor();

                process.destroy();
            }
            //返回当前图片名字
            return curPhoto;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
