package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LiteralGeneticNode<T> extends GeneticNode {
    private T data;

    @Override
    public GeneticNode generateChild() {
        return null;
    }

    @Override
    public String getText() {
        return data.toString();
    }
}
