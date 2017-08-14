package streamtest;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Tool {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static void newLine() {
        System.out.println();
    }

    public static void println(String str) {
        System.out.println(str);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void print(String str) {
        print(str, "");
    }

    public static void print(String str, String separator) {
        if (!isEmpty(separator)) {
            System.out.print(str + " " + separator + " ");
        } else {
            System.out.print(str);
        }
    }

    public static void printInNewLine(String str, String separator) {
        newLine();
        print(str, separator);
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }


}
