package org.example;

import org.example.frames.IntroFrame;
import org.example.frames.SortFrame;

public class App {

    private IntroFrame mainFrame;
    private SortFrame numbersFrame;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        mainFrame = new IntroFrame(this);
        mainFrame.setVisible(true);
    }

    public void showMain() {
        numbersFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    public void showNumbers(int count) {
        numbersFrame = new SortFrame(this);
        mainFrame.setVisible(false);
        numbersFrame.setVisible(true);
        numbersFrame.generatePanelNumbers(count);
    }
}
