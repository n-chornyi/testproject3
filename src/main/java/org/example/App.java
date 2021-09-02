package org.example;

import org.example.frames.IntroFrame;
import org.example.frames.SortFrame;

public class App {

    private IntroFrame introFrame;
    private SortFrame sortFrame;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        introFrame = new IntroFrame(this);
        introFrame.setVisible(true);
    }

    public void showMain() {
        sortFrame.setVisible(false);
        introFrame.setVisible(true);
    }

    public void showNumbers(int count) {
        sortFrame = new SortFrame(this);
        introFrame.setVisible(false);
        sortFrame.setVisible(true);
        sortFrame.generatePanelNumbers(count);
    }
}
