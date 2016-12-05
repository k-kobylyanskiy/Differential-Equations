import javax.swing.*;
import java.awt.*;

public class FieldWindow {
    Field field;
    JFrame fieldWindow = new JFrame("График");

    public void fieldInit(){
        fieldWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        fieldWindow.setSize(1300, 715);
        fieldWindow.setResizable(false);
        fieldWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        fieldWindow.setVisible(true);

        field = new Field();
        field.setLayout(null);
        field.setPreferredSize(new Dimension(1300, 715));
        fieldWindow.add(field);
    }
}
