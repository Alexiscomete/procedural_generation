package procedural_generation.biomes;

import procedural_generation.climat.ClimatMinMax;
import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.HashMap;

public class SimpleBiome implements Biome {
    private final double altitude;
    private final String name;
    private final HashMap<ClimatRule, ClimatMinMax> climatRules;

    public SimpleBiome(double altitude, String name, HashMap<ClimatRule, ClimatMinMax> climatRules) {
        this.altitude = altitude;
        this.name = name;
        this.climatRules = climatRules;
    }

    @Override
    public double pourcent(double x, double y, double altitude, HashMap<ClimatRule, Double> climatPourcent, ComplexNoise complexNoise) {
        double total = 0;
        for (ClimatRule climatRule : climatRules.keySet()) {
            Double pourcent = climatPourcent.get(climatRule);
            if (pourcent != null) {
                total += 1 - climatRules.get(climatRule).distanceRange(pourcent);
            }
        }
        return total / climatRules.size();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getAltitude(double x, double y, double altitude) {
        return this.altitude;
    }
}
