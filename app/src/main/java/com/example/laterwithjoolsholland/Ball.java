package com.example.laterwithjoolsholland;

class Ball {
    private double x;
    private double y;
    private double velocity;
    private double direction;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public Ball(double x, double y, double velocity, double direction) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.direction = direction;
    }
}
