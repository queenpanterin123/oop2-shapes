package pl.qp123;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingCanvas extends JComponent{
    public ArrayList<Shape> shapes = new ArrayList<>();

    protected void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D) g;
        for (Shape s:shapes){
            s.draw(g2d);
        }
    }
    public void rePaint(){
        this.repaint();
    }
}
