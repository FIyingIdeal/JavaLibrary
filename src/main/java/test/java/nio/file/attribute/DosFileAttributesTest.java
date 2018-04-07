package test.java.nio.file.attribute;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

/**
 * DOS视图在basic视图的基础上拓展四个属性:
 *   方法名            属性
 * isReadOnly()      readonly
 * isHidden()        hidden
 * isArchive()       archive
 * isSystem()        system
 */
public class DosFileAttributesTest {

    Path path = Paths.get("G:/IDEAWorkspace/JavaLibrary/JavaLibrary.iml");

    @Test
    public void getAttributes() {
        DosFileAttributes attr = null;

        try {
            attr = Files.readAttributes(path, DosFileAttributes.class);
        } catch (IOException exception) {}

        System.out.println("Is read only ? " + attr.isReadOnly());
        System.out.println("Is Hidden ? " + attr.isHidden());
        System.out.println("Is archive ? " + attr.isArchive());
        System.out.println("Is system ? " + attr.isSystem());
    }

    @Test
    public void setAttributes() {
        try {
            String viewAndAttributeName = "dos:hidden";
            Files.setAttribute(path, viewAndAttributeName, false);
        } catch (IOException e) {}
    }
}
