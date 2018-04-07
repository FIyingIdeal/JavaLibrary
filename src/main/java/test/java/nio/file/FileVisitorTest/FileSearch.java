package test.java.nio.file.FileVisitorTest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * 根据文件名字查找文件
 * TODO 现在存在一个问题，查找到两个文件夹后就停止了，不知道为什么
 */

public class FileSearch implements FileVisitor<Path> {

    private final Path searchedFile;
    public boolean found;

    public FileSearch(Path searchedFile) {
        this.searchedFile = searchedFile;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("preVisitDirectory visiting: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        search(file);
        if (found) {
            return FileVisitResult.TERMINATE;
        } else {
            return FileVisitResult.CONTINUE;
        }
    }

    public void search(Path path) throws IOException {
        Path fileName = path.getFileName();
        if (fileName != null && fileName.equals(this.searchedFile)) {
            System.out.println("Searched file was found: " + searchedFile +
                    " in " + path.toRealPath().toString());
        }
        found = true;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("visitFileFailed visited: " + file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("postVisitDirectory visited: " + dir);
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("FileSearch.java");
        FileSearch search = new FileSearch(path);
        EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Path start = Paths.get("G:/");
        /*Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path root : dirs) {
            if (!search.found) {
                Files.walkFileTree(root, opts, Integer.MAX_VALUE, search);
            }
        }*/
        Files.walkFileTree(start, opts, Integer.MAX_VALUE, search);
        if (!search.found) {
            System.out.println("The file " + path + " was not found!");
        }
    }
}
