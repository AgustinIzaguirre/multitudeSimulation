package ar.edu.itba.ss;

import javafx.geometry.Point2D;

public class Particle {
    private final int id;
    private final double radius;
    private final Point2D position;
    private final Point2D velocity;
    private final boolean escapeVelocity;



    public Particle(int id, double radius, Point2D position, Point2D velocity, boolean escapeVelocity) {
        this.id = id;
        this.radius = radius;
        this.position = new Point2D(position.getX(), position.getY());
        if(velocity != null) {
            this.velocity = new Point2D(velocity.getX(), velocity.getY());
        }
        else {
            this.velocity = null;
        }
        this.escapeVelocity = escapeVelocity;
    }



    public Particle copy(Point2D position, Point2D velocity, boolean escapeVelocity) {
        return new Particle(id, radius, position, velocity, escapeVelocity);

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

    public boolean isEscapeVelocity() {
        return escapeVelocity;
    }

    public Point2D getTangentVersor() {
        Point2D normalVersor = position.normalize();
        return new Point2D(-normalVersor.getY(), normalVersor.getX());
    }

    public Point2D getDistance(Particle other) {
        return position.subtract(other.position);
    }

    public Point2D getDistance(Wall wall) {
        return wall.getDistance(this).multiply(-1);
    }

    @Override
    public String toString() {
        return id + "\t" + radius + "\t" + position.getX() + "\t" + position.getY()
                + "\t" + velocity.getX() + "\t" + velocity.getY() + "\t" + getSpeed();
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


}
