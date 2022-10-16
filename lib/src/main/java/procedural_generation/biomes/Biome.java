package procedural_generation.biomes;

import procedural_generation.climat.ClimatRule;
import procedural_generation.noise.ComplexNoise;

import java.util.HashMap;

/**
 * Generate a biome
 */
public interface Biome {
    /**
     * It returns a double value between 0 and 1, which represents the probability of a biome being placed at the given
     * coordinates
     *
     * @param x              the x coordinate of the point
     * @param y              the y coordinate of the block
     * @param altitude       the altitude of the current point
     * @param climatPourcent a HashMap containing the climat rules and their respective percentage.
     * @param complexNoise   The complex noise that will be used to generate the terrain.
     * @return A double.
     */
    double pourcent(double x, double y, double altitude, HashMap<ClimatRule, Double> climatPourcent, ComplexNoise complexNoise);

    /**
     * This function returns a String.
     *
     * @return A string
     */
    String getName();

    /**
     * Change the altitude based on the biome.
     *
     * @param x        The x coordinate of the point you want to get the altitude of.
     * @param y        The y coordinate of the point to get the altitude of.
     * @param altitude The initial altitude of the point.
     * @return The altitude of the point (x,y) in this biome
     */
    double getAltitude(double x, double y, double altitude);
}
