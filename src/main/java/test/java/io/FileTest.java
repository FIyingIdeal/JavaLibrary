package test.java.io;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;

public class FileTest {

    @Test
    public void listFiles() {
        File file = new File("G:/a");

        // 获取所有文件
        Arrays.stream(file.listFiles()).forEach(
                f -> System.out.print(f.getName() + ", ")
        );

        System.out.println();
        System.out.println("============");

        // 使用FileFilter设置过滤条件
        FileFilter fileFilter = f -> f.isDirectory();
        Arrays.stream(file.listFiles(fileFilter)).forEach(
                file1 -> System.out.print(file1.getName() + ", ")
        );

        System.out.println();
        System.out.println("============");

        // 使用FilenameFilter设置过滤条件
        FilenameFilter filenameFilter = (f1, name) -> name.endsWith(".java");
        Arrays.stream(file.listFiles(filenameFilter)).forEach(
                file1 -> System.out.print(file1.getName() + ", ")
        );
    }
}
