package com.snake1999.remix.ballgame.math;

/**
 * Created by Lin Mulan on 2016/9/29 0029.
 */
public interface GameBoundingCircle extends GameBoundingBox {

    Vector2D getCenter();

    int getRadius();

    @Override
    default boolean isCollidedWith(GameBoundingBox other){
        if (other instanceof GameBoundingCircle) {
            return $CollidingTool.cCirCir(this, (GameBoundingCircle) other);
        } else return false;
    }
}
