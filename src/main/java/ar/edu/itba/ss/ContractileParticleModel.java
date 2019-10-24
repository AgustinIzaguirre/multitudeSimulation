package ar.edu.itba.ss;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContractileParticleModel {
    private static final double MIN_RADIUS      = 0.15;
    private static final double MAX_RADIUS      = 0.32;
    private static final double BETA            = 0.9;
    private static final double TAU             = 0.5;
    private static final double DT              = 0.05;
    private static final double DESIRED_SPEED   = 1.55;
    private static int particlesQuantity;
    private List<Wall> walls;
    private List<Particle> particles;
    private double internalWallRadius;
    private double externalWallRadius;
    private Random random;


    public ContractileParticleModel(double internalWallRadius, double externalWallRadius,
                                        int particlesQuantity, long randomSeed) throws InvalidArgumentException {
        if(externalWallRadius <= internalWallRadius) {
            throw new InvalidArgumentException(new String[]{"Wall radius are invalid"});
        }
        if(randomSeed == 0) {
            random = new Random();
        }
        else {
            random = new Random(randomSeed);
        }

        this.internalWallRadius = internalWallRadius;
        this.externalWallRadius = externalWallRadius;
        walls = new ArrayList<>();
        walls.add(new Wall(internalWallRadius));
        walls.add(new Wall(externalWallRadius));
        this.particlesQuantity = particlesQuantity;
        generateParticles(particlesQuantity);
    }

    private void generateParticles(int particlesQuantity) {
        for(int i = 0; i < particlesQuantity; i++) {

            Point2D newPosition = getNewParticlePosition();
            Point2D initialVelocity = getInitialVelocity(newPosition);
            particles.add(new Particle(i, MAX_RADIUS, newPosition, initialVelocity));
        }
    }



    public Point2D getNewParticlePosition() {
        boolean validPosition = false;
        double maxSpaceAvailable, centerDistance, angle;
        Point2D newParticlePosition = null;
        while(validPosition) {
            maxSpaceAvailable = externalWallRadius - (2 * MAX_RADIUS + internalWallRadius);
            centerDistance = random.nextDouble() * (maxSpaceAvailable) + MAX_RADIUS;
            angle = random.nextDouble() * 2.0 * Math.PI;
            newParticlePosition = getPosition(centerDistance, angle);
            if (!isOverlapping(newParticlePosition)) {
                validPosition = true;
            }
        }
        return newParticlePosition;
    }

    private Point2D getPosition(double centerDistance, double angle) {
        double x = centerDistance * Math.cos(angle);
        double y = centerDistance * Math.sin(angle);
        return new Point2D(x, y);
    }

    private boolean isOverlapping(Point2D newParticlePosition) {
       for(Particle particle : particles) {
            if(getParticlesDistance(particle.getPosition(), newParticlePosition) < 2 * MAX_RADIUS) {
                return true;
            }
        }
        return false;
    }

    private double getParticlesDistance(Point2D position, Point2D newParticlePosition) {
        return position.subtract(newParticlePosition).magnitude();
    }

    private Point2D getInitialVelocity(Point2D newPosition) {
        Point2D normalVersor = newPosition.normalize();
        Point2D tangentVersor = new Point2D(-normalVersor.getY(), normalVersor.getX());
        return tangentVersor.multiply(DESIRED_SPEED);
    }

    public void perform(double maxTime) {

    }
}
