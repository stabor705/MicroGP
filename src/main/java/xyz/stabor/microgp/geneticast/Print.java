package xyz.stabor.microgp.geneticast;

import java.util.List;

public class Print extends GeneticNode {
    public static final int minHeight = 3;

    protected Print(List<GeneticNode> children) {
        super(children);
    }

    public static Print generate(GenerationContext ctx) {
        return new Print(List.of(Expression.generate(ctx.deeper())));
    }

    @Override
    protected String getTemplate() {
        return "print %s;";
    }
}
