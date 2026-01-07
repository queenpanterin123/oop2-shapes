package pl.qp123;

import javax.swing.*;
import java.awt.*;

public class Circle extends Ellipse {

    public Circle(){
        super();
        name="Circle";
    }

    public Circle(Color color, Point center, double radius){
        super(color, center, radius,radius);
        name="Circle";
    }

    @Override
    protected void getExtraData(JFrame frame) {
        String rad= JOptionPane.showInputDialog(frame,
                "Enter radius of the circle r",
                "10");
        ax1=Double.parseDouble(rad);
        ax2=ax1;
    }
    public Square getCircumscribedSquare(Color color){
        double side = 2*ax1;
        return new Square(color, center,side);
    }

    @Override
    public String toSVG(){
        return String.format("<circle cx='%f' cy='%f' r='%f' fill='rgb(%d,%d,%d)' />",
                center.x, center.y, ax1, color.getRed(),color.getGreen(),color.getBlue());

    }
}
