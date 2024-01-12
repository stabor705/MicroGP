package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;
import xyz.stabor.microgp.geneticast.unions.Statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class Block extends GeneticNode {
    public static final int minHeight = 2;
    protected Block(List<GeneticNode> children) {
        super(children);
    }

    @Override
    protected String getTemplate() {
        StringJoiner stringJoiner = new StringJoiner("", "{", "}");
        for (int i = 0; i < children.size(); i++) {
            stringJoiner.add("%s");
        }
        return stringJoiner.toString();
    }

    static public Block generate(GenerationContext ctx) {
        Random rng = new Random();

        int numOfStatements = rng.nextInt(ctx.maxWidth() - 1) + 1;
        List<GeneticNode> children = new ArrayList<>();
        for (int i = 0; i < numOfStatements; i++) {
            children.add(Statements.getInstance().generate(ctx.deeper()));
        }
        return new Block(children);
    }
}
