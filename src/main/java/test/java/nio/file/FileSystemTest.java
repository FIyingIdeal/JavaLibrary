package test.java.nio.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.System.out;

/**
 * @author yanchao
 * @date 2018-5-8 10:39:00
 */
public class FileSystemTest {

    public Path getPath() {
        return Paths.get("F:/movie");
    }

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
        Path path = getPath();
        try {
            // 获取 文件/路径 所在的分区
            FileStore store = Files.getFileStore(path);
            boolean supported = store.supportsFileAttributeView("basic");
            System.out.println(store.name() + " supports basicView ? => " + supported);
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

    @Test
    public void newWatchService() throws Exception {
        System.out.println(isSuccess(getPath()));
    }

    @Test
    public void createFile() throws IOException {
        Path path = getPath().resolve("status.json");
        Files.deleteIfExists(path);
        Files.createFile(path);
    }

    public boolean isSuccess(Path path) throws TimeoutException, IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        boolean success = false;
        while (!success) {
            WatchKey key = watchService.poll(20, TimeUnit.SECONDS);
            if (key != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    // 只过滤status.json相关的事件
                    if ("status.json".equals(String.valueOf(event.context()))) {
                        // 文件创建
                        if (StandardWatchEventKinds.ENTRY_CREATE == event.kind()) {
                            System.out.println("status.json文件被创建");
                            try {
                                String status = getStatus(path.resolve("status.json"));
                                if ("success".equals(status)) {
                                    success = true;
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("创建文件未读取到内容");
                            }
                        }
                        // 文件修改
                        else if (StandardWatchEventKinds.ENTRY_MODIFY == event.kind()) {
                            System.out.println("status.json文件被修改");
                            String status = getStatus(path.resolve("status.json"));
                            if ("success".equals(status)) {
                                success = true;
                            }
                        }
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            } else {
                throw new TimeoutException("获取结果超时");
            }
        }
        return success;
    }

    @Test
    public void getStatusTest() throws IOException {
        Path path = Paths.get("F:/movie", "status.json");
        System.out.println(getStatus(path));
    }

    public String getStatus(Path path) throws IOException {
        String line = Files.newBufferedReader(path).lines().findFirst().get();
        return new ObjectMapper().readTree(line).get("status").asText();
    }


}
