package xyz.stabor.microgp.geneticast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Block extends GeneticNode {
    private static final int MAX_STATEMENTS = 10;
    private List<GeneticNode> statements = new ArrayList<>();
    private Random rng = new Random();
    public Block() {
        int numOfStatements = rng.nextInt(MAX_STATEMENTS);
        for (int i = 0; i < numOfStatements; i++) {
            statements.add(Statements.generateRandom());
        }
    }

    @Override
    public GeneticNode generateChild() {
        int roll = rng.nextInt(statements.size());
        return statements.get(roll).generateChild();
    }

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (GeneticNode statement : statements) {
            stringBuilder.append(statement.getText());
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}
