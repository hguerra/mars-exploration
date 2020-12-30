package br.com.carneiro.marsexploration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heitor Carneiro
 */
public class RoverControlService {

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
