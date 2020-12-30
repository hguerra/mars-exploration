package br.com.carneiro.marsexploration;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static br.com.carneiro.marsexploration.Direction.E;
import static br.com.carneiro.marsexploration.Direction.N;
import static br.com.carneiro.marsexploration.Instruction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Heitor Carneiro
 */
class RoverControlServiceTest {

    @Test
    void process() {
        Plateau initialPlateau = new Plateau(5, 5);
        List<RoverInput> inputs = Arrays.asList(
                new RoverInput(new Position(1, 2, N), Arrays.asList(L, M, L, M, L, M, L, M, M)),
                new RoverInput(new Position(3, 3, E), Arrays.asList(M, M, R, M, M, R, M, R, R, M))
        );

        RoverControlService service = new RoverControlService();
        Plateau newPlateau = service.process(initialPlateau, inputs);
        List<Position> newPositions = newPlateau.getRoversPositions();

        assertEquals(newPositions.get(0).toString(), "1 3 N");
        assertEquals(newPositions.get(1).toString(), "5 1 E");
    }

}