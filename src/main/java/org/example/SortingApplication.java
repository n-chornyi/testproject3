package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

public class SortingApplication {

    private IntroFrame introFrame;
    private SortFrame sortFrame;

    public static void main(String[] args) {
        new SortingApplication().run();
    }

    /**
     * Initialization fields {@link SortingApplication#introFrame}, {@link SortingApplication#sortFrame}
     * Shows intro frame
     */
    private void run() {
        introFrame = new IntroFrame(this);
        sortFrame = new SortFrame(this);
        showIntroFrame();
    }

    /**
     * Shows intro frame
     */
    public void showIntroFrame() {
        sortFrame.setVisible(false);
        introFrame.showFrame();
    }

    /**
     * Shows sorting frame
     */
    public void showSortingFrame(int count) {
        introFrame.setVisible(false);
        sortFrame.setVisible(true);
        sortFrame.generatePanelCells(count);
    }

    /**
     * IntroFrame implements an application that creates and  displays a intro window
     */
    class IntroFrame extends JFrame {

        private final SortingApplication app;

        private JTextField fieldCountNumbers;

        /**
         * Initialization field {@link IntroFrame#app}
         */
        public IntroFrame(SortingApplication app) {
            this.app = app;
            initFrame();
        }

        /**
         * Initialization intro frame
         */
        private void initFrame() {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(null);
            JLabel titleButton = new JLabel("How many numbers to display?");
            titleButton.setFont(new java.awt.Font("Times New Roman", 0, 16));
            titleButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            titleButton.setBounds(125,60,300,50);
            fieldCountNumbers = new JTextField();
            fieldCountNumbers.setFont(new java.awt.Font("Times New Roman", 0, 16));
            fieldCountNumbers.setBounds(200,120,150,25);
            fieldCountNumbers.setHorizontalAlignment(SwingConstants.CENTER);
            JButton enterButton = new JButton("Enter");
            enterButton.setFont(new java.awt.Font("Times New Roman", 0, 16));
            enterButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            enterButton.setBounds(225,170,100,25);
            fieldCountNumbers.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if ((!Character.isDigit(c) ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE) ||
                            fieldCountNumbers.getText().length() > 7)) {
                        e.consume();
                    }
                }
            });
            enterButton.addActionListener(action -> {
                String tempTextNumber = fieldCountNumbers.getText();
                if (!tempTextNumber.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "Incorrect, Enter numbers from 1 to 1000");
                } else {
                    int tempInt = Integer.parseInt(tempTextNumber);
                    if (tempInt > 1000 || tempInt < 1) {
                        JOptionPane.showMessageDialog(null, "Enter numbers from 1 to 1000");
                    } else {
                        this.app.showSortingFrame(tempInt);
                    }
                }
            });
            jPanel.add(titleButton);
            jPanel.add(fieldCountNumbers);
            jPanel.add(enterButton);
            setContentPane(jPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            int height = 420;
            int width = 600;
            setMinimumSize(new Dimension(width, height));
            setBounds((size.width/2) - (width /2), (size.height/2) - (height /2), width, height);
            setResizable(false);
            setTitle("TestProject - Swing version");
            pack();
        }

        /**
         * Shows sorting frame
         */
        public void showFrame() {
            fieldCountNumbers.setText("");
            setVisible(true);
        }
    }

    /**
     * IntroFrame implements an application that creates and  displays a sort window
     */
    class SortFrame extends JFrame {
        private final SortingApplication app;

        private final JPanel contentPane = new JPanel(null);
        private final JPanel panelCells = new JPanel();
        private final JTextField speedTextField = new JTextField();
        private JButton buttonSort;
        private JButton buttonReset;
        private List<JButton> arrayCells = new ArrayList<>();

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
        private boolean isReverse = true;

        /**
         * Initialization field {@link IntroFrame#app}
         */
        public SortFrame(SortingApplication app) {
            this.app = app;
            initializationFrame();
        }

        /**
         * Initialization sort frame
         */
        private void initializationFrame() {
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
                    if (!isReverse) {
                        reverseQuickSort(arrayNumbers, 0, arrayNumbers.length-1);
                    } else {
                        quickSort(arrayNumbers, 0, arrayNumbers.length-1);
                    }
                    buttonReset.setEnabled(true);
                    buttonSort.setEnabled(true);
                    speedTextField.setEnabled(true);
                    isWork = false;
                    isReverse = !isReverse;
                }).start();
            });
            buttonReset = new JButton("Reset");
            buttonReset.setBounds(650,200,120,30);
            buttonReset.addActionListener(a -> {
                app.showIntroFrame();
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

            JScrollPane scrollPane = new JScrollPane(panelCells);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBounds(25, 75, 600, 360);
            contentPane.add(scrollPane);
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

        /**
         * Updates a panel cells
         */
        public void generatePanelCells(int count) {
            countNumbers = count;
            panelCells.removeAll();
            generateCells();
            panelCells.setLayout(new BoxLayout(panelCells, BoxLayout.X_AXIS));
            panelCells.repaint();
            panelCells.revalidate();
        }

        /**
         * Creates a new cells
         * Writes values to cells
         * Adds to panel for visibility
         */
        private void generateCells() {
            arrayCells.clear();
            arrayNumbers = generateArrayNumbers();
            int temp = 0;
            for (int i = 0; i <= countNumbers / 10; i++) {
                Box box = Box.createVerticalBox();
                box.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                box.setAlignmentY(0);
                for (int j = 0; j < 10; j++) {
                    if (temp >= countNumbers) {
                        break;
                    }
                    JButton tempButton = new JButton(Integer.toString(arrayNumbers[temp]));
                    tempButton.setMaximumSize(new Dimension(100,30));
                    tempButton.setMinimumSize(new Dimension(100,30));
                    tempButton.setBackground(Color.WHITE);
                    tempButton.setFont(new Font("Times new Roman", 0 , 18));
                    tempButton.addActionListener(l -> {
                        if (!isWork) {
                            if (Integer.parseInt(l.getActionCommand()) < 31) {
                                countNumbers = Integer.parseInt(l.getActionCommand());
                                generatePanelCells(countNumbers);
                            } else {
                                JOptionPane.showMessageDialog(null, "Please select a value smaller or equal to 30.");
                            }
                        }
                    });
                    arrayCells.add(tempButton);
                    box.add(arrayCells.get(temp));
                    temp++;
                }
                panelCells.add(box);
            }
        }

        /**
         * Creates cell values
         */
        private int[] generateArrayNumbers() {
            Random random = new Random();
            int[] tempCollection = new int[countNumbers];
            for (int i = 0; i < countNumbers; i++) {
                tempCollection[i] = random.nextInt(5000)+31;
            }
            tempCollection[random.nextInt(countNumbers)] = random.nextInt(30)+1;
            return tempCollection;
        }

        /**
         * Sort cells using an algorithm "quicksort"
         */
        public void reverseQuickSort(int[] array, int low, int high) {
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
                reverseQuickSort(array, low, j);
            if (high > i)
                reverseQuickSort(array, i, high);
        }

        /**
         * Reverse sort cells using an algorithm "quicksort"
         */
        public void quickSort(int[] array, int low, int high) {
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
                quickSort(array, low, j);
            if (high > i)
                quickSort(array, i, high);
        }

        /**
         * Changes background color in cell
         */
        private void changeBackgroundCell(int i, Color color) {
            arrayCells.get(i).setBackground(color);
        }

        /**
         * Swaps the values in 2 cells in places
         */
        private void swapTextButtons(int[] array, int i, int j) {
            changeBackgroundCell(i, changeColor);
            changeBackgroundCell(j, changeColor);
            String tempString = arrayCells.get(i).getText();
            arrayCells.get(i).setText(arrayCells.get(j).getText());
            arrayCells.get(j).setText(tempString);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            sleepSort();
            changeBackgroundCell(i, defaultColor);
            changeBackgroundCell(j, defaultColor);
        }

        /**
         * Delays for a while after each iteration
         */
        private void sleepSort() {
            try {
                Thread.sleep(timeDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
