package procedural_generation.biomes;

import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.HashMap;

public interface Biome {
    double pourcent(double x, double y, double altitude, HashMap<ClimatRule, Double> climatPourcent, ComplexNoise complexNoise);
    String getName();
    double getAltitude(double x, double y, double altitude);
}
