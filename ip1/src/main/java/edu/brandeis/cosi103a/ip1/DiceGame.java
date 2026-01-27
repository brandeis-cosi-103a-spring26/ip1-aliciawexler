package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;

/**
 * Manages the overall game flow
 */
public class DiceGame {
    private static final int TURNS_PER_PLAYER = 10;
    private Player_d player1;
    private Player_d player2;
    private Die die;
    private Scanner scanner;

    public DiceGame() {
        this.die = new Die();
        this.scanner = new Scanner(System.in);
        this.player1 = new Player_d("Player 1");
        this.player2 = new Player_d("Player 2");
    }

    public void play() {
        System.out.println("Welcome to the Dice Game!");
        System.out.println("Each player gets " + TURNS_PER_PLAYER + " turns.\n");

        for (int turn = 1; turn <= TURNS_PER_PLAYER; turn++) {
            System.out.println("=== Turn " + turn + " ===");
            playTurn(player1);
            playTurn(player2);
            System.out.println();
        }

        displayResults();
        scanner.close();
    }

    private void playTurn(Player_d player) {
        System.out.println(player.getName() + "'s turn:");
        int rollValue = die.roll();
        System.out.println("Die rolled: " + rollValue);

        int finalValue = handleRerolls(rollValue);
        player.addScore(finalValue);
        System.out.println(player.getName() + " added " + finalValue + " points. Total score: " + player.getScore() + "\n");
    }

    private int handleRerolls(int currentValue) {
        int rerollsRemaining = 2;
        int value = currentValue;

        while (rerollsRemaining > 0) {
            System.out.print("Current die value: " + value + ". Reroll? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                value = die.roll();
                System.out.println("Die rolled: " + value);
                rerollsRemaining--;
            } else {
                break;
            }
        }

        return value;
    }

    private void displayResults() {
        System.out.println("=== Game Over ===");
        System.out.println(player1.getName() + " final score: " + player1.getScore());
        System.out.println(player2.getName() + " final score: " + player2.getScore());

        if (player1.getScore() > player2.getScore()) {
            System.out.println(player1.getName() + " wins!");
        } else if (player2.getScore() > player1.getScore()) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public Player_d getPlayer1() {
        return player1;
    }

    public Player_d getPlayer2() {
        return player2;
    }
}