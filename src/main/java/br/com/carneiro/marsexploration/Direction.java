package br.com.carneiro.marsexploration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heitor Carneiro
 */
public enum Direction {
    N(0, 0, 1),
    E(90, 1, 0),
    S(180, 0, -1),
    W(270, -1, 0);

    private final int degree;
    private final int x;
    private final int y;
    private static Map<Integer, Direction> values = new HashMap<>();

    Direction(int degree, int x, int y) {
        this.degree = degree;
        this.x = x;
        this.y = y;
    }

    static {
        for (Direction direction : Direction.values()) {
            values.put(direction.getDegree(), direction);
        }
    }

    public static Direction valueOf(int degree) {
        return values.get(degree);
    }

    public int getDegree() {
        return degree;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
