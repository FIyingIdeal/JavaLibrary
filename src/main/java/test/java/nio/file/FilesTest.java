package test.java.nio.file;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * java.nio.file.Files 测试
 * @datetime 2018-4-6 13:41:04
 * @reference http://www.cnblogs.com/IcanFixIt/p/4838375.html
 */

public class FilesTest {

    Path path = Paths.get("G:/IDEAWorkspace/JavaLibrary/JavaLibrary.iml");

    @Test
    public void exists() {
        boolean b = Files.exists(path);
        System.out.println(b);
    }

    /**
     * notExists()方法不是exists()方法的补充，无法确定文件是否存在的话，这个exists和notExists方法都会返回false
     */
    @Test
    public void notExists() {
        boolean b = Files.notExists(path);
        System.out.println(b);
    }

    /**
     * 文件权限检查
     */
    @Test
    public void rwer() {
        boolean is_readable = Files.isReadable(path);
        boolean is_writable = Files.isWritable(path);
        boolean is_executable = Files.isExecutable(path);
        boolean is_regular = Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);

        if ((is_readable) && (is_writable) && (is_executable) && (is_regular)) {
            System.out.println("The checked file is accessible!");
        } else {
            System.out.println("The checked file is not accessible!");
        }
    }

    /**
     * 两Path是否指向同一个文件或路径，有一个前提就是两个Path指定的路径或文件必须真实存在
     */
    @Test
    public void isSameFile() {
        Path path1 = Paths.get("G:/IDEAWorkspace/JavaLibrary/JavaLibrary.iml");
        Path path2 = Paths.get("G:/IDEAWorkspace/JavaLibrary", "../JavaLibrary/JavaLibrary.iml");
        try {
            boolean b = Files.isSameFile(path1, path2);
            System.out.println("is same file : " + b);
        } catch (IOException e) {}
    }

    /**
     * 创建目录
     */
    @Test
    public void createDirectory() {
        Path path = Paths.get("G:/123");
        try {
            // 如果文件已经存在的话将会抛出异常
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录中的内容
     */
    @Test
    public void newDirectoryStream() {
        Path path = Paths.get("G:/a");
        // 获取文件所有内容
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            ds.forEach(file -> System.out.print(file + ","));
        } catch (IOException e) {}

        System.out.println();
        System.out.println("------------------------");

        // 使用正则表达式对文件进行过滤
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*.{sql,java}")) {
            ds.forEach(file -> System.out.print(file + ","));
        } catch (IOException e) {}

        System.out.println();
        System.out.println("------------------------");

        // 自定义过滤规则
        DirectoryStream.Filter<Path> dir_filter =
                path1 -> Files.isDirectory(path1);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, dir_filter)) {
            ds.forEach(file -> System.out.print(file + ","));
        } catch (IOException e) {}
    }

    @Test
    public void size() throws Exception {
        System.out.println(Files.size(path));
    }

    /**
     * 创建 删除文件
     */
    @Test
    public void createFile() {
        Path newFile = Paths.get("G:/123", "aaa/bb/test.txt");
        try {
            // 如果文件不存在则创建
            if (Files.notExists(newFile)) {
                // 先判断文件夹是否存在
                if (Files.notExists(newFile.getParent())) {
                    Files.createDirectories(newFile.getParent());
                }
                Files.createFile(newFile);
            }
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 删除文件
            boolean b = Files.deleteIfExists(newFile);
            System.out.println(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     */
    @Test
    public void write() {
        Path path = Paths.get("G:/123", "test.txt");
        Charset charset = Charset.forName("UTF-8");
        List<String> lines = new ArrayList<>();
        lines.add("\n");
        lines.add("Rome Masters - 5 titles in 6 years");
        lines.add("Monte Carlo Masters - 7 consecutive titles (2005-2011)");
        lines.add("Australian Open - Winner 2009");
        lines.add("Roland Garros - Winner 2005-2008, 2010, 2011");
        lines.add("Wimbledon - Winner 2008, 2010");
        lines.add("US Open - Winner 2010");
        try {
            if (Files.notExists(path)) {
                path = Files.createFile(path);
            }
            Files.write(path, lines, charset, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     */
    @Test
    public void readAllLines() {
        Path path = Paths.get("G:/123", "test.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            lines.stream().forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void newBufferedWriter() {
        Path path = Paths.get("G:/123", "test.txt");
        try (BufferedWriter bw = Files.newBufferedWriter(path,Charset.forName("UTF-8"), StandardOpenOption.APPEND)) {
            bw.write("newBufferedWriter test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void newBufferedReader() {
        Path path = Paths.get("G:/123", "test.txt");
        try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            br.lines().forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建删除临时目录
     */
    @Test
    public void createTempDirectory() throws Exception {
        System.out.println("系统临时目录：" + System.getProperty("java.io.tmpdir"));
        Path baseDir = Paths.get("G:/123");
        try {
            if (Files.notExists(baseDir)) {
                baseDir = Files.createDirectory(baseDir);
            }
            Path tempPath = Files.createTempDirectory(baseDir, "perfix_");
            System.out.println("TMP: " + tempPath.toString());

            TimeUnit.SECONDS.sleep(5);

            // 使用File#deleteOnExit()删除临时文件夹
            /*if (Files.exists(tempPath)) {
                File file = tempPath.toFile();
                file.deleteOnExit();
                Thread.sleep(3000);
            }*/

            // 使用Shutdown-hook删除临时文件夹，在JVM关闭的时候回执行Thread的run()方法
            Runtime.getRuntime().addShutdownHook(new Thread("shutdownHook_Thread") {
                @Override
                public void run() {
                    System.out.println("Deleting the temporary directory...");
                    // 如果文件夹内有内容的话，需要遍历删除里边的文件，然后才能删除空文件夹
                    deleteDirectory(tempPath);
                    System.out.println("Shutdown hook completed...");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义删除文件夹的方法
     * @param deleteDir
     * @return
     */
    public boolean deleteDirectory(Path deleteDir) {
        if (!Files.isDirectory(deleteDir)) {
            try {
                Files.delete(deleteDir);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(deleteDir)) {
            paths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    deleteDirectory(path);
                } else {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("文件【" + path.toString() + "】删除失败");
                    }
                }
            });
            // 删除文件夹本身
            Files.delete(deleteDir);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Test
    public void deleteDirectoryTest() {
        Path deleteDir = Paths.get("G:/123", "tmpdir");
        boolean b = deleteDirectory(deleteDir);
        System.out.println("文件夹删除状态：" + b);
    }

    /**
     * 使用DELETE_ON_CLOSE在流关闭的时候删除临时文件:
     * 1. 先创建文件；
     * 2. 写入文件，并在创建流的时候执行在流关闭的时候删除文件
     */
    @Test
    public void createTempFile_delete_on_close() {
        Path baseDir = Paths.get("G:/123");
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile(baseDir, "tmp_", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = Files.newBufferedWriter(tempFile, StandardOpenOption.DELETE_ON_CLOSE)) {
            bw.write("234");
            TimeUnit.SECONDS.sleep(10);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用delete()删除目录/文件，如果删除失败，则会抛出以下异常：
     *      NoSuchFileException (i路径不存在)
     *      DirectoryNotEmptyException (i目录不为空)
     *      IOException (输入输出错误发生)
     *      SecurityException (无删除权限)
     */
    @Test
    public void delete() {
        Path path = Paths.get("G:/123", "eee.txt");
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Files.deleteIfExists()方法只有在文件存在的时候删除，这就意味着如果文件不存在
     * （代替了抛出NoSuchFileException异常）不能删除的情况下返回boolean值false
     */
    @Test
    public void deleteIfExits() {
        Path path = Paths.get("G:/123", "eee.txt");
        try {
            boolean b = Files.deleteIfExists(path);
            System.out.println(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件拷贝，支持两个Path的拷贝以及输入输出流与Path的拷贝
     */
    @Test
    public void copy() {
        Path copy_from = Paths.get("G:/123/test.txt");
        Path copy_to = Paths.get("G:/123/456/abc.txt");
        try {
            if (Files.notExists(copy_to.getParent())) {
                Files.createDirectories(copy_to.getParent());
            }
            Files.copy(copy_from, copy_to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动文件，还可以将文件重命名
     */
    @Test
    public void move() {
        Path move_from = Paths.get("G:/123/test.txt");
        // 使用Path#resolve(Path)方法来拼接路径
        Path move_to = Paths.get("G:/123/456").resolve(move_from.getFileName());
        // 使用Path#resolveSibling()方法进行文件名替换
        Path changeName = move_to.resolveSibling("test1.txt");
        try {
            if (!Files.isDirectory(move_to)) {
                if (Files.notExists(move_to.getParent())) {
                    Files.createDirectories(move_to.getParent());
                }
            } else if (Files.notExists(move_to)) {
                Files.createDirectories(move_to);
            }
            Files.move(move_from, move_to);
            TimeUnit.SECONDS.sleep(3);
            // 进行文件名替换
            Files.move(move_to, changeName);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
