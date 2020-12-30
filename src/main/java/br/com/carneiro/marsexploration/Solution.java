package br.com.carneiro.marsexploration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Heitor Carneiro
 */
public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        NasaCommandParser parser = new NasaCommandParser();

        String rawPlateau = scan.nextLine();
        Plateau initialPlateau = parser.plateau(rawPlateau);

        List<RoverInput> inputs = new ArrayList<>();

        String rawPosition = null;
        boolean isPositionLine = true;
        boolean hasNext = true;
        while(hasNext && scan.hasNextLine()) {
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
