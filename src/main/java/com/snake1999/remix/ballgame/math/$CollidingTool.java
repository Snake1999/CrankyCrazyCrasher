package com.snake1999.remix.ballgame.math;


/**
 * Created by Lin Mulan on 2016/9/29 0029.
 */
final class $CollidingTool {
    static int distance(Vector2D a, Vector2D b) {
        return (int) Math.sqrt(square(a.getX() - b.getX()) + square(a.getY() - b.getY()));
    }
    private static int minOf(int a, int b) {
        return Math.min(a,b);
    }
    private static int maxOf(int a, int b) {
        return Math.max(a,b);
    }
    private static int square(int a) {
        return a*a;
    }
    static boolean cRectRect(GameBoundingRectangle a, GameBoundingRectangle b) {
        int axf = minOf(a.getFrom().getX(), a.getTo().getX());
        int axt = maxOf(a.getFrom().getX(), a.getTo().getX());
        int ayf = minOf(a.getFrom().getY(), a.getTo().getY());
        int ayt = maxOf(a.getFrom().getY(), a.getTo().getY());
        int bxf = minOf(b.getFrom().getX(), b.getTo().getX());
        int bxt = maxOf(b.getFrom().getX(), b.getTo().getX());
        int byf = minOf(b.getFrom().getY(), b.getTo().getY());
        int byt = maxOf(b.getFrom().getY(), b.getTo().getY());
        return (axf >= bxf && axf <= bxt && ayf >= byf && ayf <= byt) ||
                (axt >= bxf && axt <= bxt && ayt >= ayf && ayt <= byt);
    }
    static boolean cCirCir(GameBoundingCircle a, GameBoundingCircle b) {
        int dist = a.getCenter().distanceTo(b.getCenter());
        int rr = a.getRadius() + b.getRadius();
        return dist <= rr;
    }
}
