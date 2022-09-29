package procedural_generation.noise;

public class NoiseMapBuilder implements NodeBuilder {

    @Override
    public Node build(long seed) {
        return new NoiseMap(seed);
    }
}
