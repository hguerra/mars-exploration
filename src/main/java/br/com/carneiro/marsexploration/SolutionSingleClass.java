package br.com.carneiro.marsexploration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Merge all other files to be run in some solution like HackerRank.
 *
 * @author Heitor Carneiro
 */
public class SolutionSingleClass {

    static enum Direction {
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

    static enum Instruction {
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

    static class Position {
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

    static class Plateau {
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

    static class RoverInput {
        private final Position initialPosition;
        private final List<Instruction> instructions;

        public RoverInput(Position initialPosition, List<Instruction> instructions) {
            this.initialPosition = initialPosition;
            this.instructions = instructions;
        }

        public Position getInitialPosition() {
            return initialPosition;
        }

        public List<Instruction> getInstructions() {
            return instructions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RoverInput that = (RoverInput) o;
            return Objects.equals(initialPosition, that.initialPosition) && Objects.equals(instructions, that.instructions);
        }

        @Override
        public int hashCode() {
            return Objects.hash(initialPosition, instructions);
        }

        @Override
        public String toString() {
            List<String> instructionsNames = instructions.stream().map(Enum::name).collect(Collectors.toList());
            return String.format("%s\n%s", initialPosition.toString(), String.join("", instructionsNames));
        }
    }

    static class RoverControlService {

        public Plateau process(Plateau initialPlateau, List<RoverInput> inputs) {
            List<Position> positions = new ArrayList<>();

            for (RoverInput input : inputs) {
                Position position = input.getInitialPosition();

                for (Instruction instruction : input.getInstructions()) {
                    position = instruction.execute(initialPlateau, position);
                }

                positions.add(position);
            }

            return new Plateau(initialPlateau.getMaxWidth(), initialPlateau.getMaxHeight(), positions);
        }
    }

    static class NasaCommandParser {
        private final static int PLATEAU_COMMANDS_SIZE = 2;
        private final static int POSITION_COMMANDS_SIZE = 3;

        public Plateau plateau(String raw) {
            assertOrThrows("Plateau size can not be null.", null != raw);

            raw = raw.trim();
            assertOrThrows("Plateau size can not be empty.", !raw.isEmpty());

            String[] commands = raw.split(" ");
            assertOrThrows(String.format("Plateau size can not be different from %d, got %d.", PLATEAU_COMMANDS_SIZE, commands.length), PLATEAU_COMMANDS_SIZE == commands.length);

            for (String command : commands) {
                assertOrThrows("Plateau size must be two numbers.", isNumeric(command));
            }

            return new Plateau(Integer.parseInt(commands[0]), Integer.parseInt(commands[1]));
        }

        public RoverInput roverInput(String rawPosition, String rawInstruction) {
            assertOrThrows("Position or Instruction can not be null.", null != rawPosition, null != rawInstruction);

            rawPosition = rawPosition.trim();
            rawInstruction = rawInstruction.trim();
            assertOrThrows("Position or Instruction can not be empty.", !rawPosition.isEmpty(), !rawInstruction.isEmpty());

            String[] positionCommands = rawPosition.split(" ");
            assertOrThrows(String.format("Position size can not be different from %d, got %d.", POSITION_COMMANDS_SIZE, positionCommands.length), POSITION_COMMANDS_SIZE == positionCommands.length);
            assertOrThrows("Position must be two numbers and one possible direction (N, E, W or S).", isNumeric(positionCommands[0]));
            assertOrThrows("Position must be two numbers and one possible direction (N, E, W or S).", isNumeric(positionCommands[1]));

            Direction direction = Direction.valueOf(positionCommands[2]);

            List<Instruction> instructions = new ArrayList<>();
            for (String command : rawInstruction.split("")) {
                Instruction instruction = Instruction.valueOf(command);
                instructions.add(instruction);
            }

            return new RoverInput(
                    new Position(
                            Integer.parseInt(positionCommands[0]),
                            Integer.parseInt(positionCommands[1]),
                            direction
                    ),
                    instructions
            );
        }

        private static void assertOrThrows(String error, boolean... conditions) {
            for (boolean condition : conditions) {
                if (!condition) {
                    throw new IllegalArgumentException(error);
                }
            }
        }

        private static boolean isNumeric(String strNum) {
            if (strNum == null) {
                return false;
            }

            try {
                Double.parseDouble(strNum);
            } catch (NumberFormatException nfe) {
                return false;
            }

            return true;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        NasaCommandParser parser = new NasaCommandParser();

        String rawPlateau = scan.nextLine();
        Plateau initialPlateau = parser.plateau(rawPlateau);

        List<RoverInput> inputs = new ArrayList<>();

        String rawPosition = null;
        boolean isPositionLine = true;
        boolean hasNext = true;
        while (hasNext && scan.hasNextLine()) {
            String line = scan.nextLine();
            if (isPositionLine) {
                rawPosition = line;

                if (rawPosition.trim().isEmpty()) {
                    hasNext = false;
                }
            } else {
                inputs.add(parser.roverInput(rawPosition, line));
                rawPosition = null;
            }

            isPositionLine = !isPositionLine;
        }

        scan.close();

        RoverControlService service = new RoverControlService();
        Plateau newPlateau = service.process(initialPlateau, inputs);

        List<Position> newPositions = newPlateau.getRoversPositions();
        for (Position newPosition : newPositions) {
            System.out.println(newPosition);
        }
    }
}
