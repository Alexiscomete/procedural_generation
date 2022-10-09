package procedural_generation.biomes;

import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.ArrayList;

public interface Biome {
    double pourcent(double x, double y, ComplexNoise altitude, ArrayList<Biome> biomes, ArrayList<ClimatRule> climatRules);
    String getName();
}
