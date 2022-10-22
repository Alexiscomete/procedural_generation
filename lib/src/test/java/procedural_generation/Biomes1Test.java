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
import procedural_generation.noise.nodes.*;

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
        climatMinMaxes1.put(temperature, new ClimatMinMax(0.4, 0.45, 0.4));
        climatMinMaxes1.put(humidity, new ClimatMinMax(0.6, 0.65, 0.4));
        Biome plaine = new SimpleBiome(0.51, "plaine", climatMinMaxes1, 0.1);
        biomes.add(plaine);

        HashMap<ClimatRule, ClimatMinMax> climatMinMaxes2 = new HashMap<>();
        climatMinMaxes2.put(wind, new ClimatMinMax(0.5, 0.6, 0.6));
        climatMinMaxes2.put(temperature, new ClimatMinMax(0.8, 0.9, 0.5));
        climatMinMaxes2.put(humidity, new ClimatMinMax(0.0, 0.4, 0.5));
        Biome desert = new SimpleBiome(0.6, "desert", climatMinMaxes2, 0.1);
        biomes.add(desert);

        HashMap<ClimatRule, ClimatMinMax> climatMinMaxes3 = new HashMap<>();
        climatMinMaxes3.put(wind, new ClimatMinMax(0.6, 0.8, 0.6));
        climatMinMaxes3.put(temperature, new ClimatMinMax(0.0, 0.4, 0.5));
        Biome montagne = new SimpleBiome(0.8, "montagne", climatMinMaxes3, 0.3);
        biomes.add(montagne);

        ComplexNoise complexNoise = complexNoiseBuilder.build(0);

        BiomesManager biomesManager = new BiomesManager(biomes, climatRules, complexNoise);

        // generate an image of the biomes (color)
        BufferedImage biomeImage = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        BufferedImage biomeMontagne = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        BufferedImage biomePlaine = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        BufferedImage biomeDesert = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                HashMap<Biome, Double> biomeValues = biomesManager.getBiomes(x, y, 0.0);
                // get values
                Double montagneValue = biomeValues.get(montagne);
                Double plaineValue = biomeValues.get(plaine);
                Double desertValue = biomeValues.get(desert);
                int blue = montagneValue != null ? (int) (montagneValue * 255) : 0;
                int green = plaineValue != null ? (int) (plaineValue * 255) : 0;
                int red = desertValue != null ? (int) (desertValue * 255) : 0;
                try {
                    biomeMontagne.setRGB(x, y, (new Color(0, 0, blue)).getRGB());
                    biomePlaine.setRGB(x, y, (new Color(0, green, 0)).getRGB());
                    biomeDesert.setRGB(x, y, (new Color(red, 0, 0)).getRGB());
                } catch (Exception ignored) {

                }
                // only keep the max
                /*int base = 185;
                int max = Math.max(Math.max(Math.max(red, green), blue), base);
                if (max != base) {
                    red = max == red ? red : 0;
                    green = max == green ? green : 0;
                    blue = max == blue ? blue : 0;
                }*/
                try {
                    biomeImage.setRGB(x, y, (new Color(red, green, blue)).getRGB());
                } catch (Exception ignored) {

                }
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(biomeImage, "png", new java.io.File("biome1.png"));
            javax.imageio.ImageIO.write(biomeMontagne, "png", new java.io.File("biomeMontagne1.png"));
            javax.imageio.ImageIO.write(biomePlaine, "png", new java.io.File("biomePlaine1.png"));
            javax.imageio.ImageIO.write(biomeDesert, "png", new java.io.File("biomeDesert1.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        BufferedImage alt0 = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                int color = (int) (ValueOperation.REMOVE_POURCENT.apply(complexNoise.getValue(x, y), 0.25) * 255);
                int blue = 0;
                int green = 0;
                int red = 0;
                if (color > 127) {
                    green = 255 - color;
                    if (color > 191) {
                        red = 255 - color;
                        if (color > 223) {
                            blue = color;
                            green = color;
                        }
                    }
                } else {
                    blue = color;
                }
                try {
                    alt0.setRGB(x, y, (new Color(red, green, blue)).getRGB());
                } catch (Exception ignored) {

                }
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(alt0, "png", new java.io.File("alt01.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


        BufferedImage alt = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                int color = (int) (ValueOperation.REMOVE_POURCENT.apply(biomesManager.getFinalAltitude(x, y), 0.1) * 255);
                int blue = 0;
                int green = 0;
                int red = 0;
                if (color > 127) {
                    green = 255 - color;
                    if (color > 191) {
                        red = 255 - color;
                        if (color > 223) {
                            blue = color;
                            green = color;
                        }
                    }
                } else {
                    blue = color;
                }
                try {
                    alt.setRGB(x, y, (new Color(red, green, blue)).getRGB());
                } catch (Exception ignored) {

                }
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(alt, "png", new java.io.File("alt1.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
