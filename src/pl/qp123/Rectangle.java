package pl.qp123;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Shape {
    protected double a,b;

    public Rectangle(){
        super();
        name="Rectangle";
    }

    public Rectangle(Color color, Point center,
                     double a, double b){
        super(color,center);
        this.a=a;
        this.b=b;
        name="Rectangle";
    }
    @Override
    protected double computeArea(){
        return a*b;
    }
    @Override
    protected double computePerimeter(){
        return 2*a+2*b;
    }
    @Override
    public Point getCorner(){
        Point pm=new Point(a/2,b/2);
        return center.subtract(pm);
    }
    public Circle getCircumscribedCircle(Color color){
        return new Circle(color, center,
                new Point(a/2,b/2).vecLength());
    }
    @Override
    protected void getExtraData(JFrame frame){
        String sides= JOptionPane.showInputDialog(frame,
                "Enter sides lengths a:b",
                "105:55");
        String[] sidesArray=sides.split(":");
        a=Double.parseDouble(sidesArray[0]);
        b=Double.parseDouble(sidesArray[1]);
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        Point corner=getCorner();
        Rectangle2D.Double rectangle=
                new Rectangle2D.Double(corner.x,corner.y,a,b);
        g2d.draw(rectangle);
        g2d.fill(rectangle);
    }
    @Override
    public String toString(){
        return super.toString()+
                (isEmpty ? "":", [a:"+(int) a+", b:"+(int)b+"]");
    }
    @Override
    public String toSVG(){
        Point corner = getCorner();
        return String.format("<rect width='%f' height='%f' x='%f' y='%f' fill='rgb(%d,%d,%d)' />",
                a,b, corner.x,corner.y, color.getRed(),color.getGreen(),color.getBlue());

    }
}
