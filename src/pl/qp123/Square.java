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
        String inA = JOptionPane.showInputDialog(frame,
                "Enter side lenght: ", "80");
        if (inA == null){
            return;
        }
        try{
            a=Double.parseDouble(inA);
            if (a < 0){
                JOptionPane.showMessageDialog(frame,"Error: Invalid input");
            }else{
                b=a;
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: Invalid input");
        }


    }

    public Circle getInscribedCircle(Color color){
        return new Circle(color, center,a/2);
    }


    @Override
    public String toSVG(){
        Point corner = getCorner();
        return String.format("<rect width=\"%.2f\" height=\"%.2f\" x=\"%.2f\" y=\"%.2f\" fill=\"rgb(%d,%d,%d)\" />",
                a,a, corner.x,corner.y, color.getRed(),color.getGreen(),color.getBlue());

    }
}
