package br.com.carneiro.marsexploration;

/**
 * @author Heitor Carneiro
 */
public enum Instruction {
    L {
        @Override
        Position execute(Plateau plateau, Position position) {
            int initialDegree = position.getDirection().getDegree();
            if (initialDegree == 0) {
                initialDegree = 360;
            }

            Direction newDirection = Direction.valueOf(initialDegree - 90);
            return new Position(position.getX(), position.getY(), newDirection);
        }
    },
    R {
        @Override
        Position execute(Plateau plateau, Position position) {
            int finalDegree = position.getDirection().getDegree() + 90;
            if (finalDegree >= 360) {
                finalDegree = 0;
            }

            Direction newDirection = Direction.valueOf(finalDegree);
            return new Position(position.getX(), position.getY(), newDirection);
        }
    },
    M {
        @Override
        Position execute(Plateau plateau, Position position) {
            Direction direction = position.getDirection();
            int newX = position.getX() + direction.getX();
            int newY = position.getY() + direction.getY();

            if (newX >= Plateau.MIN_WIDTH && newX <= plateau.getMaxWidth() && newY >= Plateau.MIN_HEIGHT && newY <= plateau.getMaxHeight()) {
                return new Position(newX, newY, direction);
            }

            return position;
        }
    };

    abstract Position execute(Plateau plateau, Position position);
}
