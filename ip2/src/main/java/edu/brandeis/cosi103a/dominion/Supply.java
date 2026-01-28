package edu.brandeis.cosi103a.ip1;

import java.util.*;
import java.util.function.Supplier;

public class Supply {
    private Map<String, Queue<Card>> cardPiles;
    
    public Supply() {
        this.cardPiles = new HashMap<>();
        initializeSupply();
    }
    
    private void initializeSupply() {
        // Cryptocurrency cards
        cardPiles.put("Bitcoin", createQueue(() -> new CryptocurrencyCard(0, 1), 60));
        cardPiles.put("Ethereum", createQueue(() -> new CryptocurrencyCard(3, 2), 40));
        cardPiles.put("Dogecoin", createQueue(() -> new CryptocurrencyCard(6, 3), 30));


        // Automation cards
        cardPiles.put("Method", createQueue(() -> new AutomationCard(2, 1), 14));
        cardPiles.put("Module", createQueue(() -> new AutomationCard(5, 3), 8));
        cardPiles.put("Framework", createQueue(() -> new AutomationCard(8, 6), 8));
    }
    
    private Queue<Card> createQueue(Supplier<Card> factory, int count) {
        Queue<Card> queue = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            queue.add(factory.get()); // NEW object each time
        }
        return queue;
    }
    
    public Card purchaseCard(String cardName) {
        Queue<Card> pile = cardPiles.get(cardName);
        if (pile != null && !pile.isEmpty()) {
            return pile.poll();
        }
        return null;
    }
    
    public int getCardCost(String cardName) {
        Queue<Card> pile = cardPiles.get(cardName);
        if (pile != null && !pile.isEmpty()) {
            return pile.peek().getCost();
        }
        return -1;
    }
    
    public boolean hasCard(String cardName) {
        Queue<Card> pile = cardPiles.get(cardName);
        return pile != null && !pile.isEmpty();
    }
    
    public List<String> getAvailableCards() {
        List<String> available = new ArrayList<>();
        for (String cardName : cardPiles.keySet()) {
            if (hasCard(cardName)) {
                available.add(cardName);
            }
        }
        return available;
    }
}