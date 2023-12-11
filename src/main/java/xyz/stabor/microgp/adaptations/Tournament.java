package xyz.stabor.microgp.adaptations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tournament {
    public static List<Integer> selectNewPopulation(List<Double> currentPopulation, int sizeOfTournament){
        List<Integer> newPopulation = new ArrayList<Integer>();
        Random rand = new Random();

        for(int i = 0; i < currentPopulation.size(); ++i){
            List<Integer> tournament = new ArrayList<>();
            for(int j = 0; j < sizeOfTournament; ++j){
                int randIdx = rand.nextInt(currentPopulation.size());
                tournament.add(randIdx);
            }
            int bestIndex = tournament.get(0);
            for(Integer idx : tournament) {
                if (currentPopulation.get(idx) > currentPopulation.get(bestIndex)) {
                    bestIndex = idx;
                }
            }

            newPopulation.add(bestIndex);
        }
        return newPopulation;
    }
}
