//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snake1999.remix.ballgame$origin;

class CollideMessage {
    private double time;
    private Vector collideDir;

    private CollideMessage(double time, Vector collideDir) {
        this.time = time;
        this.collideDir = collideDir;
    }

    public Vector getCollideDir() {
        return collideDir;
    }

    public double getTime() {
        return time;
    }

    public static CollideMessage getCirCollision(Vector pos, Vector vel, double r) {
        if(vel.equals(Vector.ZERO_VECTOR)) {
            return null;
        } else {
            double area = Vector.cross(pos, Vector.add(pos, vel));
            double h = area / Vector.dist(vel);
            if(Math.abs(h) >= r) {
                return null;
            } else {
                double l = Math.sqrt(r * r - h * h);
                double d = Vector.dist(pos);
                double time = (Math.sqrt(d * d - h * h) - l) / Vector.dist(vel);
                double EPS = 1.0E-7D;
                if(time < -1.0E-7D) {
                    return null;
                } else {
                    if(time < 0.0D) time = 0.0D;

                    Vector o = Vector.mul(h / Vector.dist(vel), new Vector(vel.getY(), -vel.getX()));
                    if(!Vector.inTheSameDir(Vector.sub(o, pos), vel)) {
                        return null;
                    } else {
                        Vector collideDir = Vector.add(pos, Vector.mul(time, vel));
                        return new CollideMessage(time, collideDir);
                    }
                }
            }
        }
    }

    private static CollideMessage getRectCollision(Vector pos, double width, double height, Vector vel) {
        if(vel.equals(Vector.ZERO_VECTOR)) {
            return null;
        } else {
            double EPS = 1.0E-7D;
            CollideMessage msg = null;
            double time;
            Vector newPos;
            if(vel.getX() < -1.0E-7D) {
                time = -(pos.getX() - width / 2.0D) / vel.getX();
                if(time >= -1.0E-7D) {
                    newPos = Vector.add(pos, Vector.mul(time, vel));
                    if(Math.abs(newPos.getX()) <= width / 2.0D + 1.0E-7D && Math.abs(newPos.getY()) <= height / 2.0D + 1.0E-7D) {
                        msg = new CollideMessage(time, new Vector(1.0D, 0.0D));
                    }
                }
            } else if(vel.getX() > 1.0E-7D) {
                time = -(pos.getX() + width / 2.0D) / vel.getX();
                if(time >= -1.0E-7D) {
                    newPos = Vector.add(pos, Vector.mul(time, vel));
                    if(Math.abs(newPos.getX()) <= width / 2.0D + 1.0E-7D && Math.abs(newPos.getY()) <= height / 2.0D + 1.0E-7D) {
                        msg = new CollideMessage(time, new Vector(-1.0D, 0.0D));
                    }
                }
            }

            if(vel.getY() < -1.0E-7D) {
                time = -(pos.getY() - height / 2.0D) / vel.getY();
                if(time >= -1.0E-7D) {
                    newPos = Vector.add(pos, Vector.mul(time, vel));
                    if(Math.abs(newPos.getX()) <= width / 2.0D + 1.0E-7D && Math.abs(newPos.getY()) <= height / 2.0D + 1.0E-7D && (msg == null || msg.getTime() > time)) {
                        msg = new CollideMessage(time, new Vector(0.0D, 1.0D));
                    }
                }
            } else if(vel.getY() > 1.0E-7D) {
                time = -(pos.getY() + width / 2.0D) / vel.getY();
                if(time >= -1.0E-7D) {
                    newPos = Vector.add(pos, Vector.mul(time, vel));
                    if(Math.abs(newPos.getX()) <= width / 2.0D + 1.0E-7D && Math.abs(newPos.getY()) <= height / 2.0D + 1.0E-7D && (msg == null || msg.getTime() > time)) {
                        msg = new CollideMessage(time, new Vector(0.0D, -1.0D));
                    }
                }
            }

            return msg;
        }
    }

    public static CollideMessage getCapsuleCollision(Vector pos, Vector vel, double r, double len) {
        if(vel.equals(Vector.ZERO_VECTOR)) {
            return null;
        } else {
            CollideMessage msg = null;
            CollideMessage ret = getRectCollision(pos, 2.0D * r, len, vel);
            msg = ret;

            ret = getCirCollision(Vector.add(pos, new Vector(0.0D, len / 2.0D)), vel, r);
            if(msg == null || ret != null && ret.getTime() < msg.getTime()) {
                msg = ret;
            }

            ret = getCirCollision(Vector.sub(pos, new Vector(0.0D, len / 2.0D)), vel, r);
            if(msg == null || ret != null && ret.getTime() < msg.getTime()) {
                msg = ret;
            }

            return msg;
        }
    }
}
