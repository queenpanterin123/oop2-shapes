package pl.qp123;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
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
    private JButton buttonLoad;
    private JButton buttonExport;
    private JButton buttonSave;
    private JPanel control2Panel;
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
        buttonLoad.setText("Load file");
        buttonSave.setText("Save file");
        buttonExport.setText("Export file");
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
        buttonLoad.addActionListener(e -> actionLoad());
        buttonSave.addActionListener(e -> actionSave());
        buttonExport.addActionListener(e -> actionExport());

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
        Color color1 = new Color(0x84, 0xb4, 0xc8, 0xd0);
        Color color2 = new Color(0x8e, 0xc9, 0xbb, 0xd0);
        Color color3 = new Color(0xf4, 0xdc, 0xd6, 0xd0);
        Circle c = new Circle(color1, new Point(200, 200), 70);
        canvas.shapes.add(c.getCircumscribedSquare(color3));
        canvas.shapes.add(c);
        Rectangle r = new Rectangle(color1, new Point(520, 350), 130, 90);
        canvas.shapes.add(r.getCircumscribedCircle(color3));
        canvas.shapes.add(r);
        Ellipse e = new Ellipse(color2, new Point(410, 250), 90, 150);
        canvas.shapes.add(e);
        canvas.shapes.add(e.getInscribedCircle(color3));
        Square s = new Square(color1, new Point(600, 80), 90);
        canvas.shapes.add(s);
        canvas.shapes.add(s.getInscribedCircle(color3));
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
                "Color Selection",
                JOptionPane.QUESTION_MESSAGE, null, null, "67:67:67").toString();
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
        }
        updateLabels();
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

    private void actionSave() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()))) {
                out.writeObject(canvas.shapes);
                JOptionPane.showMessageDialog(this, "Successfully saved!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error: " + ex.getMessage());
            }
        }
    }

    private void actionLoad() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
                canvas.shapes = (ArrayList<Shape>) in.readObject();
                canvas.rePaint();
                JOptionPane.showMessageDialog(this, "Successfully loaded!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error: " + ex.getMessage());
            }
        }
    }

    private void actionExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export to SVG");
        fileChooser.setSelectedFile(new File("new_file.svg"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                writer.println("<svg width=\"800\" height=\"600\" xmlns=\"http://www.w3.org/2000/svg\">");
                for (Shape s : canvas.shapes) {
                    writer.println(s.toSVG());
                }
                writer.println("</svg>");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error! Could not create file.");
            }

        }
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
        control2Panel = new JPanel();
        control2Panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        canvasPanel.add(control2Panel, BorderLayout.NORTH);
        buttonLoad = new JButton();
        buttonLoad.setText("Button");
        control2Panel.add(buttonLoad, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        control2Panel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonSave = new JButton();
        buttonSave.setText("Button");
        control2Panel.add(buttonSave, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonExport = new JButton();
        buttonExport.setText("Button");
        control2Panel.add(buttonExport, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}




