//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame;


import javax.swing.*;

public class MainFrame extends JFrame {;
    private GameCarrier carrier = new GameCarrier();

    public MainFrame() {
        //this.setJMenuBar(this.gameController.gameMenuBar);
        //this.add(this.gameController.gameLabel);
        this.setTitle("Ball Game");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        //this.addKeyListener(this.gameController.userInputHandler);
    }
}
