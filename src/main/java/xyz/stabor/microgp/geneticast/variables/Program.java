package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;
import xyz.stabor.microgp.geneticast.unions.Statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program extends GeneticNode {
    public static final int minHeight = 1;
    protected Program(List<GeneticNode> children) {
        super(children);
    }

    static public Program generate(GenerationContext ctx) {
        Random rng = new Random();

        int numOfStatements = rng.nextInt(ctx.maxWidth() - 1) + 1;
        List<GeneticNode> children = new ArrayList<>();
        for (int i = 0; i < numOfStatements; i++) {
            children.add(Statements.getInstance().generate(ctx.deeper()));
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
