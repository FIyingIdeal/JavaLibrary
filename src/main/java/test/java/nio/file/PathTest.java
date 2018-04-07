package test.java.nio.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

public class PathTest {

    @Test
    public void PathsGet() {
        Path path = Paths.get("G:/IDEAWorkspace/JavaLibrary/README.md");
        System.out.println(path);

        // Paths.get()本质上是使用FileSystems.getDefault().getPath()来获取Path的
        Path path1 = FileSystems.getDefault().getPath(
                "G:/IDEAWorkspace/JavaLibrary/README.md");

        Path path2 = Paths.get(System.getProperty("user.home"));
        System.out.println(path2);
    }

    public Path getPath(String... more) {
        return Paths.get("G:/IDEAWorkspace/JavaLibrary/README.md", more);
    }

    @Test
    public void getFileName() {
        Path path = getPath();
        // README.md
        System.out.println("file name is " + path.getFileName());
    }

    @Test
    public void getRoot() {
        Path path = getPath();
        // G:\
        System.out.println("root name is " + path.getRoot());
    }

    @Test
    public void getParent() {
        Path path = getPath();
        // G:\IDEAWorkspace\JavaLibrary
        System.out.println("parent is " + path.getParent());
    }

    @Test
    public void getNameCount() {
        Path path = getPath();
        // 获取路径层级的个数
        int count = path.getNameCount();
        System.out.println("Name count is " + count);
        for (int i = 0; i < count; i++) {
            // getName(int) 获取每个子路径
            Path childPath = path.getName(i);
            System.out.println("Name element " + i + " is " + childPath);
        }
        // Name element 0 is IDEAWorkspace
        // Name element 1 is JavaLibrary
        // Name element 2 is README.md
    }

    @Test
    public void subpath() {
        Path path = getPath();
        // IDEAWorkspace\JavaLibrary
        System.out.println("subpath(0,2) is " + path.subpath(0, 2));
    }

    @Test
    public void toStringTest() {
        System.out.println(getPath().toString());
    }

    @Test
    public void toUri() {
        URI path_to_uri = getPath().toUri();
        // file:///G:/IDEAWorkspace/JavaLibrary/README.md
        System.out.println("path to URI is " + path_to_uri);
    }

    @Test
    public void toAbsolutePath() {
        Path path_to_absolute_path = getPath().toAbsolutePath();
        // G:\IDEAWorkspace\JavaLibrary\README.md
        System.out.println("path to absolute path is " + path_to_absolute_path);

        // AbsolutePath当前工作路径加上相对路径，直接组合在一起
        // 仍然只是字符串的组合，不管路径是否是真的路径还是软链接（符号链接）
        // 甚至不管路径是否存在，都无所谓
        Path path1 = Paths.get("../test").toAbsolutePath();
        Path path2 = Paths.get("./../test").toAbsolutePath();
        // G:\IDEAWorkspace\JavaLibrary\..\test
        System.out.println("absolutePath is " + path1);
        // G:\IDEAWorkspace\JavaLibrary\.\..\test
        System.out.println("absolutePath is " + path2);
        // false  这两个路径都指向了统一个文件，只是表达方式不一样，但两者toAbsolutePath()是不equals的
        // 其本质是两个Path对应的字符串的比较
        System.out.println(path2.equals(path1));
    }

    // 获取真实路径，如果路径在文件系统中无法找到的话，会抛出异常
    @Test
    public void toRealPath() {
        try {
            // RealPath就不一样，它不仅是绝对路径，而且是“真正的”绝对路径，不只是把当前路径加相对路径那么简单
            // 如果路径不存在，则直接抛出NoSuchFileException异常
            Path path_to_real_path = getPath().toRealPath(LinkOption.NOFOLLOW_LINKS);
            // G:\IDEAWorkspace\JavaLibrary\README.md
            System.out.println("path to real path is " + path_to_real_path);

            Path path1 = getPath("../").toRealPath(LinkOption.NOFOLLOW_LINKS);
            Path path2 = getPath("./../").toRealPath(LinkOption.NOFOLLOW_LINKS);
            // G:\IDEAWorkspace\JavaLibrary
            System.out.println("realPath1 is " + path1);
            // G:\IDEAWorkspace\JavaLibrary
            System.out.println("realPath2 is " + path2);
            // true toRealPath()会将路径进行”计算“，所以虽然两种表达形式不同，但指向同一个路径的两个Path
            // 经过toRealPath()转换以后是一样的。
            System.out.println(path1.equals(path2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toFile() {
        File path_to_file = getPath().toFile();
        System.out.println("path to file is " + path_to_file);

        Path file_to_path = path_to_file.toPath();
        System.out.println("file to path is " + file_to_path);
    }

    // 合并两个路径
    @Test
    public void resolve() {
        Path base = getPath();
        Path path1 = Paths.get("../test.txt");
        path1 = base.resolve(path1); // 参数可以是一个String或Path对象
        // G:\IDEAWorkspace\JavaLibrary\README.md\..\test.txt
        System.out.println(path1);
    }

    // 根据给定的路径去替换当前的路径
    @Test
    public void resolveSibling() {
        Path base = getPath();
        Path path1 = base.resolveSibling("test.txt");
        // G:\IDEAWorkspace\JavaLibrary\test.txt
        System.out.println(path1);

        base = Paths.get("G:/IDEAWorkspace/JavaLibrary");
        path1 = base.resolveSibling("abc/test.txt");
        // G:\IDEAWorkspace\abc\test.txt
        System.out.println(path1);

        base = Paths.get("G:/IDEAWorkspace/JavaLibrary/");
        path1 = base.resolveSibling("test.txt");
        // G:\IDEAWorkspace\test.txt
        System.out.println(path1);
    }

    @Test
    public void relativize() {
        Path path1 = Paths.get("1.txt");
        Path path2 = Paths.get("2.txt");
        // 两个文件在同一个目录下
        // ..\2.txt
        Path path = path1.relativize(path2);
        System.out.println(path);


        path1 = Paths.get("/root/1.txt");
        path2 = Paths.get("/root/first/2.txt");
        // 有相同的根路径
        // ../first/2.txt
        System.out.println(path1.relativize(path2));
        // ../../1.txt
        System.out.println(path2.relativize(path1));

        // 两个路径必须有相同的根路径，否则的话，将会抛出java.lang.IllegalArgumentException: 'other' is different type of Path
    }

    /**
     *  Path.equals()方法并不直接访问文件系统，所以没有强制要求文件必须存在，也不会检查两个文件是否是同一个文件。
     *  在有的系统中，路径的比较会忽略大小写，有的则是大小写敏感的
     *  可以使用Files.isSameFile()来判断两个路径是否指向同一个文件或路径且真实存在
     */
    @Test
    public void equalsTest() {
        Path path1 = getPath("./../");
        Path path2 = getPath("../");
        // false
        System.out.println(path1.equals(path2));
    }

    /**
     * 这个方法用来检查两个Path是否指向同一个路径/文件，前提是对应的 路径/文件 真实存在
     * @see java.nio.file.Files#isSameFile(Path, Path)
     */
    @Test
    public void isSameFile() {
        Path path1 = getPath("./../");
        Path path2 = getPath("../");
        try {
            boolean b = Files.isSameFile(path1, path2);
            // true
            System.out.println(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对路径进行比较，根据字典顺序进行比较
     * 该方法不会检查路径/文件是否真实存在
     */
    @Test
    public void compareTo() {
        Path path1 = getPath("./../");
        Path path2 = getPath("../abc");
        System.out.println(path1.compareTo(path2));
    }

    /**
     * 局部路径比较，不是局部字符比较
     */
    @Test
    public void startsWith() {
        // true
        System.out.println(getPath().startsWith("G:/"));
        // false
        System.out.println(getPath().startsWith("G:"));
    }

    /**
     * 局部路径比较，不是局部字符比较
     */
    @Test
    public void endsWith() {
        // true
        System.out.println(getPath().endsWith("README.md"));
        // false
        System.out.println(getPath().endsWith("md"));
    }

    /**
     * Path实现了Iterable接口，所以你可以得到一个对象然后新型循环遍历一个路径的信息
     */
    @Test
    public void iterable() {
        for (Path path : getPath()) {
            System.out.println(path);
        }
    }
}
