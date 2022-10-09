package procedural_generation.climat;

import procedural_generation.noise.ComplexNoiseBuilder;

public class ClimatRuleNoiseBuilder implements ClimatRuleBuilder {
    private final ComplexNoiseBuilder complexNoiseBuilder;

    public ClimatRuleNoiseBuilder(ComplexNoiseBuilder complexNoiseBuilder) {
        this.complexNoiseBuilder = complexNoiseBuilder;
    }

    @Override
    public ClimatRule build(long seed) {
        return new ClimatRuleNoise(complexNoiseBuilder.build(seed));
    }
}
