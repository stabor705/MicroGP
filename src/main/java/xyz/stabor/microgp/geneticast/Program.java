package xyz.stabor.microgp.geneticast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class Program extends GeneticNode {
    private List<GeneticNode> statements = new ArrayList<>();
    private Random rng = new Random();
    @Override
    public GeneticNode generateChild() {
        if (rng.nextBoolean() && !statements.isEmpty()) {
            return expandRandomStatement();
        } else {
            return addNewStatement();
        }
    }

    private GeneticNode expandRandomStatement() {
        return statements.get(rng.nextInt(statements.size())).generateChild();
    }

    private GeneticNode addNewStatement() {
        GeneticNode geneticNode = Statements.generateRandom();
        statements.add(geneticNode);
        return geneticNode;
    }

    @Override
    public String getText() {
        StringJoiner joiner = new StringJoiner("");
        for (GeneticNode statement : statements) {
            joiner.add(statement.getText());
        }
        return joiner.toString();
    }
}
