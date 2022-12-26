package coef;

import org.junit.jupiter.api.Test;

public class ADATest {
    @Test
    void execute() {
        new Zoom(1000.0, 4).genImage("1000");
        new Zoom(200.0, 3).genImage("200");
        new Zoom(10.0, 2).genImage("10");
        new Zoom(5.0, 1).genImage("5");
        new Zoom(1.0, 1).genImage("1");
        new Zoom(0.5, 5).genImage("0.5");
    }
}
