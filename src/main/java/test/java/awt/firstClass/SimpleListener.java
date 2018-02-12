package test.java.awt.firstClass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Objects;

/**
 * @author yanchao
 * @date 2018/1/30 10:22
 */
public class SimpleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        if (Objects.equals(buttonName, "button")) {
            System.out.println("Button onClick!");
        }
    }
}
