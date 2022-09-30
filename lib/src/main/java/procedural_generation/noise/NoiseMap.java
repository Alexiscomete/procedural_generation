package procedural_generation.noise;

import de.articdive.jnoise.JNoise;
import de.articdive.jnoise.fade_functions.FadeFunction;
import de.articdive.jnoise.interpolation.Interpolation;

public class NoiseMap implements Node {
    private final JNoise jNoise;

    public NoiseMap(long seed) {
        this.jNoise = JNoise.newBuilder().perlin()
                .setSeed(seed)
                .setInterpolation(Interpolation.COSINE)
                .setFadeFunction(FadeFunction.IMPROVED_PERLIN_NOISE)
                .setFrequency(0.01)
                .build();
    }

    @Override
    public double getValue(double x, double y) {
        return jNoise.getNoise(x, y);
    }
}
