//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

import java.awt.Color;
import java.awt.Graphics2D;

public class DisplayObject {
    protected double mass;
    protected Vector pos;
    protected Vector vel;
    protected Vector force;
    public static final double GRAVITATIONAL_ACCELERATION = 9.8D;
    public static final double GRAVITATIONAL_CONSTANT = 1.5D;

    public DisplayObject() {
        this(0,null,null,null);
    }

    public DisplayObject(double mass, Vector pos, Vector vel, Vector force) {
        this.mass = mass;
        this.pos = pos;
        this.vel = vel;
        this.force = force;
    }

    public Vector getPos() {
        return this.pos;
    }

    public Vector getVel() {
        return this.vel;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    public Vector getForce() {
        return this.force;
    }

    public double getMass() {
        return this.mass;
    }

    public Vector getFriction() {
        return Vector.ZERO_VECTOR;
    }

    public void calcFriction() {
    }

    public Vector getAcc() {
        return Vector.mul(1.0D / this.mass, this.force);
    }

    public double getLeftBorder() {
        return 0.0D;
    }

    public double getRightBorder() {
        return 1000.0D;
    }

    public double getTopBorder() {
        return 0.0D;
    }

    public double getBottomBorder() {
        return 1000.0D;
    }

    public void boundAtLeftBorder() {
        this.vel = new Vector(-this.vel.getX() * 0.5D, this.vel.getY());
    }

    public void boundAtRightBorder() {
        this.vel = new Vector(-this.vel.getX() * 0.5D, this.vel.getY());
    }

    public void boundAtTopBorder() {
        this.vel = new Vector(this.vel.getX(), -this.vel.getY() * 0.5D);
    }

    public void boundAtBottomBorder() {
        this.vel = new Vector(this.vel.getX(), -this.vel.getY() * 0.5D);
    }

    public void addPos(Vector pos) {
        this.pos = Vector.add(this.pos, pos);
    }

    public void addVel(Vector vel) {
        this.vel = Vector.add(this.vel, vel);
    }

    public void addForce(Vector force) {
        this.force = Vector.add(this.force, force);
    }

    public void clearForce() {
        this.force = Vector.ZERO_VECTOR;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)this.pos.getX() - 5, (int)this.pos.getY() - 5, 10, 10);
    }
}
