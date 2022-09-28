package procedural_generation.noise;

import de.articdive.jnoise.JNoise;

public class NoiseMap implements Node {
    private final JNoise jNoise;

    public NoiseMap(long seed) {
        this.jNoise = JNoise.newBuilder().perlin()
                .setSeed(seed)
                .build();
    }

    @Override
    public double getValue(double x, double y) {
        return jNoise.getNoise(x, y);
    }
}
