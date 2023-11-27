package xyz.stabor.microgp.geneticast;

import java.util.List;

public class While extends GeneticNode {
    static public final int minHeight = 4;

    protected While(List<GeneticNode> children) {
        super(children);
    }

    public static While generate(GenerationContext ctx) {
        return new While(List.of(Conditions.generate(ctx.deeper()), Block.generate(ctx.deeper())));
    }

    @Override
    protected String getTemplate() {
        return "while %s %s";
    }
}
