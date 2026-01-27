package edu.brandeis.cosi103a.ip1;

import java.util.*;

public class Player {
    private String name;
    private List<Card> drawPile;
    private List<Card> discardPile;
    private List<Card> hand;
    private Supply supply;
    
    public List<Card> getDrawPile() {
        return drawPile;
    }
    public List<Card> getDiscardPile() {
        return discardPile;
    }
    public List<Card> getHand() {
        return hand;
    }
    public Player(String name, Supply supply) {
        this.name = name;
        this.supply = supply;
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.hand = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addCardToDrawPile(Card card) {
        if (card == null) throw new IllegalArgumentException("Cannot add null card to draw pile.");
        drawPile.add(card);
    }
    
    public void addCardToDiscardPile(Card card) {
        if (card == null) throw new IllegalArgumentException("Cannot add null card to draw pile.");
        discardPile.add(card);
    }
    
    public void shuffleDeck() {
        Collections.shuffle(drawPile);
    }
    
    public void dealHand(int handSize) {
        hand.clear();
        for (int i = 0; i < handSize; i++) {
            if (drawPile.isEmpty()) {
                reshuffleDeck();
            }
            if (!drawPile.isEmpty()) {
                hand.add(drawPile.remove(0));
            }
        }
    }
    
    private void reshuffleDeck() {
        if (discardPile.isEmpty()) {
            return;
        }
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile);
    }
    
    public int playCryptocurrencyCards() {
        int total = 0;
        
        // Play all cryptocurrency cards in hand and sum their values
        for (Card card : hand) {
            if (card instanceof CryptocurrencyCard) {
                total += card.getValue();
            }
        }
        
        return total;
    }
    
    public String chooseCardToBuy(int cryptocoins, Supply supply) {
        List<String> affordableCards = new ArrayList<>();
        
        for (String cardName : supply.getAvailableCards()) {
            if (supply.getCardCost(cardName) <= cryptocoins) {
                affordableCards.add(cardName);
            }
        }
        
        if (affordableCards.isEmpty()) {
            return null;
        }
        
        // Simple AI: prioritize Frameworks, then Modules, then Methods, then coins
        String[] priority = {"Framework", "Module", "Method", "Dogecoin", "Ethereum", "Bitcoin"};
        for (String card : priority) {
            if (affordableCards.contains(card)) {
                return card;
            }
        }
        
        return affordableCards.get(0);
    }
    
    public void cleanup(int handSize) {
        // Add current hand to discard pile
        discardPile.addAll(hand);
        hand.clear();
        
        // Deal new hand
        dealHand(handSize);
    }
    
    public int calculateAutomationPoints() {
        int total = 0;
        for (Card card : drawPile) {
            if (card instanceof AutomationCard) {
                total += card.getValue();
            }
        }
        for (Card card : discardPile) {
            if (card instanceof AutomationCard) {
                total += card.getValue();
            }
        }
        for (Card card : hand) {
            if (card instanceof AutomationCard) {
                total += card.getValue();
            }
        }
        return total;
    }
}