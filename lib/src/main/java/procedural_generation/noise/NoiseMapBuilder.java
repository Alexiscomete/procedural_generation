package procedural_generation.noise;

public class NoiseMapBuilder implements NodeBuilder {
    private final double weight;

    public NoiseMapBuilder(double weight) {
        this.weight = weight;
    }

    @Override
    public Node build(long seed) {
        return new NoiseMap(seed, weight);
    }
}
