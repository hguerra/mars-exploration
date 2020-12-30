package br.com.carneiro.marsexploration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Heitor Carneiro
 */
class NasaCommandParserTest {

    @Test
    void plateau() {
        NasaCommandParser parser = new NasaCommandParser();
        Plateau plateau = parser.plateau("5 5");

        assertEquals(5, plateau.getMaxWidth());
        assertEquals(5, plateau.getMaxHeight());
        assertEquals(0, plateau.getRoversPositions().size());

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau("            ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau("5");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau("5 5 5");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau("5 5 5");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.plateau("A B");
        });
    }

    @Test
    void roverInput() {
        NasaCommandParser parser = new NasaCommandParser();

        RoverInput input1 = parser.roverInput("1 2 N", "LMLMLMLMM");
        assertEquals(1, input1.getInitialPosition().getX());
        assertEquals(2, input1.getInitialPosition().getY());
        assertEquals(Direction.N, input1.getInitialPosition().getDirection());
        assertEquals(9, input1.getInstructions().size());
        assertEquals(Instruction.L, input1.getInstructions().get(0));
        assertEquals(Instruction.M, input1.getInstructions().get(8));

        RoverInput input2 = parser.roverInput("3 3 E", "MMRMMRMRRM");
        assertEquals(3, input2.getInitialPosition().getX());
        assertEquals(3, input2.getInitialPosition().getY());
        assertEquals(Direction.E, input2.getInitialPosition().getDirection());
        assertEquals(10, input2.getInstructions().size());
        assertEquals(Instruction.R, input2.getInstructions().get(2));
        assertEquals(Instruction.M, input2.getInstructions().get(9));

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput(null, "LMLMLMLMM");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("LMLMLMLMM", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("1 2 N", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput(null, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("", "");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("1 2 N", "1 2 N");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("LMLMLMLMM", "LMLMLMLMM");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("1 2 N N", "LMLMLMLMM");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("1 2 H", "LMLMLMLMM");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.roverInput("1 2 N", "LMLMLMLMMH");
        });
    }
}
