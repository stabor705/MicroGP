package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.List;

public abstract class LiteralGeneticNode<T> extends GeneticNode {
    public static final int minHeight = 0;
    private T data;

    public LiteralGeneticNode(T data) {
        super(List.of());
        this.data = data;
    }

    @Override
    protected String getTemplate() {
        return data.toString();
    }
}
