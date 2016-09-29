//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.*;

public class Ball extends DisplayObject {
    public static final double DENSITY = 0.1D;

    public Ball(double mass, Vector pos, Vector vel, Vector force) {
        super(mass, pos, vel, force);
    }

    public double getRadius() {
        return Math.sqrt(this.getMass() / 0.3141592653589793D);
    }

    public double getLeftBorder() {
        return this.getRadius();
    }

    public double getRightBorder() {
        return 1000.0D - this.getRadius();
    }

    public double getTopBorder() {
        return this.getRadius();
    }

    public double getBottomBorder() {
        return 1000.0D - this.getRadius();
    }

    public void collide(DisplayObject obj) {
        if(obj instanceof Ball) {
            Ball other = (Ball)obj;
            double o = this.getRadius() + other.getRadius() - Vector.dist(this.getPos(), other.getPos());
            if(o < 0.0D) {
                return;
            }

            Vector f = Vector.sub(this.getPos(), other.getPos());
            f = Vector.mul(2.0D * o / Vector.dist(f), f);
            this.addForce(f);
        } else if(obj instanceof VerticalStick) {
            VerticalStick other1 = (VerticalStick)obj;
            Vector o1 = new Vector(other1.getPos().getX(), this.getPos().getY());
            if(o1.getY() < other1.getPos().getY() - other1.getLen() / 2.0D) {
                o1 = new Vector(o1.getX(), other1.getPos().getY() - other1.getLen() / 2.0D);
            }

            if(o1.getY() > other1.getPos().getY() + other1.getLen() / 2.0D) {
                o1 = new Vector(o1.getX(), other1.getPos().getY() + other1.getLen() / 2.0D);
            }

            double d = this.getRadius() - Vector.dist(this.getPos(), o1);
            if(d < 0.0D) {
                return;
            }

            this.vel = new Vector(-this.vel.getX(), this.vel.getY());
        }

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)(this.pos.getX() - this.getRadius()), (int)(this.pos.getY() - this.getRadius()), (int)(this.getRadius() * 2.0D), (int)(this.getRadius() * 2.0D));
    }
}
