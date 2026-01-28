package edu.brandeis.cosi103a.ip1;

public abstract class Card {
    private int cost;
    private int value;
    
    public Card(int cost, int value) {
        this.cost = cost;
        this.value = value;
    }
    public int getCost() {
        return cost;
    }
    public int getValue() {
        return value;
    }
}