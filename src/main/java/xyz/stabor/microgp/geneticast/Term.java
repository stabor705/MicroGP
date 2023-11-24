package xyz.stabor.microgp.geneticast;

public class Term extends Arithmetic<GeneticNode> {
    public Term() {
        super(Factors.generateRandom());
    }

    public Term(GeneticNode left) {
        super(left);
    }

    @Override
    protected char[] getAllowedOperationTypes() {
        return new char[]{'*', '/'};
    }

    @Override
    protected GeneticNode getGenerated() {
        return Factors.generateRandom();
    }
}
