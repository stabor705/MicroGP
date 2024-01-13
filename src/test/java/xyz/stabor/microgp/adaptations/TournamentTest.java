package xyz.stabor.microgp.adaptations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TournamentTest {

    @Test
    public void testSelectNewPopulation_Size() {
        List<Double> currentPopulation = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        int sizeOfTournament = 3;
        List<Integer> newPopulation = Tournament.selectNewPopulation(currentPopulation, sizeOfTournament);

        assertEquals(currentPopulation.size(), newPopulation.size(), "The size of new population should be equal to the size of current population.");
    }

    @Test
    public void testSelectNewPopulation_IndicesInRange() {
        List<Double> currentPopulation = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        int sizeOfTournament = 3;
        List<Integer> newPopulation = Tournament.selectNewPopulation(currentPopulation, sizeOfTournament);

        for(Integer idx : newPopulation) {
            assertTrue(idx >= 0 && idx < currentPopulation.size(), "All indices in the new population should be within the range of the current population.");
        }
    }

    @Test
    public void testSelectNewPopulation_BestIndividualsSelected() {
        Random rand = new Random();
        List<Double> currentPopulation = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            currentPopulation.add(rand.nextDouble()); // Populate the list with random doubles
        }
        double sumOfPopulation = currentPopulation.stream().mapToDouble(Double::doubleValue).sum();
        int sizeOfTournament = 2;
        List<Integer> newPopulation = Tournament.selectNewPopulation(currentPopulation, sizeOfTournament);
        double[] sumOfNewPopulation = new double[1];
        newPopulation.forEach(value -> sumOfNewPopulation[0] += currentPopulation.get(value));
         assertTrue(sumOfNewPopulation[0] > sumOfPopulation);

    }
}
