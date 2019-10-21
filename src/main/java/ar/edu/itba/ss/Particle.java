package ar.edu.itba.ss;

import javafx.geometry.Point2D;

public class Particle {
    private final int id;
    private final double radius;
    private final Point2D position;
    private final Point2D velocity;
    private final Point2D acceleration;
    private boolean isDown;

    private final Point2D previousAcceleration;


    public Particle(int id, double radius, Point2D position, Point2D velocity ,
                    Point2D acceleration, Point2D previousAcceleration, boolean isDown) {
        this.id = id;
        this.radius = radius;
        this.position = new Point2D(position.getX(), position.getY());
        this.isDown = isDown;
        if(velocity != null) {
            this.velocity = new Point2D(velocity.getX(), velocity.getY());
        }
        else {
            this.velocity = null;
        }
        if(acceleration != null) {
            this.acceleration = new Point2D(acceleration.getX(), acceleration.getY());
        }
        else {
            this.acceleration = null;
        }
        if(previousAcceleration != null) {
            this.previousAcceleration = new Point2D(previousAcceleration.getX(), previousAcceleration.getY());
        }
        else {
            this.previousAcceleration = null;
        }
    }

    public Particle(int id, double radius, Point2D position, Point2D velocity, Point2D acceleration) {
        this(id, radius, position, velocity, acceleration, null, false);
    }

    public Particle copy(Point2D position, Point2D velocity, Point2D acceleration,
                         Point2D previousAcceleration, boolean isDown) {
        return new Particle(id, radius, position, velocity, acceleration,
                previousAcceleration, isDown);

    }

    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public Point2D getPosition() {
        return position;
    }


    public Point2D getVelocity() {
        return velocity;
    }

    public double getSpeed() {
        return getVelocity().magnitude();
    }

    public Point2D getDistance(Particle other) {
        return position.subtract(other.position);
    }

    public Point2D getDistance(Wall wall) {
        return wall.getDistance(this).multiply(-1);
    }

    @Override
    public String toString() {//TODO: maybe add acceleration
        return id + "\t" + radius + "\t" + position.getX() + "\t" + position.getY()
                + "\t" + velocity.getX() + "\t" + velocity.getY() + "\t" + getSpeed();
    }

    public Point2D getAcceleration() {
        return acceleration;
    }

    public Point2D getPreviousAcceleration() {
        return previousAcceleration;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().equals(other.getClass())) {
            return false;
        }

        return getId() == ((Particle) other).getId();
    }

    public boolean IsDown() {
        return isDown;
    }

    public Particle setIsDown(boolean isDown) {
        return copy(position, velocity, acceleration, previousAcceleration, isDown);
    }

    public double getPerimeter() {
        return 2 * Math.PI * getRadius();
    }
}
