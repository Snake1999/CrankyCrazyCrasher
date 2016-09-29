//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

import java.awt.Color;
import java.awt.Graphics2D;

public class VerticalStick extends DisplayObject {
    private int owner;
    private double len;
    public static final double FRICTION_FACTOR = 0.8D;
    Vector friction;

    public VerticalStick(double mass, Vector pos, Vector vel, Vector force, double len, int owner) {
        super(mass, pos, vel, force);
        this.len = len;
        this.owner = owner;
    }

    public int getOwner() {
        return this.owner;
    }

    public double getLen() {
        return this.len;
    }

    public Vector getFriction() {
        return this.friction;
    }

    public void calcFriction() {
        double l = 0.8D * this.mass * 9.8D;
        Vector dir;
        if(this.vel.equals(Vector.ZERO_VECTOR)) {
            if(Vector.dist(this.force) < l) {
                l = Vector.dist(this.force);
            }

            dir = this.force;
        } else {
            dir = this.vel;
        }

        if(dir.equals(Vector.ZERO_VECTOR)) {
            this.friction = Vector.ZERO_VECTOR;
        } else {
            this.friction = Vector.mul(-l / Vector.dist(dir), dir);
        }

        this.force = Vector.add(this.force, this.friction);
    }

    public double getLeftBorder() {
        switch(this.owner) {
            case 1:
                return 0.0D;
            case 2:
                return 600.0D;
            default:
                return super.getLeftBorder();
        }
    }

    public double getRightBorder() {
        switch(this.owner) {
            case 1:
                return 400.0D;
            case 2:
                return 1000.0D;
            default:
                return super.getLeftBorder();
        }
    }

    public double getTopBorder() {
        return this.len / 2.0D;
    }

    public double getBottomBorder() {
        return 1000.0D - this.len / 2.0D;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)(this.pos.getX() - 2.0D) - 1, (int)(this.pos.getY() - this.len / 2.0D) - 1, 6, (int)this.len + 2);
        switch(this.owner) {
            case 1:
                g.setColor(Color.RED);
                break;
            case 2:
                g.setColor(Color.BLUE);
                break;
            default:
                g.setColor(Color.WHITE);
        }

        g.fillRect((int)(this.pos.getX() - 2.0D), (int)(this.pos.getY() - this.len / 2.0D), 4, (int)this.len);
    }
}
