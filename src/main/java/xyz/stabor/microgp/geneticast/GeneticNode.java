package xyz.stabor.microgp.geneticast;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneticNode {
    protected List<GeneticNode> children = new ArrayList<>();

    protected GeneticNode(List<GeneticNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return String.format(getTemplate(), children.stream().map(GeneticNode::toString).toArray());
    }

    protected abstract String getTemplate();
}

