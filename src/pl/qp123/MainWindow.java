package pl.qp123;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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

    public MainWindow() {
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


        buttonCredits.addActionListener(e -> actionCredits());
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

    private void updateLabels() {
        pointLabel.setText("Point: " + (point != null ? point.toString() : "not set"));
        if (color != null) {
            colorLabel.setText("Color selected");
            colorLabel.setForeground(color);
        } else {
            colorLabel.setText("Color: not set");
        }
    }

    private void drawInitialShapes() {
        Color color1 = new Color(132, 180, 200, 200);
        Circle c = new Circle(color1, new Point(150, 150), 50);
        canvas.shapes.add(c);
        canvas.rePaint();
    }

    private void actionClose() {
        System.exit(0);
    }

    private void actionCredits() {
        JOptionPane.showMessageDialog(this, "Author: Martyna Uranowska");
    }

    private void actionChooseColor() {
        String c = JOptionPane.showInputDialog(this,
                "Please input color components: r:g:b\n or leave empty for random color",
                JOptionPane.QUESTION_MESSAGE);
        if (c == null || c.trim().isEmpty()) {
            this.color = null;
        } else {
            try {

                String[] rgb = c.split(":");
                if (rgb.length == 3) {
                    this.color = new Color(
                            Integer.parseInt(rgb[0].trim()),
                            Integer.parseInt(rgb[1].trim()),
                            Integer.parseInt(rgb[2].trim())
                    );
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Use format r:g:b (e.g. 255:0:0)");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: Invalid numbers or format!");
            }
            updateLabels();
        }
    }


    private void actionAdd() {
        if (point == null) {
            JOptionPane.showMessageDialog(this, "Select a point (click on canvas) and a color");
            return;
        }

        Color colorToUse;
        if (this.color != null) {
            colorToUse = this.color;
        } else {
            Random rand = new Random();
            colorToUse = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }
        String[] possibilities = {"Circle", "Rectangle", "Ellipse", "Square"};
        String selection = (String) JOptionPane.showInputDialog(this,
                "Select shape type:",
                "Add shape",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "Circle");

        if (selection != null) {
            Shape newShape = null;
            if (selection.equals("Circle")) newShape = new Circle(colorToUse, point, 0);
            else if (selection.equals("Rectangle")) newShape = new Rectangle(colorToUse, point, 0, 0);
            else if (selection.equals("Ellipse")) newShape = new Ellipse(colorToUse, point, 0, 0);
            else if (selection.equals("Square")) newShape = new Square(colorToUse, point, 0);

            if (newShape != null) {
                newShape.getExtraData(this);
                canvas.shapes.add(newShape);
                canvas.rePaint();
            }
        }
    }

    private void actionClear() {
        canvas.shapes.clear();
        canvas.rePaint();
    }

    private void actionShowShapes() {
        StringBuilder sb = new StringBuilder("Shapes on canvas:\n");
        for (Shape s : canvas.shapes) {
            sb.append(s.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Shapes List", JOptionPane.PLAIN_MESSAGE);
    }

    private void actionPointClicked(MouseEvent e) {
        this.point = new Point(e.getX(), e.getY());
        updateLabels();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        controlPanel = new JPanel();
        controlPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(controlPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonColor = new JButton();
        buttonColor.setText("Button");
        controlPanel.add(buttonColor, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonShapes = new JButton();
        buttonShapes.setText("Button");
        controlPanel.add(buttonShapes, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAdd = new JButton();
        buttonAdd.setText("Button");
        controlPanel.add(buttonAdd, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonClear = new JButton();
        buttonClear.setText("Button");
        controlPanel.add(buttonClear, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pointLabel = new JLabel();
        pointLabel.setText("Label");
        controlPanel.add(pointLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCredits = new JButton();
        buttonCredits.setText("Button");
        controlPanel.add(buttonCredits, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        colorLabel = new JLabel();
        colorLabel.setText("Label");
        controlPanel.add(colorLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonClose = new JButton();
        buttonClose.setText("Button");
        controlPanel.add(buttonClose, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canvasPanel = new JPanel();
        canvasPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(canvasPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(14, 242), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        canvasPanel.add(spacer1, BorderLayout.EAST);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}




