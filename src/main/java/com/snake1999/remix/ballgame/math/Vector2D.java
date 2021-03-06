package com.snake1999.remix.ballgame.math;

import com.snake1999.remix.ballgame.V2DBase;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public interface Vector2D {

    Vector2D ZERO = new V2DBase();

    double getX();

    double getY();

    default double distanceTo(Vector2D a) {
        return $CollidingTool.distance(this, a);
    }

    Vector2D add(Vector2D a);

    Vector2D minus(Vector2D a);

    Vector2D multiply(double a);
}
