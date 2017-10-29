package test.java.beans.PropertyEditorTest;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/6/8.
 */
public class CustomDateEditorTest {

    private DateFormat getDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat;
    }

    @Test
    public void customDateEditorTest() {
        DateFormat dateFormat = getDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat);
        dateEditor.setAsText("2017-06-08");
        System.out.println(dateEditor.getValue());
        System.out.println(dateEditor.getAsText());
    }
}
