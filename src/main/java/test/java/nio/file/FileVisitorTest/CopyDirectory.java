package test.java.nio.file.FileVisitorTest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDirectory implements FileVisitor<Path> {

    private Path copyTo;
    private Path currentPath;

    public CopyDirectory(Path copyTo) {
        this.copyTo = copyTo;
    }

    private void copy(Path copyFrom, Path copyTo) throws IOException {
        Files.copy(copyFrom, copyTo);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        currentPath = this.copyTo.resolve(dir.getName(dir.getNameCount() - 1));
        System.out.println("copy directory 【" + dir + "】 to 【" + currentPath + "】");
        if (Files.notExists(currentPath)) {
            Files.createDirectories(currentPath);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path currentFile = currentPath.resolve(file.getName(file.getNameCount() - 1));
        System.out.println("copy directory 【" + file + "】 to 【" + currentFile + "】");
        copy(file, currentFile);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if (exc instanceof FileSystemLoopException) {
            System.out.println("FileSystemLoopException occurred while copy " + file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        // 这里可以这是文件夹的属性
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        Path copyFrom = Paths.get("G:/123");
        Path copyTo = Paths.get("G:/123bak");
        CopyDirectory cp = new CopyDirectory(copyTo);
        Files.walkFileTree(copyFrom, cp);
    }
}
