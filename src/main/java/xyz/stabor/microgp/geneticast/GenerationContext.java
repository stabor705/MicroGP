package xyz.stabor.microgp.geneticast;

public record GenerationContext(int maxDepth, int currentDepth) {
    public GenerationContext(int maxDepth) {
        this(maxDepth, 0);
    }

    public int remainingHeight() {
        return maxDepth - currentDepth;
    }

    public GenerationContext deeper() {
        return new GenerationContext(maxDepth, currentDepth + 1);
    }
}
