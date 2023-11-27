package xyz.stabor.microgp.geneticast;

public class GeneticAST {
    private GeneticNode root;

    public GeneticAST() {
        this.root = Program.generate(new GenerationContext(10));
    }
}
