package ar.edu.itba.ss;



import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.geometry.Point2D;


public class Wall {
    private Point2D start;
    private Point2D end;

    public Wall(Point2D point1, Point2D point2) throws InvalidArgumentException {
        if ((point1.getY() == point2.getY()) && (point1.getX() < point2.getX()) ||
                (point1.getX() == point2.getX()) && (point1.getY() < point2.getY())) {
            start = point1;
            end   = point2;
        }
        else if((point1.getY() == point2.getY()) && (point1.getX() > point2.getX()) ||
                (point1.getX() == point2.getX()) && (point1.getY() > point2.getY())) {
            start = point2;
            end   = point1;
        }
        else {
            throw new InvalidArgumentException(new String[]{"There is no wall"});
        }
    }

    public Point2D getDistance(Particle particle) {
        if(start.getX() == end.getX() &&
                start.getY() < particle.getPosition().getY() &&
                particle.getPosition().getY() < end.getY()) {
            return new Point2D(particle.getPosition().getX() - start.getX(), 0);
        }
        else if(start.getY() == end.getY() &&
                start.getX() < particle.getPosition().getX() &&
                particle.getPosition().getX() < end.getX()) {
            return new Point2D(0, particle.getPosition().getY() - start.getY());
        }
        else {
            Point2D distanceToStart = start.subtract(particle.getPosition());
            Point2D distanceTOEnd   = end.subtract(particle.getPosition());
            return (distanceTOEnd.magnitude() < distanceToStart.magnitude()) ? distanceTOEnd.multiply(-1) : distanceToStart.multiply(-1);
        }
    }
}
