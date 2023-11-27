package xyz.stabor.microgp.geneticast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class Program extends GeneticNode {
    public static final int minHeight = 1;
    protected Program(List<GeneticNode> children) {
        super(children);
    }

    static public Program generate(GenerationContext ctx) {
        int MAX_STATEMENTS = 10;
        Random rng = new Random();

        int numOfStatements = rng.nextInt(MAX_STATEMENTS - 1) + 1;
        List<GeneticNode> children = new ArrayList<>();
        for (int i = 0; i < numOfStatements; i++) {
            children.add(Statements.generate(ctx.deeper()));
        }
        return new Program(children);


    }

    @Override
    protected String getTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < children.size(); i++) {
            stringBuilder.append("%s");
        }
        return stringBuilder.toString();
    }
}
