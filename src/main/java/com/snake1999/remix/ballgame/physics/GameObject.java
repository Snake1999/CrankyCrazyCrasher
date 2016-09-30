package com.snake1999.remix.ballgame.physics;

import com.snake1999.remix.ballgame.math.GameBoundingBox;

import java.awt.*;

/**
 * Created by Mulan Lin('Snake1999') on 2016/9/30 10:21.
 */
public interface GameObject {

    GameBoundingBox getBoundingBox();

    GameMass getMass();

    GameVelocity getVelocity();

    void setVelocity(GameVelocity v);

    void processVelocity();

    void displayTo(Graphics2D graphics2D);

    void nextTick(boolean isCollided, GameObject collidingOther);
}
