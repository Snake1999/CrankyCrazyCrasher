//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

import java.lang.*;

public class Vector extends DisplayObject {
    private double x;
    private double y;
    public static final Vector ZERO_VECTOR = new Vector(0.0D, 0.0D);

    public Vector() {
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public static Vector add(Vector lhs, Vector rhs) {
        return new Vector(lhs.x + rhs.x, lhs.y + rhs.y);
    }

    public static Vector sub(Vector lhs, Vector rhs) {
        return new Vector(lhs.x - rhs.x, lhs.y - rhs.y);
    }

    public static Vector mul(double a, Vector v) {
        return new Vector(a * v.x, a * v.y);
    }

    public static double dot(Vector lhs, Vector rhs) {
        return lhs.x * rhs.x + lhs.y * rhs.y;
    }

    public static double cross(Vector lhs, Vector rhs) {
        return lhs.x * rhs.y - lhs.y * rhs.x;
    }

    public static double dist(Vector v) {
        return Math.hypot(v.x, v.y);
    }

    public static double dist(Vector lhs, Vector rhs) {
        return dist(sub(lhs, rhs));
    }

    public boolean equals(DisplayObject obj) {
        if (!(obj instanceof Vector)) return false;
        double e = 1.0E-7D;
        Vector rhs = (Vector)obj;
        return Math.abs(this.x - rhs.x) <= 1.0E-7D && Math.abs(this.y - rhs.y) <= 1.0E-7D;
    }

    public static boolean inTheSameDir(Vector lhs, Vector rhs) {
        double EPS = 1.0E-7D;
        return dot(lhs, rhs) > 1.0E-7D && Math.abs(cross(lhs, rhs)) <= 1.0E-7D;
    }
}
