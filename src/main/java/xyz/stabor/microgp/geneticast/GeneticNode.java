package xyz.stabor.microgp.geneticast;

import java.io.Serializable;
import java.util.*;

public abstract class GeneticNode implements Serializable {
    protected List<GeneticNode> children = new LinkedList<>();
    protected GeneticNode parent;
    protected static Random rng = new Random();

    protected GeneticNode(List<GeneticNode> children) {
        this.children.addAll(children);
        for (GeneticNode child : children) {
            child.parent = this;
        }
    }

    @Override
    public String toString() {
        return String.format(getTemplate(), children.stream().map(GeneticNode::toString).toArray());
    }

    public int getNumberOfNodes() {
        Queue<GeneticNode> queue = new LinkedList<>();
        int count = 0;
        queue.add(this);
        while (!queue.isEmpty()) {
            GeneticNode node = queue.remove();
            count += 1;
            queue.addAll(node.children);
        }
        return count;
    }

    public GeneticNode getNthNode(int n) {
        Queue<GeneticNode> queue = new LinkedList<>();
        int currentIdx = 0;
        queue.add(this);
        while (!queue.isEmpty()) {
            GeneticNode node = queue.remove();
            if (currentIdx == n) {
                return node;
            }
            queue.addAll(node.children);
            currentIdx++;
        }
        return null;
    }

    public int getHeight() {
        return children
                .stream()
                .mapToInt(child -> child.getHeight(1))
                .max()
                .orElse(1);
    }

    public int getHeight(int current) {
        return children
                .stream()
                .mapToInt(child -> child.getHeight(current + 1))
                .max()
                .orElse(current);
    }

    protected abstract String getTemplate();
}

