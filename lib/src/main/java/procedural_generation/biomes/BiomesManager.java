package procedural_generation.biomes;

import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * BiomesManager is a class that manage all biomes. You can add biome and get biome at a position. You can also get an altitude at a position.
 */
public class BiomesManager {

    private final ArrayList<Biome> biomes;
    private final ArrayList<ClimatRule> climatRules;
    private final ComplexNoise altitude;

    public BiomesManager(ArrayList<Biome> biomes, ArrayList<ClimatRule> climatRules, ComplexNoise altitude) {
        this.biomes = biomes;
        this.climatRules = climatRules;
        this.altitude = altitude;
    }

    /**
     * Adds a biome to the list of biomes
     *
     * @param biome The biome to add to the list of biomes.
     */
    public void addBiome(Biome biome) {
        biomes.add(biome);
    }

    /**
     * This function adds a climat rule to the list of climat rules.
     *
     * @param climatRule The climatRule to add to the list of climatRules.
     */
    public void addClimatRule(ClimatRule climatRule) {
        climatRules.add(climatRule);
    }

    /**
     * It returns a HashMap of Biome and Double, where the Double is the percentage of the Biome at the given coordinates
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param currentAltitude the altitude of the current point
     * @return A HashMap of Biome and Double.
     */
    public HashMap<Biome, Double> getBiome(double x, double y, double currentAltitude) {
        HashMap<Biome, Double> biomePourcent = new HashMap<>();
        HashMap<ClimatRule, Double> climatPourcent = getClimatRuleDoubleHashMap(x, y, currentAltitude);
        double sum = 0;
        for (Biome biome : biomes) {
            double pourcent = biome.pourcent(x, y, currentAltitude, climatPourcent, altitude);
            if (pourcent > 0.8) {
                biomePourcent.clear();
                biomePourcent.put(biome, pourcent);
                return biomePourcent;
            }

            biomePourcent.put(biome, pourcent);
            sum += pourcent;

        }
        // normalize
        for (Biome biome : biomePourcent.keySet()) {
            double pourcent = biomePourcent.get(biome);
            biomePourcent.replace(biome, pourcent / sum);
        }
        return biomePourcent;
    }

    /**
     * It returns a HashMap of ClimatRule and Double, which is the climat pourcent for each climat rule
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param currentAltitude the altitude of the current point
     * @return A HashMap of ClimatRule and Double.
     */
    public HashMap<ClimatRule, Double> getClimatRuleDoubleHashMap(double x, double y, double currentAltitude) {
        // get climat pourcent
        HashMap<ClimatRule, Double> climatPourcent = new HashMap<>();
        for (ClimatRule climatRule : climatRules) {
            climatPourcent.put(climatRule, climatRule.getRuleValue(x, y, currentAltitude));
        }
        return climatPourcent;
    }

    /**
     * It takes the altitude of the current point, then it gets the biome of the current point, then it gets the altitude
     * of the current point for each biome, then it returns the average of all the altitudes
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return The altitude of the biome at the given coordinates.
     */
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
        if (biomePourcentValue > 0.75) {
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
