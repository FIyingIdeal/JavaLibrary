package apache_commons.commons_io;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yanchao
 * @date 2018/4/18 11:53
 */
public class FileUtilsTest {

    @Test
    public void copyFile() {
        // String sourceFilePath = "https://npm.taobao.org/mirrors/npm/v1.1.0-alpha-6.zip";
        String sourceFilePath = "D:/hiynn.cer";
        String targetFilePath = "F:/aa/hiynn.cer";
        try {
            URI uri = new URI(sourceFilePath);
            if ("file".equals(uri.getScheme())) {
                FileUtils.copyFile(new File(uri), new File(targetFilePath));
                System.out.println("文件拷贝完成！");
            } else {
                FileUtils.copyFile(new File(sourceFilePath), new File(targetFilePath));
                System.out.println("文件拷贝完成！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
