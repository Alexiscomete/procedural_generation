package procedural_generation.climat;

import procedural_generation.noise.ComplexNoiseBuilder;

public class ClimatRuleNoiseBuilder implements ClimatRuleBuilder {
    private final ComplexNoiseBuilder complexNoiseBuilder;
    private final String name;

    public ClimatRuleNoiseBuilder(ComplexNoiseBuilder complexNoiseBuilder, String name) {
        this.complexNoiseBuilder = complexNoiseBuilder;
        this.name = name;
    }

    @Override
    public ClimatRule build(long seed) {
        return new ClimatRuleNoise(complexNoiseBuilder.build(seed), name);
    }
}
