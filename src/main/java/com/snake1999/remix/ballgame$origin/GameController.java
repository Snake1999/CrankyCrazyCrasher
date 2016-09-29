//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameController {
    private boolean isRuning;
    private GameSituation gameSituation;
    private Set<String> pressingCmds = new HashSet<>();
    private Set<String> pressedCmds = new HashSet<>();
    public GameController.UserInputHandler userInputHandler = new GameController.UserInputHandler();
    public GameController.GameLabel gameLabel = new GameController.GameLabel();
    public GameController.GameMenuBar gameMenuBar = new GameController.GameMenuBar();

    public GameController() {
        Thread thread = new Thread(new GameController.GameRunner(), "Game Runner");
        thread.start();
    }

    public void draw(Graphics g, int width, int height) {
        if(this.isRuning) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int l = Math.min(width, height);
            g2d.translate((double)(width - l) / 2.0D, (double)(height - l) / 2.0D);
            g2d.setColor(Color.CYAN);
            g2d.fillRect(0, 0, l, l);
            g2d.scale((double)l / 1000.0D, (double)l / 1000.0D);
            this.gameSituation.draw(g2d);
        }

    }

    public class GameLabel extends JLabel {
        private static final long serialVersionUID = 1L;

        public GameLabel() {
            this.setOpaque(true);
            this.setBackground(Color.WHITE);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            GameController.this.draw(g, this.getWidth(), this.getHeight());
        }
    }

    public class GameMenuBar extends JMenuBar {
        private static final long serialVersionUID = 1L;
        private JMenu gameMenu = new JMenu("Game");
        private JMenuItem newGameMenuItem = new JMenuItem("New Game");

        public GameMenuBar() {
            this.add(this.gameMenu);
            this.gameMenu.setMnemonic(71);
            this.gameMenu.add(this.newGameMenuItem);
            this.newGameMenuItem.setMnemonic(78);
            this.newGameMenuItem.addActionListener(GameController.this.userInputHandler);
        }
    }

    public class GameRunner implements Runnable {
        public GameRunner() {
        }

        public void run() {
            long last = 0L;

            while(true) {
                GameController e = GameController.this;
                synchronized(GameController.this) {
                    if(GameController.this.pressedCmds.contains("New Game")) {
                        last = System.currentTimeMillis();
                        GameController.this.gameSituation = new GameSituation();
                        GameController.this.isRuning = true;
                        GameController.this.pressedCmds = new HashSet<>(GameController.this.pressingCmds);
                        GameController.this.gameLabel.repaint();
                    } else if(GameController.this.isRuning) {
                        long cur = System.currentTimeMillis();
                        if(cur - last >= 5L) {
                            last = cur;
                            GameController.this.gameSituation.simulate(0.3D);
                            GameController.this.pressedCmds = new HashSet<>(GameController.this.pressingCmds);
                            GameController.this.gameLabel.repaint();
                        }
                    }
                }

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException var6) {
                    GameController.this.isRuning = false;
                    return;
                }
            }
        }
    }

    public class UserInputHandler implements KeyListener, ActionListener {
        public UserInputHandler() {
        }

        private void cmdPressed(String cmd) {
            GameController.this.pressingCmds.add(cmd);
            GameController.this.pressedCmds.add(cmd);
        }

        private void cmdReleased(String cmd) {
            GameController.this.pressingCmds.remove(cmd);
        }

        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            GameController var3 = GameController.this;
            synchronized(GameController.this) {
                if(cmd.startsWith("pressed ")) {
                    this.cmdPressed(cmd.substring("pressed ".length()));
                } else if(cmd.startsWith("released ")) {
                    this.cmdReleased(cmd.substring("released ".length()));
                } else {
                    GameController.this.pressedCmds.add(cmd);
                }

            }
        }

        public void keyPressed(KeyEvent e) {
            if(GameController.this.gameSituation != null) {
                GameController.this.gameSituation.userInputHandler.keyPressed(e);
            }

        }

        public void keyReleased(KeyEvent e) {
            if(GameController.this.gameSituation != null) {
                GameController.this.gameSituation.userInputHandler.keyReleased(e);
            }

        }

        public void keyTyped(KeyEvent e) {
            if(GameController.this.gameSituation != null) {
                GameController.this.gameSituation.userInputHandler.keyTyped(e);
            }

        }
    }
}
