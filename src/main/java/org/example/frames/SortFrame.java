package org.example.frames;

import org.example.App;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SortFrame extends JFrame {

    private final App app;

    private final JPanel contentPane = new JPanel(null);
    private final JPanel panelCells = new JPanel();
    private final JTextField speedTextField = new JTextField();
    private JButton buttonSort;
    private JButton buttonReset;
    private JButton[] arrayCells;

    private final Color defaultColor = Color.WHITE;
    private final Color changeColor = Color.CYAN;
    private final Color pilotColor = Color.GREEN;
    private final int height = 500;
    private final int width = 800;
    private int status = 1;
    private int countNumbers = 0;
    private int[] arrayNumbers;
    private long timeDelay = 500;
    private boolean isWork = false;
    private boolean firstStart = true;
    private boolean reverse = false;

    public SortFrame(App app) {
        this.app = app;
        initFrame();
    }

    private void initFrame() {
        buttonSort = new JButton("Sort");
        buttonSort.setBounds(650,150,120,30);
        buttonSort.addActionListener( a -> {
            new Thread(() -> {
                isWork = true;
                buttonReset.setEnabled(false);
                buttonSort.setEnabled(false);
                speedTextField.setEnabled(false);
                String tempSpeedValue = speedTextField.getText();
                if (tempSpeedValue == null || tempSpeedValue.isEmpty()) {
                    timeDelay = 500;
                } else {
                    timeDelay = Integer.parseInt(tempSpeedValue) * 1000;
                }
                if (!reverse) {
                    reverseQuickSort(arrayNumbers, 0, arrayNumbers.length-1);
                } else {
                    quickSort(arrayNumbers, 0, arrayNumbers.length-1);
                }
                buttonReset.setEnabled(true);
                buttonSort.setEnabled(true);
                speedTextField.setEnabled(true);
                isWork = false;
                reverse = !reverse;
            }).start();
        });
        buttonReset = new JButton("Reset");
        buttonReset.setBounds(650,200,120,30);
        buttonReset.addActionListener(a -> {
            firstStart = true;
            app.showMain();
        });
        String builder = "<html>" +
                "Enter speed <br>" +
                "show sort [1,30] <br>" +
                "int (default 0,5 s):" +
                "</html>";
        JLabel labelSpeed = new JLabel(builder);
        labelSpeed.setBounds(655,240,120,70);
        speedTextField.setBounds(655,300,120,30);
        speedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        speedTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((!Character.isDigit(c) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) ||
                        speedTextField.getText().length() > 1)) {
                    e.consume();
                }
            }
        });
        contentPane.add(buttonSort);
        contentPane.add(buttonReset);
        contentPane.add(labelSpeed);
        contentPane.add(speedTextField);
        contentPane.setPreferredSize(new Dimension(width, height));
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(width, height));
        setBounds((size.width/2) - (width/2), (size.height/2) - (height/2), width, height);
        setResizable(false);
        setTitle("TestProject - Swing version");
        pack();
    }

    public void generatePanelNumbers(int count) {
        countNumbers = count;
        arrayNumbers = generateArrayNumbers();
        if (firstStart) {
            arrayCells = new JButton[countNumbers];
            generateButtons();
            JScrollPane scrollPane = new JScrollPane(panelCells);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBounds(25, 25, 600, 450);
            panelCells.setLayout(new BoxLayout(panelCells, BoxLayout.X_AXIS));
            panelCells.setSize(400,400);
            contentPane.add(scrollPane);
            firstStart = false;
        } else {
            for (int i = 0; i < countNumbers; i++) {
                arrayCells[i].setText(Integer.toString(arrayNumbers[i]));
            }
        }
    }

    private void generateButtons() {
        int indentVertical;
        int indentHorizontal = 20;
        int temp = 0;
        for (int i = 0; i <= countNumbers / 10; i++) {
            indentVertical = 20;
            Box box = Box.createVerticalBox();
            box.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            for (int j = 0; j < 10; j++) {
                if (temp >= countNumbers) {
                    break;
                }
                arrayCells[temp] = new JButton(Integer.toString(arrayNumbers[temp]));
                arrayCells[temp].setBackground(Color.WHITE);
                arrayCells[temp].setFont(new Font("Times new Roman", 0 , 18));
                arrayCells[temp].addActionListener(l -> {
                    if (!isWork) {
                        if (Integer.parseInt(l.getActionCommand()) < 30) {
                            generatePanelNumbers(countNumbers);
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a value smaller or equal to 30.");
                        }
                    }
                });
                box.add(arrayCells[temp]);
                indentVertical = indentVertical + 48;
                temp++;
            }
            panelCells.add(box);
            indentHorizontal = indentHorizontal + 90;
        }
    }

    private int[] generateArrayNumbers() {
        Random random = new Random();
        int[] tempCollection = new int[countNumbers];
        for (int i = 0; i < countNumbers; i++) {
            tempCollection[i] = random.nextInt(5000)+30;
        }
        tempCollection[random.nextInt(countNumbers)] = random.nextInt(30);
        return tempCollection;
    }

    public void quickSort(int[] array, int low, int high) {
        if (array.length == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        int pilotValue = array[middle];
        changeBackgroundCell(middle, pilotColor);
        int i = low, j = high;
        int maxReverse = 10;
        while (i <= j) {
            while (array[i] < pilotValue) {
                if (status > maxReverse / 2) {
                    changeBackgroundCell(i, changeColor);
                    sleepSort();
                    changeBackgroundCell(i, defaultColor);
                    status++;
                }
                i++;
            }
            while (array[j] > pilotValue) {
                if (status <= maxReverse / 2) {
                    changeBackgroundCell(j, changeColor);
                    sleepSort();
                    changeBackgroundCell(j, defaultColor);
                    status++;
                }
                j--;
            }
            if (i <= j) {
                swapTextButtons(array, i, j);
                i++;
                j--;
            }
        }
        if (status == maxReverse) {
            status = 1;
        }
        if (low < j)
            quickSort(array, low, j);
        if (high > i)
            quickSort(array, i, high);
    }

    public void reverseQuickSort(int[] array, int low, int high) {
        if (array.length == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        int pilovValue = array[middle];
        changeBackgroundCell(middle, pilotColor);
        int i = low, j = high;
        int showIteration = 10;
        while (i <= j) {
            while (array[i] > pilovValue) {
                if (status > showIteration / 2) {
                    changeBackgroundCell(i, changeColor);
                    sleepSort();
                    changeBackgroundCell(i, defaultColor);
                    status++;
                }
                i++;
            }
            while (array[j] < pilovValue) {
                if (status <= showIteration / 2) {
                    changeBackgroundCell(j, changeColor);
                    sleepSort();
                    changeBackgroundCell(j, defaultColor);
                    status++;
                }
                j--;
            }
            if (i <= j) {
                swapTextButtons(array, i, j);
                i++;
                j--;
            }
        }
        if (status == showIteration) {
            status = 1;
        }
        if (low < j)
            reverseQuickSort(array, low, j);
        if (high > i)
            reverseQuickSort(array, i, high);
    }

    private void changeBackgroundCell(int i, Color color) {
        arrayCells[i].setBackground(color);
    }

    private void swapTextButtons(int[] array, int i, int j) {
        changeBackgroundCell(i, changeColor);
        changeBackgroundCell(j, changeColor);
        String tempString = arrayCells[i].getText();
        arrayCells[i].setText(arrayCells[j].getText());
        arrayCells[j].setText(tempString);
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        sleepSort();
        changeBackgroundCell(i, defaultColor);
        changeBackgroundCell(j, defaultColor);
    }

    private void sleepSort() {
        try {
            Thread.sleep(timeDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}