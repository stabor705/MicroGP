package xyz.stabor.microgp.geneticast;

import java.beans.PropertyEditorManager;
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
        int MAX_STATEMENTS = 10;
        Random rng = new Random();

        int numOfStatements = rng.nextInt(MAX_STATEMENTS - 1) + 1;
        List<GeneticNode> children = new ArrayList<>();
        for (int i = 0; i < numOfStatements; i++) {
            children.add(Statements.generate(ctx.deeper()));
        }
        return new Block(children);
    }
}
