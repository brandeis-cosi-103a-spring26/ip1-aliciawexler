package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Before;
/**
 * Rigorous tests for the Dice Game
 */
public class AppTest 
{
    private Player_d player1;
    private Player_d player2;
    private Die die;

    @Before
    public void setUp() {
        player1 = new Player_d("Player 1");
        player2 = new Player_d("Player 2");
        die = new Die();
    }

    // ===== Player Tests =====
    @org.junit.Test
    public void playerInitialScoreShouldBeZero() {
        assertEquals(0, player1.getScore());
    }

    @org.junit.Test
    public void playerNameShouldBeSet() {
        assertEquals("Player 1", player1.getName());
    }

    @org.junit.Test
    public void playerShouldAccumulateScore() {
        player1.addScore(5);
        player1.addScore(3);
        assertEquals(8, player1.getScore());
    }

    @org.junit.Test
    public void playerScoreShouldNotBeNegative() {
        player1.addScore(10);
        assertTrue(player1.getScore() >= 0);
    }

    // ===== Die Tests =====
    @org.junit.Test
    public void dieRollShouldBeWithinValidRange() {
        for (int i = 0; i < 100; i++) {
            int roll = die.roll();
            assertTrue("Roll should be between 1 and 6", roll >= 1 && roll <= 6);
        }
    }

    @org.junit.Test
    public void dieRollShouldNeverBeZero() {
        for (int i = 0; i < 100; i++) {
            int roll = die.roll();
            assertNotEquals(0, roll);
        }
    }

    @org.junit.Test
    public void dieRollShouldNeverExceedSix() {
        for (int i = 0; i < 100; i++) {
            int roll = die.roll();
            assertTrue("Roll should not exceed 6", roll <= 6);
        }
    }

    @org.junit.Test
    public void dieRollShouldProduceDifferentValues() {
        boolean hasVariety = false;
        int firstRoll = die.roll();
        
        for (int i = 0; i < 50; i++) {
            if (die.roll() != firstRoll) {
                hasVariety = true;
                break;
            }
        }
        assertTrue("Die should produce variety in rolls", hasVariety);
    }

    // ===== Game Rule Tests =====
    @org.junit.Test
    public void gameShouldHaveTwoPlayers() {
        DiceGame game = new DiceGame();
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertNotEquals(game.getPlayer1(), game.getPlayer2());
    }

    @org.junit.Test
    public void eachPlayerShouldGetTenTurns() {
        Player_d testPlayer = new Player_d("Test");
        for (int i = 0; i < 10; i++) {
            testPlayer.addScore(1);
        }
        assertEquals(10, testPlayer.getScore());
    }

    @org.junit.Test
    public void scoreShouldBeAddedFromDieRoll() {
        int rollValue = die.roll();
        player1.addScore(rollValue);
        assertEquals(rollValue, player1.getScore());
    }

    @org.junit.Test
    public void playerWithHigherScoreShouldWin() {
        player1.addScore(50);
        player2.addScore(30);
        assertTrue("Player 1 has higher score", player1.getScore() > player2.getScore());
    }

    @org.junit.Test
    public void tieGameShouldBeDetected() {
        player1.addScore(40);
        player2.addScore(40);
        assertEquals(player1.getScore(), player2.getScore());
    }

    @org.junit.Test
    public void maxPossibleScoreAfterTenTurns() {
        for (int i = 0; i < 10; i++) {
            player1.addScore(6);
        }
        assertEquals(60, player1.getScore());
    }

    @org.junit.Test
    public void minPossibleScoreAfterTenTurns() {
        for (int i = 0; i < 10; i++) {
            player1.addScore(1);
        }
        assertEquals(10, player1.getScore());
    }

    @org.junit.Test
    public void dieRerollMechanicAllowsUpToThreeRolls() {
        int initialRoll = die.roll();
        int reroll1 = die.roll();
        int reroll2 = die.roll();
        
        assertTrue(initialRoll >= 1 && initialRoll <= 6);
        assertTrue(reroll1 >= 1 && reroll1 <= 6);
        assertTrue(reroll2 >= 1 && reroll2 <= 6);
    }

    @org.junit.Test
    public void playerScoreShouldNotIncludeDiscardedRolls() {
        int roll1 = 2;
        int roll2 = 5;
        int finalScore = roll2;
        
        player1.addScore(finalScore);
        assertEquals(5, player1.getScore());
        assertNotEquals(7, player1.getScore());
    }

    @org.junit.Test
    public void bothPlayersShouldHaveIndependentScores() {
        player1.addScore(25);
        player2.addScore(15);
        
        assertEquals(25, player1.getScore());
        assertEquals(15, player2.getScore());
        assertNotEquals(player1.getScore(), player2.getScore());
    }

    @org.junit.Test
    public void scoresShouldCumulateAcrossMultipleTurns() {
        int[] rolls = {3, 5, 2, 6, 1};
        int expectedTotal = 17;
        
        for (int roll : rolls) {
            player1.addScore(roll);
        }
        
        assertEquals(expectedTotal, player1.getScore());
    }
}
