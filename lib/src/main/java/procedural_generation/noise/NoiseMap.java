package procedural_generation.noise;

public class NoiseMap implements Node {
    private final int seed;
    private final int jNoise;

    public NoiseMap(int seed) {
        this.seed = seed;
        this.jNoise =
    }

    @Override
    public double getValue(double x, double y) {
        return 0;
    }
}
