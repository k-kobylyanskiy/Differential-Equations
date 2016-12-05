import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Field extends JPanel {

    ArrayList<Point> listOfPoints;
    ArrayList<Point> interpolatedPoints;
    ArrayList<CalculatedPoint> calculatedFromMethodPoints;

    public float x0;
    public float y0;
    public float border;
    public float epsilon;

    public void paintComponent(Graphics g){
        super.paintComponent(g);


        //int step = Integer.parseInt(Elements.getBorder());

        // рисование исходных точек

        for (Point point : listOfPoints) {
            g.setColor(Color.decode("#c0392b"));
            g.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
        }

        // рисование графика

        Graphics2D g2 = (Graphics2D)g;
        BasicStroke pen1 = new BasicStroke(3);
        g2.setStroke(pen1);

        // Результат интерполяции
        Iterator<Point> it = interpolatedPoints.iterator();
        g2.setColor(Color.decode("#3498db"));

        Point p1 = new Point(0,0);
        Point p2;

        if(it.hasNext()){
            p1 = it.next();
        }
        while (it.hasNext()){
            p2 = it.next();
            g2.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
            if (it.hasNext()){
                p1 = p2;
                p2 = it.next();
            }
        }
        g.setColor(Color.RED);
        Integer x = Math.round(1300/(border)/2);
        g.drawLine(650, 705, 650, 715);
        g.drawLine(0, 65, 10, 65);

    }

    Field(){
        initPoints();
        calculateLagrange();
        repaint();
    }


    public void initPoints(){

        // инициализация точек пикселей для вывода на экран
        listOfPoints = new ArrayList<>();
        calculatedFromMethodPoints = new ArrayList<>();

        x0 = Float.parseFloat(Elements.getCertainConditionsX());
        y0 = Float.parseFloat(Elements.getCertainConditionsY());
        border = Float.parseFloat(Elements.getBorder());
        epsilon = Float.parseFloat(Elements.getEpsilon());

        Float h = (border - x0) * epsilon ;
        calculatedFromMethodPoints.add(new CalculatedPoint(x0, y0));
        int i = 1;
        Float j;
        for (j = x0; j <= x0 + 5 * h; j +=h){

            CalculatedPoint point = new CalculatedPoint(0f,0f);

            point.setX(calculatedFromMethodPoints.get(i-1).getX()+ h);
            point.setY(calculatedFromMethodPoints.get(i-1).getY() + h * calculateF(
                    calculatedFromMethodPoints.get(i-1).getX() + h, calculatedFromMethodPoints.get(i-1).getY()
                ));
            j += h;
            i++;
            calculatedFromMethodPoints.add(point);
        }

        while (j < border + h){

            CalculatedPoint point = new CalculatedPoint(0f,0f);
            // предиктор
            point.setX(calculatedFromMethodPoints.get(i-1).getX()+ h);
            point.setY(calculatedFromMethodPoints.get(i-1).getY() +
                    (h/24) * (55 * calculateF(calculatedFromMethodPoints.get(i-1).getX(), calculatedFromMethodPoints.get(i-1).getY())
                    - 59 * calculateF(calculatedFromMethodPoints.get(i-2).getX(), calculatedFromMethodPoints.get(i-2).getY()) +
                   37 *  calculateF(calculatedFromMethodPoints.get(i-3).getX(), calculatedFromMethodPoints.get(i-3).getY()) -
                            9 * calculateF(calculatedFromMethodPoints.get(i-4).getX(), calculatedFromMethodPoints.get(i-4).getY())
                    ));
            calculatedFromMethodPoints.add(point);
            // корректор
            Float y = calculatedFromMethodPoints.get(i-1).getY();
            y += (h/24) * (9 * calculateF(calculatedFromMethodPoints.get(i).getX(), calculatedFromMethodPoints.get(i).getY()) +
                    19 * calculateF(calculatedFromMethodPoints.get(i-1).getX(), calculatedFromMethodPoints.get(i-1).getY()) -
                    5 * calculateF(calculatedFromMethodPoints.get(i-2).getX(), calculatedFromMethodPoints.get(i-2).getY()) +
                    calculateF(calculatedFromMethodPoints.get(i-3).getX(), calculatedFromMethodPoints.get(i-3).getY())
            ) ;
            calculatedFromMethodPoints.get(i).setY(y);
            j += h;
            i++;
        }


        for (CalculatedPoint p: calculatedFromMethodPoints){
            System.out.println(p.getX() + ";" + p.getY());
        }

        // вычисление пикселей на основе точек

        for (i = 0; i < calculatedFromMethodPoints.size(); i++){
            Float x = calculatedFromMethodPoints.get(i).getX()*1300/(border);
            Float y = 715 - calculatedFromMethodPoints.get(i).getY()*1300/(border);

            Point p = new Point(Math.round(x), Math.round(y));
            listOfPoints.add(p);
        }
    }

    public Float calculateF(Float x, Float y){
        return x + y;
    }

    public void calculateLagrange() {
        interpolatedPoints = new ArrayList<>();
        for (double i = 0; i < 1300; i++) {
            double lagrangePol = 0.0;
            double basicsPol;

            for (int j = 0; j < listOfPoints.size(); j++) {
                basicsPol = 1.0;
                for (int k = 0; k < listOfPoints.size(); k++) {
                    if (k == j) continue;
                    basicsPol *= ((i - listOfPoints.get(k).getX()) / (listOfPoints.get(j).getX() - listOfPoints.get(k).getX()));
                }
                lagrangePol += (basicsPol * (listOfPoints.get(j).getY()));
            }
            interpolatedPoints.add(new Point((int)i, (int)lagrangePol));
        }
    }

}

