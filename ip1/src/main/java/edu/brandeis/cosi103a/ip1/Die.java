package edu.brandeis.cosi103a.ip1;

import java.util.Random;

/**
 * Represents a 6-sided die
 */
public class Die {
    private Random random;
    private static final int SIDES = 6;

    public Die() {
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(SIDES) + 1;
    }
}