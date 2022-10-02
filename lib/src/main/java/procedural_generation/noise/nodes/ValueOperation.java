package procedural_generation.noise.nodes;

public enum ValueOperation {
    POWER {
        @Override
        public double apply(double a, double b) {
            return Math.pow(a, b);
        }
    },
    POWER_START_NEGATIVE {
        @Override
        public double apply(double a, double b) {
            return (Math.pow(a * 2 - 1, b) + 1) / 2;
        }
    },
    POWER_SYMMETRICAL {
        @Override
        public double apply(double a, double b) {
            double h = a * 2 - 1;
            double z = Math.pow(h, b);
            double sign = Math.signum(h);
            return (z * sign + 1) / 2;
        }
    },
    REMOVE_POURCENT {
        @Override
        public double apply(double a, double b) {
            double n = Math.max(Math.min(a, 1 - b), b);
            // normalize the value to be between 0 and 1

            return n / (1 - (2 * b));
        }
    };

    public abstract double apply(double a, double b);
}
