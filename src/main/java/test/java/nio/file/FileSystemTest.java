package test.java.nio.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.Set;
import static java.lang.System.out;

public class FileSystemTest {

    /**
     * 获取当前系统支持的所有FileAttributeView的名字
     */
    @Test
    public void supportedFileAttributeViews() {
        FileSystem fs = FileSystems.getDefault();
        Set<String> views = fs.supportedFileAttributeViews();
        // owner,dos,acl,basic,user
        out.println(String.join(",", views));
    }

    @Test
    public void getFileStores() {
        FileSystem fs = FileSystems.getDefault();
        // FileStore对应硬盘分区？？
        for (FileStore store : fs.getFileStores()) {
            // 判断分区是否支持某一view
            boolean supported = store.supportsFileAttributeView("basic");
            boolean supported1 = store.supportsFileAttributeView(BasicFileAttributeView.class);
            out.println(store.name() + "---" + supported + "---" + supported1);
        }
    }

    @Test
    public void getFileStore() {
        Path path = Paths.get("G:/IDEAWorkspace/JavaLibrary/README.md");
        try {
            // 获取 文件/路径 所在的分区
            FileStore store = Files.getFileStore(path);
            boolean supported = store.supportsFileAttributeView("basic");
            System.out.println(store.name() + " supportes basicView ? => " + supported);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void getRootDirectories() {
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        // C:\,D:\,E:\,F:\,G:\,H:\,
        dirs.forEach(dir -> System.out.print(dir + ","));
    }


}
