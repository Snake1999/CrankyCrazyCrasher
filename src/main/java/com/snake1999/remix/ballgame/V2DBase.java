package com.snake1999.remix.ballgame;

import com.snake1999.remix.ballgame.math.Vector2D;

/**
 * Created by Mulan Lin('Snake1999') on 2016/9/30 10:39.
 */
public class V2DBase implements Vector2D {

    private int x=0,y=0;

    public V2DBase(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public V2DBase() {
        this(0, 0);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Vector2D add(Vector2D a) {
        x+=a.getX(); y+=a.getY();
        return this;
    }

    @Override
    public Vector2D minus(Vector2D a) {
        x-=a.getX(); y-=a.getY();
        return this;
    }

    @Override
    public Vector2D multiply(double a) {
        return this;
    }
}
