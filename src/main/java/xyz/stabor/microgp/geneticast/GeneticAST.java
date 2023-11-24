package xyz.stabor.microgp.geneticast;

public class GeneticAST {
    private GeneticNode root;

    public GeneticAST() {
        this.root = new Program();
    }

    public static GeneticAST generate(int height) {
        GeneticAST ast = new GeneticAST();
        ast.generate(ast.root, height);
        return ast;
    }

    private GeneticNode generate(GeneticNode geneticNode, int height) {
        return generate(geneticNode.generateChild(), height - 1);
    }
}
