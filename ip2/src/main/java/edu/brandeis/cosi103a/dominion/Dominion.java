package edu.brandeis.cosi103a.ip1;

import java.util.*;

public class Dominion {
    private static final int HAND_SIZE = 5;
    
    private List<Player> players;
    private Supply supply;
    private int currentPlayerIndex;

    public Dominion() {
        this.players = new ArrayList<>();
        this.supply = new Supply();
        this.currentPlayerIndex = 0;
    }

    public void startGame() {
        System.out.println("=== Dominion Game Starting ===\n");
        
        // Initialize 2 players
        Player player1 = new Player("Player 1", supply);
        Player player2 = new Player("Player 2", supply);
        players.add(player1);
        players.add(player2);
        
        // Setup starter decks
        setupStarterDecks(player1);
        setupStarterDecks(player2);
        
        // Randomly choose starting player
        currentPlayerIndex = new Random().nextInt(2);
        System.out.println(players.get(currentPlayerIndex).getName() + " starts first.\n");
        
        // Play until all Frameworks are purchased
        while (supply.hasCard("Framework")) {
            playTurn(players.get(currentPlayerIndex));
            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        }
        
        declareWinner();
    }

    private void setupStarterDecks(Player player) {
        // Add 7 Bitcoins and 3 Methods to starter deck
        for (int i = 0; i < 7; i++) {
            player.addCardToDrawPile(supply.purchaseCard("Bitcoin"));
        }
        for (int i = 0; i < 3; i++) {
            player.addCardToDrawPile(supply.purchaseCard("Method"));
        }
        
        // Shuffle and deal initial hand
        player.shuffleDeck();
        player.dealHand(HAND_SIZE);
    }

    private void playTurn(Player player) {
        System.out.println("\n--- " + player.getName() + "'s Turn ---");
        
        // Buy Phase
        int cryptocoinsAvailable = player.playCryptocurrencyCards();
        System.out.println(player.getName() + " has " + cryptocoinsAvailable + " cryptocoins to spend.");
        
        // Buy a card if possible
        String cardToBuy = player.chooseCardToBuy(cryptocoinsAvailable, supply);
        if (cardToBuy == null) {
            System.out.println(player.getName() + " could not afford any cards.");
            player.cleanup(HAND_SIZE);
            return;
        }
        Card boughtCard = supply.purchaseCard(cardToBuy);
        if (boughtCard == null) {
            // Pile empty between choosing and buying (rare, but possible)
            System.out.println(player.getName() + " tried to buy " + cardToBuy + " but it was unavailable.");
            player.cleanup(HAND_SIZE);
            return;
        }player.addCardToDiscardPile(boughtCard);
        System.out.println(player.getName() + " bought " + cardToBuy);
        if (cardToBuy.equals("Framework")) {
            System.out.println("Frameworks remaining: " + (supply.hasCard("Framework") ? "some left" : "none left"));
        }player.cleanup(HAND_SIZE);
    }

    private void declareWinner() {
        System.out.println("\n=== Game Over ===");
        
        int maxAPs = 0;
        Player winner = null;
        
        for (Player player : players) {
            int aps = player.calculateAutomationPoints();
            System.out.println(player.getName() + " has " + aps + " Automation Points.");
            
            if (aps > maxAPs) {
                maxAPs = aps;
                winner = player;
            }
        }
        
        System.out.println("\n" + winner.getName() + " wins with " + maxAPs + " Automation Points!");
    }

    public static void main(String[] args) {
        Dominion game = new Dominion();
        game.startGame();
    }
}
