package br.com.carneiro.marsexploration;

import java.util.Objects;

/**
 * @author Heitor Carneiro
 */
public class Position {
    private final int x;
    private final int y;
    private final Direction direction;

    public Position(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position that = (Position) o;
        return x == that.x && y == that.y && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    @Override
    public String toString() {
        return String.format("%d %d %s", x, y, direction.name());
    }
}
