package xyz.stabor.microgp.geneticast;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.geneticast.variables.Program;

import java.io.Serializable;
import java.util.Random;

public class GeneticAST implements Serializable {
    private GeneticNode root;
    private Random rng = new Random();

    public GeneticAST(Program root) {
        this.root = root;
    }

    public static GeneticAST generate(int height, int maxVars, int maxConstVal) {
        return new GeneticAST(Program.generate(new GenerationContext(height, maxVars, maxVars, maxConstVal)));
    }

    @Override
    public String toString() {
        return this.root.toString();
    }

    public void mutate(GenerationContext ctx) {
        GeneticNode mutatedNode = selectRandomNode();
        GeneticNode newNode = Genetics.generateReplacingNode(mutatedNode, new GenerationContext(mutatedNode.getHeight(), ctx.maxWidth(), ctx.maxVars(), ctx.maxConstValue()));
        if (newNode == null) {
            return;
        }
        replaceNode(mutatedNode, newNode);
    }

    public void crossover(GeneticAST other, GenerationContext ctx) {
        for (int i = 0; i < 10; i++)  {
            if (attemptCrossover(other, ctx)) {
                return;
            }
        }
    }

    private boolean attemptCrossover(GeneticAST other, GenerationContext ctx) {
        GeneticNode nodeA = selectRandomNode();
        GeneticNode nodeB = other.selectRandomNode();
        if (nodeB.getHeight() - nodeA.getHeight() > ctx.maxDepth() - root.getHeight()) {
            return false;
        }
        if (Genetics.canBeCrossed(nodeA, nodeB)) {
            replaceNode(nodeA, nodeB);
            return true;
        }
        return false;
    }

    private void replaceNode(GeneticNode node, GeneticNode newNode) {
        if (root == node) {
            root = newNode;
            return;
        }

        GeneticNode parent = node.parent;
        if (parent == null){
            return;
        }
        if (parent == newNode) {
            return;
        }
        int crossedNodeIdx = parent.children.indexOf(node);
        if (crossedNodeIdx < 0) {
            return;
        }
        parent.children.set(crossedNodeIdx, newNode);
    }

    private GeneticNode selectRandomNode() {
        int numberOfNodes = this.root.getNumberOfNodes();
        int selectedNodeIdx = rng.nextInt(numberOfNodes);
        return this.root.getNthNode(selectedNodeIdx);
    }

}
