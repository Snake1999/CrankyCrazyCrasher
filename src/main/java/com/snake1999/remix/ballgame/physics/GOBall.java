package com.snake1999.remix.ballgame.physics;

import com.snake1999.remix.ballgame.math.GameBoundingBox;
import com.snake1999.remix.ballgame.math.GameBoundingCircle;
import com.snake1999.remix.ballgame.math.Vector2D;

import java.awt.*;

/**
 * Created by Mulan Lin('Snake1999') on 2016/9/30 10:21.
 */
public class GOBall implements GameObject {
    private class BallBB implements GameBoundingCircle{
        @Override
        public Vector2D getCenter() {
            return center;
        }
        @Override
        public int getRadius() {
            return calcRadius();
        }

    }
    private Vector2D center;
    private GameBoundingBox bb = new BallBB();
    private GameVelocity velocity = new GameVelocity();
    private GameMass mass;

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
    public void setVelocity(GameVelocity v) {
        this.velocity = v;
    }

    @Override
    public void processVelocity() {
        this.center.add(getVelocity());
    }

    @Override
    public void displayTo(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)center.getX() - calcRadius(), (int)center.getY() - calcRadius(), (int)(calcRadius() * 2.0D), (int)(calcRadius() * 2.0D));
    }

    private int calcRadius() {
         return (int) (Math.pow(1.0*mass.intValue(), 1.0/3.0) + 2);
    }

    @Override
    public void nextTick(boolean isCollided, GameObject collidingOther) {
        if (!isCollided) return;
        double m1 = mass.intValue();
        double m2 = collidingOther.getMass().intValue();
        double v10 = velocity.distanceTo(Vector2D.ZERO);
        double v20 = collidingOther.getVelocity().distanceTo(Vector2D.ZERO);
        double v1 = ((m1-m2)*v10 + 2*m2*v20)/(m1+m2);
        double v2 = ((m2-m1)*v20 + 2*m1*v10)/(m1+m2);
        Vector2D pp = velocity.copy().add(collidingOther.getVelocity());
        double ppd = pp.distanceTo(Vector2D.ZERO);
        double times1 = 1.0*v1/ppd;
        double times2 = 1.0*v2/ppd;
        System.out.println("t1 "+times1+" t2 "+times2 + " v1 "+v1+" v2 "+v2);
        velocity.multiply(times1);
        //collidingOther.getVelocity().multiply(times2);
    }

    @Override
    public String toString() {
        return super.toString()+" center:" +center.getX()+","+center.getY()+" rad:"+calcRadius()+" v:"+velocity.getX()+","+velocity.getY();
    }
}
