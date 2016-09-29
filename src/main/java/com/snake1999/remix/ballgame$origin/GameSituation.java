//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameSituation {
    public GameSituation.UserInputHandler userInputHandler = new GameSituation.UserInputHandler();
    private GameSituation.Drawer drawer = new GameSituation.Drawer();
    private GameSituation.Player player1;
    private GameSituation.Player player2;
    private List<DisplayObject> displayObjects = new ArrayList<>();
    private double newBallMass1;
    private double newBallMass2;
    private double score1;
    private double score2;
    private double restTime;
    private boolean isOver;
    private int winner;
    public final double GOAL_WIDTH = 50.0D;
    public final double THRUST_MAGNITUDE = 160.0D;
    public final double NEW_BALL_VEL = 3.0D;
    public final double NEW_BALL_MASS_VEL = 0.1D;
    public final double MIN_NEW_BALL_MASS = 5.0D;
    public final double MAX_NEW_BALL_MASS = 100.0D;

    public GameSituation() {
        this.displayObjects.add(new VerticalStick(20.0D, new Vector(100.0D, 500.0D), Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, 100.0D, 1));
        this.displayObjects.add(new VerticalStick(20.0D, new Vector(900.0D, 500.0D), Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, 100.0D, 2));
        this.restTime = 10000.0D;
        this.player1 = new GameSituation.KeyPlayer();
        this.player2 = new GameSituation.KeyPlayer();
        this.player1.init(1);
        this.player2.init(2);
        this.isOver = false;
        this.winner = -1;
    }

    public synchronized void simulate(final double time) {
        if(!this.isOver) {
            this.newBallMass1 += time * 0.1D;
            if(this.newBallMass1 >= 100.0D) {
                this.newBallMass1 = 100.0D;
            }

            this.newBallMass2 += time * 0.1D;
            if(this.newBallMass2 >= 100.0D) {
                this.newBallMass2 = 100.0D;
            }

            this.player1.move();
            this.player2.move();
            class Simulator {
                public static final int MAX_AFFECT_CNT = 100;
                private int nObj;
                private DisplayObject[] objects;
                private boolean[] isDestroyed;
                private int[] affectCnt;
                private Simulator.Event recentEvent;

                Simulator() {
                }

                private void addAffectCnt(int idx) {
                    ++this.affectCnt[idx];
                    if(this.affectCnt[idx] >= 100) {
                        this.objects[idx].setVel(Vector.ZERO_VECTOR);
                        System.out.println("Too much effect!");
                    }

                }

                private void destroy(int idx) {
                    this.isDestroyed[idx] = true;
                }

                private void clearDestroyedObj() {
                    int newNObj = 0;

                    for(int i = 0; i < this.nObj; ++i) {
                        if(!this.isDestroyed[i]) {
                            this.objects[newNObj] = this.objects[i];
                            this.affectCnt[newNObj] = this.affectCnt[i];
                            ++newNObj;
                        }
                    }

                    this.nObj = newNObj;
                }

                private void shoot(int owner, double mass) {
                    switch(owner) {
                        case 1:
                            GameSituation.this.score1 = GameSituation.this.score1 + mass;
                            GameSituation.this.drawer.twinkleGoalColor(2);
                            break;
                        case 2:
                            GameSituation.this.score2 = GameSituation.this.score2 + mass;
                            GameSituation.this.drawer.twinkleGoalColor(1);
                    }

                }

                private void updateRecentEvent(Simulator.Event e) {
                    if(e.time < this.recentEvent.time) {
                        this.recentEvent = e;
                    }

                }

                private void checkBoundEvent(final int idx) {
                    final DisplayObject obj = this.objects[idx];
                    double EPS = 1.0E-7D;
                    if(this.objects[idx].getVel().getX() < -1.0E-7D) {
                        this.updateRecentEvent(new Simulator.Event((obj.getLeftBorder() - obj.getPos().getX()) / this.objects[idx].getVel().getX(), new Runnable() {
                            public void run() {
                                obj.boundAtLeftBorder();
                                Simulator.this.addAffectCnt(idx);
                            }
                        }));
                    } else if(this.objects[idx].getVel().getX() > 1.0E-7D) {
                        this.updateRecentEvent(new Simulator.Event((obj.getRightBorder() - obj.getPos().getX()) / this.objects[idx].getVel().getX(), new Runnable() {
                            public void run() {
                                obj.boundAtRightBorder();
                                Simulator.this.addAffectCnt(idx);
                            }
                        }));
                    }

                    if(this.objects[idx].getVel().getY() < -1.0E-7D) {
                        this.updateRecentEvent(new Simulator.Event((obj.getTopBorder() - obj.getPos().getY()) / this.objects[idx].getVel().getY(), new Runnable() {
                            public void run() {
                                obj.boundAtTopBorder();
                                Simulator.this.addAffectCnt(idx);
                            }
                        }));
                    } else if(this.objects[idx].getVel().getY() > 1.0E-7D) {
                        this.updateRecentEvent(new Simulator.Event((obj.getBottomBorder() - obj.getPos().getY()) / this.objects[idx].getVel().getY(), new Runnable() {
                            public void run() {
                                obj.boundAtBottomBorder();
                                Simulator.this.addAffectCnt(idx);
                            }
                        }));
                    }

                }

                private void checkScoreEvent(final int idx) {
                    final DisplayObject obj = this.objects[idx];
                    if(obj instanceof Ball) {
                        Ball ball = (Ball)obj;
                        double EPS = 1.0E-7D;
                        if(this.objects[idx].getVel().getX() < -1.0E-7D) {
                            this.updateRecentEvent(new Simulator.Event((50.0D + ball.getRadius() - obj.getPos().getX()) / this.objects[idx].getVel().getX(), new Runnable() {
                                public void run() {
                                    Simulator.this.shoot(2, obj.getMass());
                                    Simulator.this.destroy(idx);
                                }
                            }));
                        } else if(this.objects[idx].getVel().getX() > 1.0E-7D) {
                            this.updateRecentEvent(new Simulator.Event((950.0D - ball.getRadius() - obj.getPos().getX()) / this.objects[idx].getVel().getX(), new Runnable() {
                                public void run() {
                                    Simulator.this.shoot(1, obj.getMass());
                                    Simulator.this.destroy(idx);
                                }
                            }));
                        }

                    }
                }

                private void checkCollideEvent(final int idxA, final int idxB) {
                    if(this.objects[idxA] instanceof Ball) {
                        Ball a = (Ball)this.objects[idxA];
                        CollideMessage msg;
                        class CollideEventHandler implements Runnable {
                            private Vector collideDir;

                            public CollideEventHandler(Vector collideDir) {
                                this.collideDir = collideDir;
                            }

                            public void run() {
                                Vector axisX = Vector.mul(1.0D / Vector.dist(this.collideDir), this.collideDir);
                                Vector axisY = new Vector(-axisX.getY(), axisX.getX());
                                DisplayObject objA = Simulator.this.objects[idxA];
                                DisplayObject objB = Simulator.this.objects[idxB];
                                Vector velA = new Vector(Vector.dot(objA.getVel(), axisX), Vector.dot(objA.getVel(), axisY));
                                Vector velB = new Vector(Vector.dot(objB.getVel(), axisX), Vector.dot(objB.getVel(), axisY));
                                double deltaMass = objA.getMass() - objB.getMass();
                                double sumMass = objA.getMass() + objB.getMass();
                                double newVelA = (deltaMass * velA.getX() + 2.0D * objB.getMass() * velB.getX()) / sumMass;
                                double newVelB = (-deltaMass * velB.getX() + 2.0D * objA.getMass() * velA.getX()) / sumMass;
                                objA.setVel(Vector.add(Vector.mul(newVelA, axisX), Vector.mul(velA.getY(), axisY)));
                                objB.setVel(Vector.add(Vector.mul(newVelB, axisX), Vector.mul(velB.getY(), axisY)));
                                Simulator.this.addAffectCnt(idxA);
                                Simulator.this.addAffectCnt(idxB);
                            }
                        }

                        if(this.objects[idxB] instanceof Ball) {
                            Ball b = (Ball)this.objects[idxB];
                            msg = CollideMessage.getCirCollision(Vector.sub(b.getPos(), a.getPos()), Vector.sub(b.getVel(), a.getVel()), a.getRadius() + b.getRadius());
                            if(msg != null) {
                                this.updateRecentEvent(new Simulator.Event(msg.getTime(), new CollideEventHandler(msg.getCollideDir())));
                            }
                        } else if(this.objects[idxB] instanceof VerticalStick) {
                            VerticalStick b1 = (VerticalStick)this.objects[idxB];
                            msg = CollideMessage.getCapsuleCollision(Vector.sub(b1.getPos(), a.getPos()), Vector.sub(b1.getVel(), a.getVel()), a.getRadius(), b1.getLen());
                            if(msg != null) {
                                this.updateRecentEvent(new Simulator.Event(msg.getTime(), new CollideEventHandler(msg.getCollideDir())));
                            }
                        }
                    } else if(this.objects[idxA] instanceof VerticalStick) {
                        if(this.objects[idxB] instanceof Ball) {
                            this.checkCollideEvent(idxB, idxA);
                        } else if(this.objects[idxB] instanceof VerticalStick) {
                            return;
                        }
                    }

                }

                private void addGravity(int idxA, int idxB) {
                    DisplayObject objA = this.objects[idxA];
                    DisplayObject objB = this.objects[idxB];
                    if(objA instanceof Ball && objB instanceof Ball) {
                        Ball a = (Ball)objA;
                        Ball b = (Ball)objB;
                        double EPS = 1.0E-7D;
                        if(Vector.dist(a.getPos(), b.getPos()) <= a.getRadius() + b.getRadius() - EPS) {
                            return;
                        }

                        double d = Vector.dist(objA.getPos(), objB.getPos());
                        double l = 1.5D * objA.getMass() * objB.getMass() / (d * d);
                        Vector f = Vector.sub(objB.getPos(), objA.getPos());
                        f = Vector.mul(l / Vector.dist(f), f);
                        objA.addForce(f);
                        objB.addForce(Vector.mul(-1.0D, f));
                    }

                }

                public void calc() {
                    this.objects = new DisplayObject[GameSituation.this.displayObjects.size()];
                    this.nObj = this.objects.length;

                    int restTime;
                    for(restTime = 0; restTime < this.nObj; ++restTime) {
                        this.objects[restTime] = GameSituation.this.displayObjects.get(restTime);
                    }

                    for(restTime = 0; restTime < this.nObj; ++restTime) {
                        this.objects[restTime].calcFriction();
                    }

                    double var7 = time;
                    this.affectCnt = new int[this.nObj];
                    this.isDestroyed = new boolean[this.nObj];

                    int i;
                    for(i = 0; i < this.nObj; ++i) {
                        this.affectCnt[i] = 0;
                        this.isDestroyed[i] = false;
                    }

                    for(i = 0; i < this.nObj; ++i) {
                        this.objects[i].addVel(Vector.mul(0.5D * time, this.objects[i].getAcc()));
                    }

                    int j;
                    while(var7 > 0.0D) {
                        this.recentEvent = new Simulator.Event(var7, new Runnable() {
                            public void run() {
                            }
                        });

                        for(i = 0; i < this.nObj; ++i) {
                            this.checkBoundEvent(i);
                            if(!this.isDestroyed[i]) {
                                this.checkScoreEvent(i);
                            }
                        }

                        for(i = 0; i < this.nObj; ++i) {
                            for(j = 0; j < i; ++j) {
                                this.checkCollideEvent(i, j);
                            }
                        }

                        double var8 = this.recentEvent.time;

                        for(int i1 = 0; i1 < this.nObj; ++i1) {
                            DisplayObject obj = this.objects[i1];
                            obj.addPos(Vector.mul(var8, obj.getVel()));
                        }

                        var7 -= this.recentEvent.time;
                        this.recentEvent.handler.run();
                    }

                    for(i = 0; i < this.nObj; ++i) {
                        this.objects[i].addVel(Vector.mul(0.5D * time, this.objects[i].getAcc()));
                    }

                    for(i = 0; i < this.nObj; ++i) {
                        if(Vector.inTheSameDir(this.objects[i].getVel(), this.objects[i].getFriction())) {
                            this.objects[i].setVel(Vector.ZERO_VECTOR);
                        }
                    }

                    this.clearDestroyedObj();

                    for(i = 0; i < this.nObj; ++i) {
                        this.objects[i].clearForce();
                    }

                    for(i = 0; i < this.nObj; ++i) {
                        for(j = 0; j < i; ++j) {
                            this.addGravity(i, j);
                        }
                    }

                    GameSituation.this.displayObjects = new ArrayList<>();

                    for(i = 0; i < this.nObj; ++i) {
                        GameSituation.this.displayObjects.add(this.objects[i]);
                    }

                }

                class Event {
                    public double time;
                    public Runnable handler;

                    Event(double timex, Runnable handler) {
                        this.time = timex;
                        this.handler = handler;
                    }
                }
            }

            (new Simulator()).calc();
            this.restTime -= time;
            if(this.restTime < 0.0D) {
                this.restTime = 0.0D;
                this.gameOver();
            }

        }
    }

    private void gameOver() {
        double EPS = 1.0E-7D;
        if(this.score1 > this.score2 + 1.0E-7D) {
            this.winner = 1;
        } else if(this.score2 > this.score1 + 1.0E-7D) {
            this.winner = 2;
        } else {
            this.winner = 0;
        }

        this.isOver = true;
        this.player1.destroy();
        this.player2.destroy();
    }

    private void addForceToVerticalStick(int owner, Vector f) {
        if(!f.equals(Vector.ZERO_VECTOR)) {
            f = Vector.mul(160.0D / Vector.dist(f), f);

            for (Object obj : this.displayObjects) {
                if (obj instanceof VerticalStick) {
                    VerticalStick stick = (VerticalStick) obj;
                    if (stick.getOwner() == owner) {
                        stick.addForce(f);
                    }
                }
            }

        }
    }

    private void addNewBalls(int owner) {
        ArrayList<Ball> newBalls = new ArrayList<>();

        for (Object obj : this.displayObjects) {
            if (obj instanceof VerticalStick) {
                VerticalStick stick = (VerticalStick) obj;
                if (stick.getOwner() == owner) {
                    Ball ball;
                    switch (owner) {
                        case 1:
                            if (this.newBallMass1 >= 5.0D) {
                                ball = new Ball(this.newBallMass1, stick.getPos(), new Vector(3.0D, 0.0D), Vector.ZERO_VECTOR);
                                ball.addPos(new Vector(ball.getRadius(), 0.0D));
                                newBalls.add(ball);
                            }
                            break;
                        case 2:
                            if (this.newBallMass2 >= 5.0D) {
                                ball = new Ball(this.newBallMass2, stick.getPos(), new Vector(-3.0D, 0.0D), Vector.ZERO_VECTOR);
                                ball.addPos(new Vector(-ball.getRadius(), 0.0D));
                                newBalls.add(ball);
                            }
                    }
                }
            }
        }

        this.displayObjects.addAll(newBalls);
        if(!newBalls.isEmpty()) {
            switch(owner) {
                case 1:
                    this.newBallMass1 = 0.0D;
                    break;
                case 2:
                    this.newBallMass2 = 0.0D;
            }
        }

    }

    synchronized void draw(Graphics2D g) {
        this.drawer.draw(g);
    }

    private class Drawer {
        private Color goal1Color;
        private Color goal2Color;
        private Thread twinkleGoalColor1Thread;
        private Thread twinkleGoalColor2Thread;

        Drawer() {
            this.goal1Color = Color.RED;
            this.goal2Color = Color.BLUE;
            this.twinkleGoalColor1Thread = null;
            this.twinkleGoalColor2Thread = null;
        }

        private Color getGoalColor(int owner) {
            Color var2;
            switch(owner) {
                case 1:
                    var2 = this.goal1Color;
                    synchronized(this.goal1Color) {
                        return this.goal1Color;
                    }
                case 2:
                    var2 = this.goal2Color;
                    synchronized(this.goal2Color) {
                        return this.goal2Color;
                    }
                default:
                    return Color.BLACK;
            }
        }

        private void setGoalColor(int owner, Color goalColor) {
            Color var3;
            switch(owner) {
                case 1:
                    var3 = this.goal1Color;
                    synchronized(this.goal1Color) {
                        this.goal1Color = goalColor;
                        break;
                    }
                case 2:
                    var3 = this.goal2Color;
                    synchronized(this.goal2Color) {
                        this.goal2Color = goalColor;
                    }
            }

        }

        public synchronized void twinkleGoalColor(final int owner) {
            Thread curThread = new Thread(() -> {
                for(int i = 0; i < 12; ++i) {
                    if(i % 2 == 0) {
                        switch(owner) {
                            case 1:
                                Drawer.this.setGoalColor(owner, Color.PINK);
                                break;
                            case 2:
                                Drawer.this.setGoalColor(owner, Color.MAGENTA);
                        }
                    } else {
                        switch(owner) {
                            case 1:
                                Drawer.this.setGoalColor(owner, Color.RED);
                                break;
                            case 2:
                                Drawer.this.setGoalColor(owner, Color.BLUE);
                        }
                    }

                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException var3) {
                        return;
                    }
                }

            }, "Twinkle Goal Color " + owner);
            switch(owner) {
                case 1:
                    if(this.twinkleGoalColor1Thread != null) {
                        this.twinkleGoalColor1Thread.interrupt();

                        try {
                            this.twinkleGoalColor1Thread.join();
                        } catch (InterruptedException var5) {
                            var5.printStackTrace();
                        }
                    }

                    this.twinkleGoalColor1Thread = curThread;
                    break;
                case 2:
                    if(this.twinkleGoalColor2Thread != null) {
                        this.twinkleGoalColor2Thread.interrupt();

                        try {
                            this.twinkleGoalColor2Thread.join();
                        } catch (InterruptedException var4) {
                            var4.printStackTrace();
                        }
                    }

                    this.twinkleGoalColor2Thread = curThread;
            }

            curThread.start();
        }

        private void drawCenterString(Graphics2D g, String str, int x, int y) {
            int width = g.getFontMetrics().stringWidth(str);
            g.drawString(str, x - width / 2, y);
        }

        private void drawRestTime(Graphics2D g) {
            g.setFont(g.getFont().deriveFont(30.0F));
            g.setColor(Color.BLACK);
            this.drawCenterString(g, String.format("Rest Time: %.1f", GameSituation.this.restTime), 500, 1000);
        }

        private void drawGoal(Graphics2D g) {
            g.setStroke(new BasicStroke(3.0F));
            double ratio = GameSituation.this.score1 + GameSituation.this.score2;
            if(ratio < 1000.0D) {
                ratio = 1000.0D;
            }

            ratio = 1000.0D / ratio;
            g.setColor(this.getGoalColor(1));
            g.drawLine(50, 0, 50, 1000);
            g.fillRect(0, (int)(1000.0D - GameSituation.this.score1 * ratio), 50, (int)(GameSituation.this.score1 * ratio));
            g.drawLine(400, 0, 400, 1000);
            g.setColor(this.getGoalColor(2));
            g.drawRect(950, 0, 50, 1000);
            g.fillRect(950, (int)(1000.0D - GameSituation.this.score2 * ratio), 50, (int)(GameSituation.this.score2 * ratio));
            g.drawLine(600, 0, 600, 1000);
        }

        private void drawObjects(Graphics2D g) {

            for (DisplayObject obj : GameSituation.this.displayObjects) {
                obj.draw(g);
            }

        }

        private void drawBallOnStick(Graphics2D g) {

            for (Object obj : GameSituation.this.displayObjects) {
                if (obj instanceof VerticalStick) {
                    VerticalStick stick = (VerticalStick) obj;
                    switch (stick.getOwner()) {
                        case 1:
                            (new Ball(GameSituation.this.newBallMass1, stick.getPos(), new Vector(0.0D, 0.0D), Vector.ZERO_VECTOR)).draw(g);
                            break;
                        case 2:
                            (new Ball(GameSituation.this.newBallMass2, stick.getPos(), new Vector(0.0D, 0.0D), Vector.ZERO_VECTOR)).draw(g);
                    }
                }
            }

        }

        private void drawScore(Graphics2D g) {
            g.setFont(g.getFont().deriveFont(50.0F));
            g.setColor(Color.RED);
            g.drawString(String.format("Score: %.2f", GameSituation.this.score1), 50, 50);
            g.setColor(Color.BLUE);
            g.drawString(String.format("Score: %.2f", GameSituation.this.score2), 600, 50);
        }

        private void drawWinner(Graphics2D g) {
            g.setFont(g.getFont().deriveFont(70.0F));
            switch(GameSituation.this.winner) {
                case 0:
                    g.setColor(Color.GRAY);
                    this.drawCenterString(g, "Draw", 500, 500);
                    break;
                case 1:
                    g.setColor(Color.RED);
                    this.drawCenterString(g, "Winner: Red", 500, 500);
                    break;
                case 2:
                    g.setColor(Color.BLUE);
                    this.drawCenterString(g, "Winner: Blue", 500, 500);
            }

        }

        public void draw(Graphics2D g) {
            this.drawRestTime(g);
            this.drawGoal(g);
            this.drawObjects(g);
            this.drawBallOnStick(g);
            this.drawScore(g);
            if(GameSituation.this.isOver) {
                this.drawWinner(g);
            }

        }
    }

    private class KeyPlayer implements GameSituation.Player, KeyListener {
        private int who;
        private Map<Integer, String> inputMap;
        private Set<String> pressingCmds;
        private Set<String> pressedCmds;

        private KeyPlayer() {
            this.inputMap = new HashMap<>();
            this.pressingCmds = new HashSet<>();
            this.pressedCmds = new HashSet<>();
        }

        public synchronized void keyPressed(KeyEvent e) {
            String cmd = this.inputMap.get(e.getKeyCode());
            if(cmd != null) {
                this.pressingCmds.add(cmd);
                this.pressedCmds.add(cmd);
            }

        }

        public synchronized void keyReleased(KeyEvent e) {
            String cmd = this.inputMap.get(e.getKeyCode());
            if(cmd != null) {
                this.pressingCmds.remove(cmd);
            }

        }

        public synchronized void keyTyped(KeyEvent e) {
        }

        public synchronized void init(int who) {
            this.who = who;
            switch(this.who) {
                case 1:
                    this.inputMap.put(87, "up");
                    this.inputMap.put(65, "left");
                    this.inputMap.put(83, "down");
                    this.inputMap.put(68, "right");
                    this.inputMap.put(32, "fire");
                    break;
                case 2:
                    this.inputMap.put(38, "up");
                    this.inputMap.put(37, "left");
                    this.inputMap.put(40, "down");
                    this.inputMap.put(39, "right");
                    this.inputMap.put(10, "fire");
            }

        }

        public synchronized void move() {
            Vector force = new Vector(0.0D, 0.0D);
            if(this.pressedCmds.contains("up")) {
                force = Vector.add(force, new Vector(0.0D, -1.0D));
            }

            if(this.pressedCmds.contains("left")) {
                force = Vector.add(force, new Vector(-1.0D, 0.0D));
            }

            if(this.pressedCmds.contains("down")) {
                force = Vector.add(force, new Vector(0.0D, 1.0D));
            }

            if(this.pressedCmds.contains("right")) {
                force = Vector.add(force, new Vector(1.0D, 0.0D));
            }

            GameSituation.this.addForceToVerticalStick(this.who, force);
            if(this.pressedCmds.contains("fire")) {
                GameSituation.this.addNewBalls(this.who);
            }

            this.pressedCmds = new HashSet<>(this.pressingCmds);
        }

        public synchronized void destroy() {
        }
    }

    private interface Player {
        void init(int var1);

        void move();

        void destroy();
    }

    public class UserInputHandler implements KeyListener {
        public UserInputHandler() {
        }

        public void keyPressed(KeyEvent e) {
            if(GameSituation.this.player1 instanceof KeyListener) {
                ((KeyListener)GameSituation.this.player1).keyPressed(e);
            }

            if(GameSituation.this.player2 instanceof KeyListener) {
                ((KeyListener)GameSituation.this.player2).keyPressed(e);
            }

        }

        public void keyReleased(KeyEvent e) {
            if(GameSituation.this.player1 instanceof KeyListener) {
                ((KeyListener)GameSituation.this.player1).keyReleased(e);
            }

            if(GameSituation.this.player2 instanceof KeyListener) {
                ((KeyListener)GameSituation.this.player2).keyReleased(e);
            }

        }

        public void keyTyped(KeyEvent e) {
            if(GameSituation.this.player1 instanceof KeyListener) {
                ((KeyListener)GameSituation.this.player1).keyTyped(e);
            }

            if(GameSituation.this.player2 instanceof KeyListener) {
                ((KeyListener)GameSituation.this.player2).keyTyped(e);
            }

        }
    }
}
