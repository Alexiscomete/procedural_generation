package procedural_generation.climat;

import procedural_generation.noise.ComplexNoise;

public class ClimatRuleNoise implements ClimatRule {
    private final ComplexNoise complexNoise;
    private final String name;
    private final ClimatRuleType type;

    public ClimatRuleNoise(ComplexNoise complexNoise, String name, ClimatRuleType type) {
        this.complexNoise = complexNoise;
        this.name = name;
        this.type = type;
    }

    @Override
    public double getRuleValue(double x, double y, double altitude) {
        return complexNoise.getValue(x, y);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ClimatRuleType getType() {
        return type;
    }
}
