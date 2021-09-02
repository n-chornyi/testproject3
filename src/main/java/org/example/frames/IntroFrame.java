package org.example.frames;

import org.example.App;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class IntroFrame extends JFrame {

    private final App app;
    private final int height = 420;
    private final int width = 600;

    private JTextField fieldCountNumbers;

    public IntroFrame(App app) {
        this.app = app;
        initFrame();
    }

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
                JOptionPane.showMessageDialog(null, "Incorrect, Enter numbers from 3 to 1000");
            } else {
                int tempInt = Integer.parseInt(tempTextNumber);
                if (tempInt > 1000 || tempInt < 3) {
                    JOptionPane.showMessageDialog(null, "Enter numbers from 3 to 1000");
                } else {
                    this.app.showNumbers(tempInt);
                }
            }
        });
        jPanel.add(titleButton);
        jPanel.add(fieldCountNumbers);
        jPanel.add(enterButton);
        setContentPane(jPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(width, height));
        setBounds((size.width/2) - (width/2), (size.height/2) - (height/2), width, height);
        setResizable(false);
        setTitle("TestProject - Swing version");
        pack();
    }
}
