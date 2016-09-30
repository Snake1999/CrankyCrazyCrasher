package com.snake1999.remix.ballgame;

import com.snake1999.remix.ballgame.physics.GOBall;
import com.snake1999.remix.ballgame.physics.GameMass;
import com.snake1999.remix.ballgame.physics.GameObject;
import com.snake1999.remix.ballgame.physics.GameVelocity;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mulan Lin('Snake1999') on 2016/9/30 10:57.
 */
public class GameCarrier {

    private static List<GameObject> gos = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        GOBall b1 = new GOBall(new GameMass(300), new V2DBase(300, 200));
        b1.setVelocity(new GameVelocity(20, 0));
        gos.add(b1);
        GOBall b2 = new GOBall(new GameMass(200), new V2DBase(100, 200));
        b2.setVelocity(new GameVelocity(50, 0));
        gos.add(b2);
        MainFrame frame = new MainFrame();
        Graphics2D g2d = (Graphics2D)frame.getGraphics();
        Map<GameObject, Boolean> mask = new HashMap<>();
        for (int tick = 0; tick < 999; tick++) {
            frame.getGraphics().clearRect(0, 0, frame.getWidth(), frame.getHeight());
            gos.forEach(o -> {
                final boolean[] found = {false};
                gos.forEach(other -> {
                    if (other == o) return;
                    if (o.getBoundingBox().isCollidedWith(other.getBoundingBox()) && !mask.getOrDefault(o, false)) {
                        o.nextTick(true, other);
                        mask.put(o, true);
                        found[0] =true;
                    } else {
                        mask.put(o, false);
                    }
                });
                if (!found[0]) {
                    o.nextTick(false, null);
                }
            });
            gos.forEach(GameObject::processVelocity);
            gos.forEach(o -> o.displayTo(g2d));
            Thread.sleep(50);
//            System.out.println(tick + " ："+gos.get(0).toString()+" ："+gos.get(1).toString());
        }
    }
}
