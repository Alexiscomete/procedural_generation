package procedural_generation.climat;

import procedural_generation.noise.ComplexNoise;

public class ClimatRuleNoise implements ClimatRule {
    private final ComplexNoise complexNoise;

    public ClimatRuleNoise(ComplexNoise complexNoise) {
        this.complexNoise = complexNoise;
    }

    @Override
    public double getRuleValue(double x, double y, double altitude) {
        return complexNoise.getValue(x, y);
    }
}
