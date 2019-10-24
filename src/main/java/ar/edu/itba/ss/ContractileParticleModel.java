package ar.edu.itba.ss;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.geometry.Point2D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private static final double ESCAPE_SPEED    = DESIRED_SPEED;
    private static final int STEPS_TO_PRINT     = 1;

    private static int particlesQuantity;
    private List<Wall> walls;
    private List<Particle> particles;
    private double internalWallRadius;
    private double externalWallRadius;
    private CellIndexMethod cellIndexMethod;
    private Random random;
    private int idFile;


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
        cellIndexMethod =  new CellIndexMethod(externalWallRadius, externalWallRadius, particles, 2 * MAX_RADIUS);
        idFile = 0;
    }

    private void generateParticles(int particlesQuantity) {
        particles = new ArrayList<>();
        for(int i = 0; i < particlesQuantity; i++) {
            Point2D newPosition = getNewParticlePosition();
            Point2D initialVelocity = getInitialVelocity(newPosition);
            particles.add(new Particle(i, MAX_RADIUS, newPosition, initialVelocity, false));
        }
    }



    public Point2D getNewParticlePosition() {
        boolean validPosition = false;
        double maxSpaceAvailable, centerDistance, angle;
        Point2D newParticlePosition = null;
        while(!validPosition) {
            maxSpaceAvailable = externalWallRadius - (2 * MAX_RADIUS + internalWallRadius);
            centerDistance = random.nextDouble() * (maxSpaceAvailable) + internalWallRadius;
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

    public void perform(double maxTime) throws IOException {
        double time = 0;
        int step = 0;
        BufferedWriter writer = initOutput(step);
        printParticles(writer);
        writer.close();

        while(time < maxTime) {
            writer = initOutput(step);
            particles = findContactsAndUpdateEscapeVelocity(particles);
            particles = updateRadius(particles);
            particles = calculateParticleVelocities(particles);
            particles = updateParticlesPosition(particles);
            cellIndexMethod.nextStep(particles);
            time += DT;
            step++;
            if(step % STEPS_TO_PRINT == 0) {
                System.out.println(time);
                printParticles(writer);
                writer.close();
            }
        }

    }

    private List<Particle> findContactsAndUpdateEscapeVelocity(List<Particle> particles) {
        List<Particle> newParticles = new ArrayList<>();
        for(Particle particle : particles) {
            Point2D escapeVelocity = Point2D.ZERO;
            for(Wall wall : walls) {
                escapeVelocity = escapeVelocity.add(getEscapeVelocity(particle, wall));
            }
            List<Particle> neighbours = cellIndexMethod.findNeighbors(particle);
            for(Particle neighbour : neighbours) {
                escapeVelocity = escapeVelocity.add(getEscapeVelocity(particle, neighbour));
            }
            if(escapeVelocity.getX() == 0 && escapeVelocity.getY() == 0) {
                newParticles.add(new Particle(particle.getId(), particle.getRadius(), particle.getPosition(),
                                                                    particle.getVelocity(), false));
            }
            else {
                escapeVelocity = escapeVelocity.normalize().multiply(ESCAPE_SPEED);
                newParticles.add(new Particle(particle.getId(), MIN_RADIUS, particle.getPosition(),
                                                                    escapeVelocity, true));

            }
        }
        
        return newParticles;
    }

    private Point2D getEscapeVelocity(Particle currentParticle, Particle neighbour) {
        Point2D escapeVector = currentParticle.getPosition().subtract(neighbour.getPosition());
        double distance = escapeVector.magnitude();
        if(distance < currentParticle.getRadius() + neighbour.getRadius()) {
            return escapeVector.normalize();
        }
        else {
            return Point2D.ZERO;
        }
    }

    private Point2D getEscapeVelocity(Particle currentParticle, Wall wall) {
        Point2D escapeVector = currentParticle.getPosition();
        if(wall.getRadius() == externalWallRadius) {
            escapeVector = escapeVector.multiply(-1);
        }
        double distance = wall.getDistance(currentParticle).magnitude();
        if(distance < currentParticle.getRadius()) {
            return escapeVector.normalize();
        }
        else {
            return Point2D.ZERO;
        }
    }

    private List<Particle> updateRadius(List<Particle> particles) {
        List<Particle> newParticles = new ArrayList<>();
        for(Particle particle : particles) {
            double deltaRadius = MAX_RADIUS / (TAU / DT);
            double newRadius = Math.min(MAX_RADIUS, particle.getRadius() + deltaRadius);
            newParticles.add(new Particle(particle.getId(), newRadius, particle.getPosition(),
                    particle.getVelocity(), particle.isEscapeVelocity()));
        }

        return newParticles;
    }

    private List<Particle> calculateParticleVelocities(List<Particle> particles) {
        List<Particle> newParticles = new ArrayList<>();
        for(Particle particle : particles) {
            if(!particle.isEscapeVelocity()) {
                double fraction = (particle.getRadius() - MIN_RADIUS) / (MAX_RADIUS - MIN_RADIUS);
                double speed = Math.pow(fraction, BETA);
                Point2D velocity = particle.getTangentVersor().multiply(speed);
                newParticles.add(new Particle(particle.getId(), particle.getRadius(), particle.getPosition(), velocity, false));
            }
            else {
                newParticles.add(new Particle(particle.getId(), particle.getRadius(), particle.getPosition(), particle.getVelocity(), true));
            }
        }
        return newParticles;
    }

    private List<Particle> updateParticlesPosition(List<Particle> particles) {
        List<Particle> newParticles = new ArrayList<>();
        for(Particle particle : particles) {
            Point2D deltaPosition = particle.getVelocity().multiply(DT);
            Point2D newPosition = particle.getPosition().add(deltaPosition); //TODO maybe validate boundaries?
            newPosition = restrainPosition(newPosition);
            newParticles.add(new Particle(particle.getId(), particle.getRadius(), newPosition, particle.getVelocity(), particle.isEscapeVelocity()));
        }
        return newParticles;
    }

    private Point2D restrainPosition(Point2D newPosition) {
        if(newPosition.magnitude() < internalWallRadius) {
            return newPosition.normalize().multiply(internalWallRadius + MIN_RADIUS);
        }
        else if(newPosition.magnitude() > externalWallRadius) {
            return newPosition.normalize().multiply(externalWallRadius - MIN_RADIUS);
        }
        else {
            return newPosition;
        }
    }

    private BufferedWriter initOutput(int step) throws IOException {
        BufferedWriter writer = null;
        if(step % STEPS_TO_PRINT == 0) {
            writer = new BufferedWriter(new FileWriter("./Output/output" + idFile + ".xyz"));
            idFile++;
            writer.write(particles.size() + "\n");
            writer.write("Lattice=\"" + externalWallRadius + " 0.0 0.0 0.0 " + externalWallRadius + " 0.0 0.0 0.0 1.0\" Properties=Id:R:1:Radius:R:1:Pos:R:2:Velocity:R:2:Speed:R:1 \n");
        }
        return writer;
    }

    private void printParticles(BufferedWriter writer) throws IOException {
        for (Particle p : particles) {
            writer.write(p.toString() + "\n");
        }
    }

}
