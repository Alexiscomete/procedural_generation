package procedural_generation.biomes;

import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.ArrayList;
import java.util.HashMap;

public class BiomesManager {

    private final ArrayList<Biome> biomes;
    private final ArrayList<ClimatRule> climatRules;
    private final ComplexNoise altitude;

    public BiomesManager(ArrayList<Biome> biomes, ArrayList<ClimatRule> climatRules, ComplexNoise altitude) {
        this.biomes = biomes;
        this.climatRules = climatRules;
        this.altitude = altitude;
    }

    public void addBiome(Biome biome) {
        biomes.add(biome);
    }

    public void addClimatRule(ClimatRule climatRule) {
        climatRules.add(climatRule);
    }

    public HashMap<Biome, Double> getBiome(double x, double y) {
        // select all biomes with more than 10%
        HashMap<Biome, Double> biomePourcent = new HashMap<>();
        for (Biome biome : biomes) {
            double pourcent = biome.pourcent(x, y, altitude, biomes, climatRules);
            if (pourcent > 0.1) {
                biomePourcent.put(biome, pourcent);
            }
        }
        return biomePourcent;
    }

}
