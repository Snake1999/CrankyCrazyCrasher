package com.snake1999.remix.ballgame.physics;

import com.snake1999.remix.ballgame.math.GameBoundingBox;
import com.snake1999.remix.ballgame.math.GameBoundingCircle;
import com.snake1999.remix.ballgame.math.Vector2D;

import java.awt.*;

/**
 * Created by Mulan Lin('Snake1999') on 2016/9/30 10:21.
 */
public class GOBall implements GameObject {
    private Vector2D center;

    private class BallBB implements GameBoundingCircle{
        @Override
        public Vector2D getCenter() {
            return center;
        }
        @Override
        public int getRadius() {
            return calcRadius();
        }
    };

    private GameBoundingBox bb = new BallBB();
    private GameVelocity velocity = new GameVelocity();
    private GameMass mass = new GameMass(0);

    public GOBall(GameMass mass, Vector2D center) {
        this.mass = mass;
        this.center = center;
    }

    @Override
    public GameBoundingBox getBoundingBox() {
        return bb;
    }

    @Override
    public GameMass getMass() {
        return mass;
    }

    @Override
    public GameVelocity getVelocity() {
        return velocity;
    }

    @Override
    public void processVelocity(GameVelocity delta) {
        this.center.add(delta);
    }

    @Override
    public void displayTo(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval(center.getX() - calcRadius(), center.getY() - calcRadius(), (int)(calcRadius() * 2.0D), (int)(calcRadius() * 2.0D));
    }

    private int calcRadius() {
         return mass.intValue() + 5;
    }

    @Override
    public void nextTick(boolean isCollided, GameObject collidingOther) {
        if (!isCollided) return;
        int m1 = mass.intValue();
        int m2 = collidingOther.getMass().intValue();
        int v10 = velocity.distanceTo(Vector2D.ZERO);
        int v20 = collidingOther.getVelocity().distanceTo(Vector2D.ZERO);
        int v1 = ((m1-m2)*v10 + 2*m2*v20)/(m1+m2);
        int v2 = ((m2-m1)*v20 + 2*m1*v10)/(m1+m2);
        Vector2D pp = velocity.copy().add(collidingOther.getVelocity());
        int ppd = pp.distanceTo(Vector2D.ZERO);
        double times1 = 1.0*v1/ppd;
        double times2 = 1.0*v2/ppd;
        velocity.multiply(times1);
        collidingOther.getVelocity().multiply(times2);
    }
}
