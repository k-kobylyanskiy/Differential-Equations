import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Elements {

    // Объявления всех элементов интерфейса
    private static JFrame mainWindow;
    private static JButton solve;
    private static JLabel errorMsg;
    private static JLabel equation;
    private static JLabel chooseBorder;
    private static JLabel certainConditionsMsg;
    private static JLabel chooseEpsilon;
    private static JTextField certainConditionsX;
    private static JTextField certainConditionsY;
    private static JTextField border;
    private static JTextField epsilon;

    public static void init(){
        mainWindowInit(true);
        buttonInit();
        labelsInit();
        textFieldsInit();
        addElements();
    }

    public static void addElements(){
        mainWindow.add(equation);
        mainWindow.add(chooseBorder);
        mainWindow.add(border);
        mainWindow.add(certainConditionsMsg);
        mainWindow.add(certainConditionsX);
        mainWindow.add(certainConditionsY);
        mainWindow.add(chooseEpsilon);
        mainWindow.add(epsilon);
        mainWindow.add(errorMsg);
        mainWindow.add(solve);
    }


    private static void mainWindowInit(boolean visible){
        mainWindow = new JFrame("Дифференциальные уравнения");
        mainWindow.setLayout(new GridLayout(10,1));
        mainWindow.setSize(280, 240);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(visible);
    }

    private static void buttonInit(){
        solve = new JButton("Найти решение");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ElementsAction.solve();
            }
        });
    }

    private static void labelsInit(){
        equation = new JLabel("y' = x + y");

        chooseBorder = new JLabel("Введите конец отрезка");
        certainConditionsMsg = new JLabel("Введите начальные условия");
        chooseEpsilon = new JLabel("Введите точность");

        errorMsg = new JLabel("Вы ввели некорректное значение");
        errorMsg.setVisible(false);
        errorMsg.setForeground(Color.red);
    }

    public static void error(){
        errorMsg.setVisible(true);
    }

    public static void textFieldsInit(){
        certainConditionsX = new JTextField();
        certainConditionsY = new JTextField();
        border = new JTextField();
        epsilon = new JTextField();
    }

    public static String getBorder(){
        return border.getText();
    }
    public static String getCertainConditionsY(){
        return certainConditionsY.getText();
    }
    public static String getCertainConditionsX(){
        return certainConditionsX.getText();
    }

    public static String getEpsilon(){
        return epsilon.getText();
    }

}
