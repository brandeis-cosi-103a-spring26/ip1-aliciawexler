package edu.brandeis.cosi103a.ip1;

/**
 * Represents a player in the game
 */
public class Player_d {
    private String name;
    private int score;

    public Player_d(String name) {
        this.name = name;
        this.score = 0;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}