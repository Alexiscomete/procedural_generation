package procedural_generation;

import org.junit.jupiter.api.Test;
import procedural_generation.biomes.Biome;
import procedural_generation.biomes.BiomesManager;
import procedural_generation.biomes.SimpleBiome;
import procedural_generation.climat.ClimatMinMax;
import procedural_generation.climat.ClimatRule;
import procedural_generation.climat.ClimatRuleNoiseBuilder;
import procedural_generation.climat.ClimatRuleType;
import procedural_generation.noise.ComplexNoise;
import procedural_generation.noise.ComplexNoiseBuilder;
import procedural_generation.noise.NoiseMapBuilder;
import procedural_generation.noise.nodes.AddNodeBuilder;
import procedural_generation.noise.nodes.ChangeLocationNodeBuilder;
import procedural_generation.noise.nodes.ChangeSeedNodeBuilder;
import procedural_generation.noise.nodes.Operation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Biomes1Test {
    @Test
    void generate() {
        ArrayList<Biome> biomes = new ArrayList<>();
        ArrayList<ClimatRule> climatRules = new ArrayList<>();
        ComplexNoiseBuilder complexNoiseBuilder = new ComplexNoiseBuilder(
                new AddNodeBuilder(
                        new ChangeLocationNodeBuilder(
                                new ChangeSeedNodeBuilder(
                                        Operation.ADD,
                                        1,
                                        new NoiseMapBuilder(2)
                                ),
                                Operation.DIVIDE,
                                Operation.DIVIDE,
                                100,
                                100
                        ),
                        new ChangeLocationNodeBuilder(
                                new ChangeSeedNodeBuilder(
                                        Operation.ADD,
                                        2,
                                        new NoiseMapBuilder(1)
                                ),
                                Operation.DIVIDE,
                                Operation.DIVIDE,
                                10,
                                10
                        ),
                        new ChangeSeedNodeBuilder(
                                Operation.ADD,
                                3,
                                new NoiseMapBuilder(0.5)
                        ),
                        new ChangeLocationNodeBuilder(
                                new ChangeSeedNodeBuilder(
                                        Operation.ADD,
                                        4,
                                        new NoiseMapBuilder(0.25)
                                ),
                                Operation.MULTIPLY,
                                Operation.MULTIPLY,
                                10,
                                10
                        )
                )
        );

        ClimatRule wind = new ClimatRuleNoiseBuilder(complexNoiseBuilder, "wind", ClimatRuleType.WIND).build(1);
        ClimatRule temperature = new ClimatRuleNoiseBuilder(complexNoiseBuilder, "temperature", ClimatRuleType.TEMPERATURE).build(2);
        ClimatRule humidity = new ClimatRuleNoiseBuilder(complexNoiseBuilder, "humidity", ClimatRuleType.HUMIDITY).build(3);

        climatRules.add(wind);
        climatRules.add(temperature);
        climatRules.add(humidity);

        // generate an image of the noise (gray scale)
        int width = 1000;
        int high = 1000;
        BufferedImage noiseImage = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                int blue = (int) (humidity.getRuleValue(x, y, 0.0) * 255);
                int green = (int) (wind.getRuleValue(x, y, 0.0) * 255);
                int red = (int) (temperature.getRuleValue(x, y, 0.0) * 255);
                try {
                    noiseImage.setRGB(x, y, (new Color(red, green, blue)).getRGB());
                } catch (Exception ignored) {

                }
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(noiseImage, "png", new java.io.File("climat1.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        HashMap<ClimatRule, ClimatMinMax> climatMinMaxes1 = new HashMap<>();
        climatMinMaxes1.put(wind, new ClimatMinMax(0.4, 0.5, 0.1));
        climatMinMaxes1.put(temperature, new ClimatMinMax(0.4, 0.5, 0.2));
        climatMinMaxes1.put(humidity, new ClimatMinMax(0.6, 0.7, 0.1));
        Biome plaine = new SimpleBiome(0.6, "plaine", climatMinMaxes1);
        biomes.add(plaine);

        HashMap<ClimatRule, ClimatMinMax> climatMinMaxes2 = new HashMap<>();
        climatMinMaxes2.put(wind, new ClimatMinMax(0.5, 0.6, 0.1));
        climatMinMaxes2.put(temperature, new ClimatMinMax(0.7, 0.8, 0.2));
        climatMinMaxes2.put(humidity, new ClimatMinMax(0.3, 0.4, 0.1));
        Biome desert = new SimpleBiome(0.7, "desert", climatMinMaxes2);
        biomes.add(desert);

        HashMap<ClimatRule, ClimatMinMax> climatMinMaxes3 = new HashMap<>();
        climatMinMaxes3.put(wind, new ClimatMinMax(0.1, 0.2, 0.2));
        climatMinMaxes3.put(temperature, new ClimatMinMax(0.4, 0.6, 0.1));
        climatMinMaxes3.put(humidity, new ClimatMinMax(0.6, 0.7, 0.1));
        Biome montagne = new SimpleBiome(0.8, "montagne", climatMinMaxes3);
        biomes.add(montagne);

        ComplexNoise complexNoise = complexNoiseBuilder.build(0);

        BiomesManager biomesManager = new BiomesManager(biomes, climatRules, complexNoise);

        // generate an image of the biomes (color)
        BufferedImage biomeImage = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                HashMap<ClimatRule, Double> values = biomesManager.getClimatRuleDoubleHashMap(x, y, 0.0);
                int blue = (int) ((montagne.pourcent(x, y, 0.0, values, complexNoise)) * 255);
                int green = (int) ((plaine.pourcent(x, y, 0.0, values, complexNoise)) * 255);
                int red = (int) ((desert.pourcent(x, y, 0.0, values, complexNoise)) * 255);
                // only keep the max
                int max = Math.max(Math.max(red, green), blue);
                red = max == red ? red : 0;
                green = max == green ? green : 0;
                blue = max == blue ? blue : 0;
                try {
                    biomeImage.setRGB(x, y, (new Color(red, green, blue)).getRGB());
                } catch (Exception ignored) {

                }
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(biomeImage, "png", new java.io.File("biome1.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
