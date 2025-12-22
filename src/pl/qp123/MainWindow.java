package pl.qp123;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JPanel canvasPanel;
    private JPanel controlPanel;
    private JButton buttonShapes;
    private JButton buttonAdd;
    private JButton buttonClear;
    private JButton buttonCredits;
    private JButton buttonClose;
    private JLabel pointLabel;
    private JLabel colorLabel;
    private JButton buttonColor;
    //custom fields
    private DrawingCanvas canvas = new DrawingCanvas();
    private Color color = null;
    private Point point = null;

    public MainWindow(){
        super();
        this.setContentPane(mainPanel);
        canvasPanel.add(canvas);
        Border greyLine = BorderFactory.createLineBorder(Color.DARK_GRAY);
        canvasPanel.setBorder(greyLine);
        canvasPanel.setBackground(new Color(250, 250, 240));
        controlPanel.setBackground(new Color(245, 245, 250));
        buttonColor.setText("Choose color");
        buttonAdd.setText("Add shape");
        buttonClear.setText("Clear");
        buttonShapes.setText("List of shapes");
        buttonCredits.setText("Credits");
        buttonClose.setText("Close");
        updateLabels();
        drawInitialShapes();


        buttonCredits.addActionListener(e -> actionClear());
        buttonClose.addActionListener(e -> actionClose());
        buttonAdd.addActionListener(e -> actionAdd());
        buttonShapes.addActionListener(e -> actionShowShapes());
        buttonColor.addActionListener(e -> actionChooseColor());
        buttonClear.addActionListener(e -> actionClear());
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actionPointClicked(e);
            }
        });
    }
}
