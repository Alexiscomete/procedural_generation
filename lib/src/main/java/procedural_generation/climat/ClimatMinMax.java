package procedural_generation.climat;

public class ClimatMinMax {
    private final double min;
    private final double max;
    private final double acceptDistance;

    public ClimatMinMax(double min, double max, double acceptDistance) {
        this.min = min;
        this.max = max;
        this.acceptDistance = acceptDistance;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getRange() {
        return max - min;
    }

    public boolean isInRange(double value) {
        return value >= min && value <= max;
    }

    public double distanceRange(double value) {
        if (value < min) {
            double distance = min - value;
            if (distance > acceptDistance) {
                return 1;
            }
            return distance / acceptDistance;
        } else if (value > max) {
            double distance = value - max;
            if (distance > acceptDistance) {
                return 1;
            }
            return distance / acceptDistance;
        } else {
            return 0;
        }
    }
}
