package procedural_generation.biomes;

import procedural_generation.climat.ClimatMinMax;
import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.HashMap;

/**
 * Generate a biome with a simple algorithm. It is based on the climat rules and min-max : you have a min value and a max value for each biome. The altitude ist constant.
 */
public class SimpleBiome implements Biome {
    private final double altitude;
    private final String name;
    private final HashMap<ClimatRule, ClimatMinMax> climatRules;
    private final double acceptDistanceAltitude;

    public SimpleBiome(double altitude, String name, HashMap<ClimatRule, ClimatMinMax> climatRules, double acceptDistanceAltitude) {
        this.altitude = altitude;
        this.name = name;
        this.climatRules = climatRules;
        this.acceptDistanceAltitude = acceptDistanceAltitude;
    }

    @Override
    public double pourcent(double x, double y, double altitude, HashMap<ClimatRule, Double> climatPourcent, ComplexNoise complexNoise) {
        double total = 0;
        if (altitude > this.altitude) {
            total += 1 - (altitude - this.altitude) / acceptDistanceAltitude;
            if (total < 0) {
                total = 0;
            }
        } else if (altitude < this.altitude) {
            total += 1 - (this.altitude - altitude) / acceptDistanceAltitude;
            if (total < 0) {
                total = 0;
            }
        }
        for (ClimatRule climatRule : climatRules.keySet()) {
            Double pourcent = climatPourcent.get(climatRule);
            if (pourcent == null) {
                pourcent = 0.0;
            }
            total += 1 - (Math.pow(climatRules.get(climatRule).distanceRange(pourcent), 1.5));
        }
        return total / (climatRules.size() + 1);
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
