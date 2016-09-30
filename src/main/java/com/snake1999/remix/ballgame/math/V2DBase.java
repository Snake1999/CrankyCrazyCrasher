package com.snake1999.remix.ballgame.math;

/**
 * Created by Mulan Lin('Snake1999') on 2016/9/30 10:39.
 */
class V2DBase implements Vector2D {

    private int x=0,y=0;

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
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
