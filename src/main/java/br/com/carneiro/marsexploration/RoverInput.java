package br.com.carneiro.marsexploration;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Heitor Carneiro
 */
public class RoverInput {
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
