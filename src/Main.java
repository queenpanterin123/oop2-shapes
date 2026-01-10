import pl.qp123.*;
import pl.qp123.Point;
import pl.qp123.Rectangle;
import pl.qp123.Shape;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

//        Circle c1=new Circle(Color.BLUE, new Point(20,20),10);
//        System.out.println(c1);
//        System.out.println(c1.getArea());
//        System.out.println(c1.getPerimeter());
//        System.out.println(c1.getCircumscribedSquare(Color.RED));
//        Shape c2=new Circle(Color.BLUE, new Point(20,20),10);
//        System.out.println(c2);
//        //This won't compile, because c2 is declared as Shape.
//        //c2.getCircumscribedSquare(Color.RED);
//        System.out.println(c2);
//        Shape c3=((Circle) c2).getCircumscribedSquare(Color.RED);
//        System.out.println(c3);
//        Rectangle r=new Rectangle(Color.PINK,new Point(15,15),10,20);
//        System.out.println(r);
//        System.out.println(r.getCircumscribedCircle(Color.CYAN));
//        System.out.println(new Ellipse(Color.YELLOW,new Point(10,10),20,30));


        MainWindow frame = new MainWindow();
        frame.setTitle("Shapes");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}