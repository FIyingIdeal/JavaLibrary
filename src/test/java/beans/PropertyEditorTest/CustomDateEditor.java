package test.java.beans.PropertyEditorTest;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Created by Administrator on 2017/6/8.
 */
public class CustomDateEditor extends PropertyEditorSupport {

    private DateFormat dateFormat;

    public CustomDateEditor(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(dateFormat.parse(text));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAsText() {
        return this.dateFormat.format(getValue());
    }
}
