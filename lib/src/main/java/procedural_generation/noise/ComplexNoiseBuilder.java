package procedural_generation.noise;

public class ComplexNoiseBuilder {
    private final NodeBuilder nodeBuilder;

    public ComplexNoiseBuilder(NodeBuilder nodeBuilder) {
        this.nodeBuilder = nodeBuilder;
    }

    public ComplexNoise build(long seed) {
        return new ComplexNoise(nodeBuilder.build(seed));
    }
}
