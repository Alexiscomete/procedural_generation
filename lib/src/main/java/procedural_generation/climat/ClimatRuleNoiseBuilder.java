package procedural_generation.climat;

import procedural_generation.noise.ComplexNoiseBuilder;

public class ClimatRuleNoiseBuilder implements ClimatRuleBuilder {
    private final ComplexNoiseBuilder complexNoiseBuilder;
    private final String name;
    private final ClimatRuleType type;

    public ClimatRuleNoiseBuilder(ComplexNoiseBuilder complexNoiseBuilder, String name, ClimatRuleType type) {
        this.complexNoiseBuilder = complexNoiseBuilder;
        this.name = name;
        this.type = type;
    }

    @Override
    public ClimatRule build(long seed) {
        return new ClimatRuleNoise(complexNoiseBuilder.build(seed), name, type);
    }
}
