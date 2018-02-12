package test.java.awt.firstClass;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author yanchao
 * @date 2018/1/30 10:24
 */
public class ActionTest {

    private static JFrame frame;
    private static JPanel myPanel;
    private JButton button;

    public ActionTest() {
        myPanel = new JPanel();
        button = new JButton("button");
        SimpleListener listener = new SimpleListener();
        button.addActionListener(listener);
        myPanel.add(button);
    }

    public static void main(String[] args) {
        ActionTest gui = new ActionTest();
        frame = new JFrame("Simple");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(myPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
