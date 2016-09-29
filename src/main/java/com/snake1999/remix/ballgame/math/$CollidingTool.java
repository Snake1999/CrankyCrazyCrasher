package com.snake1999.remix.ballgame.math;


/**
 * Created by Lin Mulan on 2016/9/29 0029.
 */
final class $CollidingTool {
    static double distance(Vector2D a, Vector2D b) {
        return Math.sqrt(square(a.getX() - b.getX()) + square(a.getY() - b.getY()));
    }
    private static double minOf(double a, double b) {
        return Math.min(a,b);
    }
    private static double maxOf(double a, double b) {
        return Math.max(a,b);
    }
    private static double square(double a) {
        return a*a;
    }
    static boolean cRectRect(BoundingRectangle a, BoundingRectangle b) {
        double axf = minOf(a.getFrom().getX(), a.getTo().getX());
        double axt = maxOf(a.getFrom().getX(), a.getTo().getX());
        double ayf = minOf(a.getFrom().getY(), a.getTo().getY());
        double ayt = maxOf(a.getFrom().getY(), a.getTo().getY());
        double bxf = minOf(b.getFrom().getX(), b.getTo().getX());
        double bxt = maxOf(b.getFrom().getX(), b.getTo().getX());
        double byf = minOf(b.getFrom().getY(), b.getTo().getY());
        double byt = maxOf(b.getFrom().getY(), b.getTo().getY());
        return (axf >= bxf && axf <= bxt && ayf >= byf && ayf <= byt) ||
                (axt >= bxf && axt <= bxt && ayt >= ayf && ayt <= byt);
    }
    static boolean cCirCir(BoundingCircle a, BoundingCircle b) {
        double dist = a.getCenter().distanceTo(b.getCenter());
        double rr = a.getRadius() + b.getRadius();
        return dist <= rr;
    }
}
