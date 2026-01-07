package pl.qp123;

import javax.swing.*;
import java.awt.*;

public class Square extends Rectangle{
    public Square(){
        super();
        name="Square";
    }
    public Square(Color color, Point center, double side){
        super(color, center, side, side);
        name="Square";
    }

    @Override
    protected void getExtraData(JFrame frame) {
        a=Double.parseDouble(JOptionPane.showInputDialog(frame,
                "Enter side lenght: ","80"));
        b=a;
    }

    public Circle getInscribedCircle(Color color){
        return new Circle(color, center,a/2);
    }


    @Override
    public String toSVG(){
        Point corner = getCorner();
        return String.format("<rect width='%f' height='%f' x='%f' y='%f' fill='rgb(%d,%d,%d)' />",
                a,a, corner.x,corner.y, color.getRed(),color.getGreen(),color.getBlue());

    }
}
