package procedural_generation.climat;

import procedural_generation.noise.ComplexNoise;

public class ClimatRuleNoise implements ClimatRule {
    private final ComplexNoise complexNoise;
    private final String name;

    public ClimatRuleNoise(ComplexNoise complexNoise, String name) {
        this.complexNoise = complexNoise;
        this.name = name;
    }

    @Override
    public double getRuleValue(double x, double y, double altitude) {
        return complexNoise.getValue(x, y);
    }

    @Override
    public String getName() {
        return name;
    }
}
