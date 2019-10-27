package ar.edu.itba.ss;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellIndexMethod {
    private double width;
    private double height;
    private List<Particle> particles;
    private int rows;
    private int cols;
    private double baseX;
    private double baseY;
    private double cellHeight;
    private double cellWidth;
    private boolean borderProperty;
    private double maxReach;
    private Map<Integer, ArrayList<Particle>> cells;


    public CellIndexMethod(double width, double height, List<Particle> particles, double reach) {
        this.width = width;
        this.height = height;
        this.particles = particles;
        this.borderProperty = false;
        this.maxReach = reach;
        this.baseX = 0;
        this.baseY = 0;

        generateCells();
        loadCells();
    }

    public CellIndexMethod(double width, double height, List<Particle> particles, double reach,
                           double baseX, double baseY) {
        this.width = width;
        this.height = height;
        this.particles = particles;
        this.borderProperty = false;
        this.maxReach = reach;
        this.baseX = baseX;
        this.baseY = baseY;

        generateCells();
        loadCells();
    }

    private void generateCells() {
        rows = calculateRowsQty();
        cols = calculateColsQty();
        cellHeight = height / rows;
        cellWidth = width / cols;
        cells = new HashMap<>();
    }

    private int calculateRowsQty() {
        return (int) (height / maxReach);
    }

    private int calculateColsQty() {
        return (int) (width / maxReach);
    }

    private void loadCells() {
        particles.forEach(particle -> {
            Point2D position = particle.getPosition().add(new Point2D(baseX, baseY));
            int row = (int) (position.getY() / cellHeight);
            int col = (int) (position.getX() / cellWidth);
            int cell = getCell(row, col);


            if (cells.containsKey(cell)) {
                cells.get(cell).add(particle);
            }
            else {
                ArrayList<Particle> particlesInCell = new ArrayList<>();
                particlesInCell.add(particle);
                cells.put(cell, particlesInCell);
            }
        });
    }

    public void nextStep(List<Particle> particles) {
        cells.clear();
        this.particles = particles;
        loadCells();
    }

    private int getCell(Particle particle) {
        Point2D position = particle.getPosition().add(new Point2D(baseX, baseY));
        int row = (int) (position.getY() / cellHeight);
        int col = (int) (position.getX() / cellWidth);
        return getCell(row, col);
    }

    public List<Particle> findNeighbors(Particle particle) {
        List<Particle> neighborsInteracting = new ArrayList<>();
        List<Particle> neighbors;
        List<Integer> neighborsCells = getNeighborCells(getCell(particle));
        for (int cell : neighborsCells) {
            neighbors = cells.get(cell);
            if (neighbors != null) {
                for (Particle n : neighbors) {
                    if (isInRadius(particle, n)) {
                        neighborsInteracting.add(n);
                    }
                }
            }
        }

        return neighborsInteracting;
    }

    public List<Integer> findNeighborsIndex(Particle particle) {
        List<Integer> neighborsInteracting = new ArrayList<>();
        List<Particle> neighbors;
        List<Integer> neighborsCells = getNeighborCells(getCell(particle));
        for (int cell : neighborsCells) {
            neighbors = cells.get(cell);
            if (neighbors != null) {
                for (Particle n : neighbors) {
                    if (isInRadius(particle, n)) {
                        neighborsInteracting.add(n.getId());
                    }
                }
            }
        }

        return neighborsInteracting;
    }

    public boolean isInRadius(Particle p1, Particle p2) {
        Point2D p1Position = p1.getPosition().add(new Point2D(baseX, baseY));
        Point2D p2Position = p2.getPosition().add(new Point2D(baseX, baseY));

        double centerDistance = p1Position.distance(p2Position);
        if(p1.equals(p2) || ( !borderProperty && centerDistance > maxReach)) {
            return false;
        }

        return true;
    }

    private boolean isInBorder(Particle p1, Particle p2) {
        Point2D newP1Position, newP2Position;
        for (int i = 1; i <= 3; i++) {
            newP1Position = getPositionWithBorder(p1.getPosition(), i);
            newP2Position = getPositionWithBorder(p2.getPosition(), i);
            if(newP1Position.distance(newP2Position) <= maxReach) {
                return true;
            }
        }
        return false;
    }

    private Point2D getPositionWithBorder(Point2D position, int type) {
        Point2D newPosition = new Point2D(position.getX(), position.getY());
        double x = 0.0;
        double y = 0.0;
        int row = (int) (newPosition.getY() / cellHeight);
        int col = (int) (newPosition.getX() / cellWidth);
        if(type == 1 || type == 3) {
            y = row == 0 ? height : 0.0;
        }
        if(type == 2 || type == 3) {
            x = col == 0 ? width : 0.0;
        }
        return newPosition.add(new Point2D(x,y));
    }


    private List<Integer> getNeighborCells(int cell) {
        int row = cell / cols;
        int col = cell % cols;
        int newRow, newCol, newCell;
        int increments[][] = {{0,1},{-1,0},{0,-1},{1,0},{1,1},{-1,1},{1,-1},{-1,-1}};
        List<Integer> neighbors = new ArrayList<>();
        neighbors.add(cell);

        for (int i = 0; i < 8; i++) {
            newRow = row + increments[i][0];
            newCol = col + increments[i][1];
            if (isValidCell(newRow, newCol)) {
                newCell = getCell(newRow, newCol);
                neighbors.add(newCell);
            }
            else if (borderProperty) {
                newRow = (newRow + rows) % rows;
                newCol = (newCol + cols) % cols;
                newCell = getCell(newRow, newCol);
                neighbors.add(newCell);
            }
        }

        return neighbors;
    }

    public int getMinCellGreaterThan(int cellNumber) {
        int totalCells = rows * cols;
        for(int i = cellNumber + 1; i < totalCells; i++) {
            ArrayList<Particle> result = cells.get(i);
            if(result == null || result.size() == 0) {
                return i;
            }
        }
        //TODO should never happen
        for(int i = cellNumber; i > 0; i--) {
            ArrayList<Particle> result = cells.get(i);
            if(result == null || result.size() == 0) {
                return i;
            }
        }

        System.out.println("\nrompio\n");
        return -1;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    private int getCell(int row, int col) {
        return row * cols + col;
    }

    public int getLastCell(double maxHeight) {
        int row = (int)(maxHeight / cellHeight);
        return row * cols;
    }

    public Point2D getCoordinates(int lastCell) {
        int row = lastCell / cols;
        int col = lastCell % cols;
        double x = col * cellWidth + cellWidth / 2;
        double y = row * cellHeight + cellHeight / 2;
        return new Point2D(x, y);
    }
}
