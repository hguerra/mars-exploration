package br.com.carneiro.marsexploration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Heitor Carneiro
 */
public class Plateau {
    public final static int MIN_WIDTH = 0;
    public final static int MIN_HEIGHT = 0;

    private final int maxWidth;
    private final int maxHeight;
    private final List<Position> roverPositions;

    public Plateau(int maxWidth, int maxHeight, List<Position> roverPositions) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.roverPositions = roverPositions;
    }

    public Plateau(int maxWidth, int maxHeight) {
        this(maxWidth, maxHeight, new ArrayList<>());
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public List<Position> getRoversPositions() {
        return roverPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plateau plateau = (Plateau) o;
        return maxWidth == plateau.maxWidth && maxHeight == plateau.maxHeight && Objects.equals(roverPositions, plateau.roverPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxWidth, maxHeight, roverPositions);
    }

    @Override
    public String toString() {
        return "Plateau{" +
                "maxWidth=" + maxWidth +
                ", maxHeight=" + maxHeight +
                ", roverPositions=" + roverPositions +
                '}';
    }
}
