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

    public GeneticAST mutate(GenerationContext ctx) {
        GeneticAST ast = SerializationUtils.clone(this);
        GeneticNode mutatedNode = ast.selectRandomNode();
        GeneticNode newNode = Genetics.generateReplacingNode(mutatedNode, new GenerationContext(mutatedNode.getHeight(), ctx.maxWidth(), ctx.maxVars(), ctx.maxConstValue()));
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
