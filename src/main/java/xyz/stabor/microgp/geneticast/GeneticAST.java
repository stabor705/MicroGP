package xyz.stabor.microgp.geneticast;

public class GeneticAST {
    private Node root;

    public GeneticAST() {
        this.root = new Program();
    }

    public static GeneticAST generate(int height) {
        GeneticAST ast = new GeneticAST();
        ast.generate(ast.root, height);
        return ast;
    }

    private Node generate(Node node, int height) {
        return generate(node.generateChild(), height - 1);
    }
}
