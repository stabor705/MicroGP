package xyz.stabor.microgp.geneticast;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.geneticast.variables.Program;

import java.io.Serializable;
import java.util.Random;

public class GeneticAST implements Serializable {
    private GeneticNode root;
    private Random rng = new Random();

    private GeneticAST(Program root) {
        this.root = root;
    }

    public static GeneticAST generate(int height) {
        return new GeneticAST(Program.generate(new GenerationContext(height)));
    }

    @Override
    public String toString() {
        return this.root.toString();
    }

    private GeneticAST(GeneticNode program) {
        this.root = program;
    }

    public GeneticAST mutated() {
        GeneticAST ast = SerializationUtils.clone(this);
        GeneticNode mutatedNode = ast.selectRandomNode();
        GeneticNode newNode = Genetics.generateReplacingNode(mutatedNode, new GenerationContext(mutatedNode.getHeight()));
        ast.replaceNode(mutatedNode, newNode);
        return ast;
    }

    public GeneticAST crossover(GeneticAST other) {
        GeneticAST ast = SerializationUtils.clone(this);
        for (int i = 0; i < 100; i++)  {
            if (ast.attemptCrossover(other)) {
                return ast;
            }
        }
        return null;
    }

    private boolean attemptCrossover(GeneticAST other) {
        GeneticNode nodeA = selectRandomNode();
        GeneticNode nodeB = other.selectRandomNode();
        if (Genetics.canBeCrossed(nodeA, nodeB)) {
            replaceNode(nodeA, nodeB);
            replaceNode(nodeB, nodeA);
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
        int mutatedNodeIdx = parent.children.indexOf(node);
        parent.children.remove(node);
        parent.children.add(mutatedNodeIdx, newNode);
    }

    private GeneticNode selectRandomNode() {
        int numberOfNodes = this.root.getNumberOfNodes();
        int selectedNodeIdx = rng.nextInt(numberOfNodes);
        return this.root.getNthNode(selectedNodeIdx);
    }

}
