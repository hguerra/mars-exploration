package br.com.carneiro.marsexploration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heitor Carneiro
 */
public class NasaCommandParser {
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
