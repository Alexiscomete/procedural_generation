package procedural_generation.biomes;

import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.HashMap;

public class SimpleBiome implements Biome {
    private final double altitude;
    private final String name;

    public SimpleBiome(double altitude, String name) {
        this.altitude = altitude;
        this.name = name;
    }

    @Override
    public double pourcent(double x, double y, double altitude, HashMap<ClimatRule, Double> climatPourcent, ComplexNoise complexNoise) {
        return 0;
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
