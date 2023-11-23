package xyz.stabor.microgp.geneticast;

public class Term extends Arithmetic<Node> {
    public Term() {
        super(Factors.generateRandom());
    }

    public Term(Node left) {
        super(left);
    }

    @Override
    protected char[] getAllowedOperationTypes() {
        return new char[]{'*', '/'};
    }

    @Override
    protected Node getGenerated() {
        return Factors.generateRandom();
    }
}
