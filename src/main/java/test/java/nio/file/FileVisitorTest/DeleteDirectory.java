package test.java.nio.file.FileVisitorTest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteDirectory implements FileVisitor<Path> {

    public DeleteDirectory() {
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Visiting Path: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean deleted = deleteFile(file);
        if (deleted) {
            System.out.println("Deleted file: " + file);
            return FileVisitResult.CONTINUE;
        } else {
            System.out.println("Not deleted file: " + file);
        }
        return FileVisitResult.TERMINATE;
    }

    boolean deleteFile(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("delete " + file + " failed! " + exc.getMessage());
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        boolean deleted = deleteFile(dir);
        if (deleted) {
            System.out.println("Deleted Directory: " + dir);
            return FileVisitResult.CONTINUE;
        } else {
            System.out.println("Not deleted Directory: " + dir);
        }
        return FileVisitResult.TERMINATE;
    }

    public static void main(String[] args) {
        Path deleteDir = Paths.get("G:/123/deleteDir");
        DeleteDirectory dd = new DeleteDirectory();
        try {
            Files.walkFileTree(deleteDir, dd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
