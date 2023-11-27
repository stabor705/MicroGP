package xyz.stabor.microgp.geneticast;

import java.util.List;

public class Read extends GeneticNode {
    public static final int minHeight = 1;

    protected Read(List<GeneticNode> children) {
        super(children);
    }

    public static Read generate(GenerationContext ctx) {
        return new Read(List.of(Factors.Identifier.generate(ctx.deeper())));
    }

    @Override
    protected String getTemplate() {
        return "read %s;";
    }
}
