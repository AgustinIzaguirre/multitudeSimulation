package ar.edu.itba.ss;



import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.geometry.Point2D;


public class Wall {
    private double radius;

    public Wall(double radius) throws InvalidArgumentException {
        if (radius > 0) {
            this.radius = radius;
        }
        else {
            throw new InvalidArgumentException(new String[]{"There is no wall"});
        }
    }

    public Point2D getDistance(Particle particle) {
        double particleCenterDistance = particle.getPosition().magnitude();
        double distance = Math.abs(radius - particleCenterDistance);
        Point2D normalVersor = particle.getPosition().normalize();
        return normalVersor.multiply(distance);
    }
}
