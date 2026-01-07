package pl.qp123;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

abstract public class Shape implements Serializable {
    //name, color
    protected String name;
    protected Color color;
    //computed values - area, perimeter
    private  Double area = null;
    private Double perimeter = null;
    Point center;
    protected boolean isEmpty=false;


    public Shape(){
        isEmpty=true;
    }
    public Shape(Color color, Point center){
        this.color=color;
        this.center=center;
    }
    public double getArea(){
        if (area==null){
            area = computeArea();
        }
        return area;
    }
    public double getPerimeter(){
        if (perimeter==null){
            perimeter=computePerimeter();
        }
        return perimeter;
    }

    abstract protected void getExtraData(JFrame frame);
    abstract public void draw(Graphics2D g2d);
    //abstrakcyjne - kazdy shape musi miec ale implementuje u sb
    abstract protected double computeArea();
    abstract protected double computePerimeter();
    abstract public Point getCorner();

    public String toString(){
        String str=name;
        if (!isEmpty){
            str+=", area: "+(int)getArea();
            str+="\n["+printColor(color)+"], ";
            str+="center: "+center;
        }
        return str;
    }
    public static String printColor(Color color){
        return "rgb "+color.getRed()+":"
                +color.getGreen()+":"+color.getBlue();
    }

    public abstract String toSVG();

}
