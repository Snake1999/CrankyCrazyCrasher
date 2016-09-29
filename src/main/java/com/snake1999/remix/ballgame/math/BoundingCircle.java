package com.snake1999.remix.ballgame.math;

/**
 * Created by Lin Mulan on 2016/9/29 0029.
 */
public interface BoundingCircle extends BoundingBox2D {

    Vector2D getCenter();

    double getRadius();

//    @Override
//    default boolean isCollidedWith(BoundingBox2D<T> other){
//
//    }
}
