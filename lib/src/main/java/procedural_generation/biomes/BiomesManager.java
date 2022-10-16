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
        double sum = 0;
        for (Biome biome : biomes) {
            double pourcent = biome.pourcent(x, y, currentAltitude, climatPourcent, altitude);
            if (pourcent > 0.9) {
                biomePourcent.clear();
                biomePourcent.put(biome, pourcent);
                return biomePourcent;
            }

            biomePourcent.put(biome, pourcent);
            sum += pourcent;

        }
        // normalize
        for (Biome biome : biomePourcent.keySet()) {
            biomePourcent.put(biome, biomePourcent.get(biome) / sum);
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
        // select the biome with the highest pourcent
        Biome biome0 = null;
        double biomePourcentValue = 0.5;
        for (Biome biome1 : biomePourcent.keySet()) {
            if (biomePourcent.get(biome1) > biomePourcentValue) {
                biomePourcentValue = biomePourcent.get(biome1);
                biome0 = biome1;
            }
        }
        if (biome0 == null) {
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
        if (biomePourcentValue > 0.9) {
            return biome0.getAltitude(x, y, currentAltitude);
        }
        double finalAltitude = 0;
        for (Biome biome : biomePourcent.keySet()) {
            finalAltitude += biome.getAltitude(x, y, currentAltitude) * biomePourcent.get(biome);
        }
        // divide by the sum of all pourcent
        double sum = 0;
        for (double pourcent : biomePourcent.values()) {
            sum += pourcent;
        }
        return finalAltitude / sum;
    }
}
