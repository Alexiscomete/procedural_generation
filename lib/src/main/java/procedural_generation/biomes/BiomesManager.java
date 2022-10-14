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

    public HashMap<Biome, Double> getBiome(double x, double y, double currentAltitude) {
        // select all biomes with more than 10%
        HashMap<Biome, Double> biomePourcent = new HashMap<>();
        HashMap<ClimatRule, Double> climatPourcent = getClimatRuleDoubleHashMap(x, y, currentAltitude);
        for (Biome biome : biomes) {
            double pourcent = biome.pourcent(x, y, currentAltitude, climatPourcent, altitude);
            if (pourcent > 0.1) {
                biomePourcent.put(biome, pourcent);
            }
        }
        return biomePourcent;
    }

    public HashMap<ClimatRule, Double> getClimatRuleDoubleHashMap(double x, double y, double currentAltitude) {
        // get climat pourcent
        HashMap<ClimatRule, Double> climatPourcent = new HashMap<>();
        for (ClimatRule climatRule : climatRules) {
            climatPourcent.put(climatRule, climatRule.getRuleValue(x, y, currentAltitude));
        }
        return climatPourcent;
    }

    public double getFinalAltitude(double x, double y) {
        double currentAltitude = altitude.getValue(x, y);
        HashMap<Biome, Double> biomePourcent = getBiome(x, y, currentAltitude);
        double finalAltitude = currentAltitude;
        for (Biome biome : biomePourcent.keySet()) {
            finalAltitude += biome.getAltitude(x, y, currentAltitude) * biomePourcent.get(biome);
        }
        // divide by the sum of all pourcent
        double sum = 1;
        for (double pourcent : biomePourcent.values()) {
            sum += pourcent;
        }
        return finalAltitude / sum;
    }
}
