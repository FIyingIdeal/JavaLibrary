package test.java.nio.file.attribute;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * Basic视图：大部分文件系统的实现支持一些通用的属性，例如大小，创建时间，上次访问时间，最后修改时间等等。
 */
public class BasicFileAttributesTest {

    BasicFileAttributes attr = null;
    Path path = Paths.get("G:/IDEAWorkspace/JavaLibrary/README.md");

    @Test
    public void test() {

        try {
            attr = Files.readAttributes(path, BasicFileAttributes.class);
            // 两种表达方式一致
            // attr = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File size: " + attr.size());
        System.out.println("File creation time: " + attr.creationTime());
        System.out.println("File was last accessed at: " + attr.lastAccessTime());
        System.out.println("File was last modified at: " + attr.lastModifiedTime());

        System.out.println("Is directory? " + attr.isDirectory());
        System.out.println("Is regular file? " + attr.isRegularFile());
        System.out.println("Is symbolic link? " + attr.isSymbolicLink());
        System.out.println("Is other? " + attr.isOther());

        // 通过Files.getAttribute()可以获取单一属性
        try {
            long size = (long)Files.getAttribute(path, "basic:size");
            System.out.println("Files.getAttribute(basic:size) is " + size);
        } catch (IOException e) {}
        /* 其他的基本属性列表为：
            lastModifiedTime
            lastAccessTime
            creationTime
            size
            isRegularFile
            isDirectory
            isSymbolicLink
            isOther
            fileKey*/

    }

    @Test
    public void Files_getFileAttributeView() {
        FileTime time = FileTime.fromMillis(System.currentTimeMillis());
        try {
            Files.getFileAttributeView(path, BasicFileAttributeView.class).setTimes(
                    time, time, time);
        } catch (IOException e) {}
    }
}
