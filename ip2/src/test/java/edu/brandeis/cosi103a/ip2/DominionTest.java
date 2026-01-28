package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Test;

public class DominionTest {

    // -------------------------
    // Card tests
    // -------------------------
    @Test
    public void testCardCryptoGetters() {
        Card crypto = new CryptocurrencyCard(5, 10);
        assertEquals(5, crypto.getCost());
        assertEquals(10, crypto.getValue());
    }

    @Test
    public void testCardAutomationGetters() {
        Card automation = new AutomationCard(3, 6);
        assertEquals(3, automation.getCost());
        assertEquals(6, automation.getValue());
    }

    @Test
    public void testCardTypes() {
        Card crypto = new CryptocurrencyCard(0, 1);
        Card automation = new AutomationCard(2, 1);

        assertTrue(crypto instanceof CryptocurrencyCard);
        assertFalse(crypto instanceof AutomationCard);

        assertTrue(automation instanceof AutomationCard);
        assertFalse(automation instanceof CryptocurrencyCard);
    }

    // -------------------------
    // Supply tests
    // -------------------------
    @Test
    public void testSupplyStartsWithFramework() {
        Supply supply = new Supply();
        assertTrue(supply.hasCard("Framework"));
    }

    @Test
    public void testSupplyGetCardCostKnownAndUnknown() {
        Supply supply = new Supply();

        assertEquals(0, supply.getCardCost("Bitcoin"));
        assertEquals(3, supply.getCardCost("Ethereum"));
        assertEquals(6, supply.getCardCost("Dogecoin"));
        assertEquals(2, supply.getCardCost("Method"));
        assertEquals(5, supply.getCardCost("Module"));
        assertEquals(8, supply.getCardCost("Framework"));

        assertEquals(-1, supply.getCardCost("NotARealCard"));
    }

    @Test
    public void testSupplyPurchaseCardKnownAndUnknown() {
        Supply supply = new Supply();

        assertNotNull(supply.purchaseCard("Bitcoin"));
        assertNotNull(supply.purchaseCard("Method"));

        assertNull(supply.purchaseCard("NotARealCard"));
    }

    @Test
    public void testSupplyPurchaseFrameworkEmptiesPile() {
        Supply supply = new Supply();

        // Your Supply initializes 8 Frameworks
        for (int i = 0; i < 8; i++) {
            assertNotNull("Expected Framework #" + (i + 1), supply.purchaseCard("Framework"));
        }

        assertFalse(supply.hasCard("Framework"));
        assertNull(supply.purchaseCard("Framework"));
        assertEquals(-1, supply.getCardCost("Framework"));
    }

    @Test
    public void testSupplyAvailableCardsIncludesFrameworkInitially() {
        Supply supply = new Supply();
        assertTrue(supply.getAvailableCards().contains("Framework"));
    }

    @Test
    public void testSupplyAvailableCardsRemovesFrameworkWhenEmpty() {
        Supply supply = new Supply();

        for (int i = 0; i < 8; i++) {
            supply.purchaseCard("Framework");
        }
        assertFalse(supply.hasCard("Framework"));
        assertFalse(supply.getAvailableCards().contains("Framework"));
    }

    // This test will FAIL with your current Supply implementation if you enqueue
    // the exact same Card instance many times.
    @Test
    public void testSupplyPurchasedCardsAreDistinctObjects() {
        Supply supply = new Supply();
        Card a = supply.purchaseCard("Bitcoin");
        Card b = supply.purchaseCard("Bitcoin");

        assertNotNull(a);
        assertNotNull(b);

        // If this fails, fix Supply to create a NEW card each time.
        assertNotSame(a, b);
    }

    // -------------------------
    // Player tests (only tests that don't require getters)
    // -------------------------
    @Test
    public void testPlayerGetName() {
        Supply supply = new Supply();
        Player p = new Player("Test Player", supply);
        assertEquals("Test Player", p.getName());
    }

    @Test
    public void testPlayerPlayCryptocurrencyCardsCountsOnlyCrypto() {
        Supply supply = new Supply();
        Player p = new Player("P", supply);

        // Build a hand by dealing from draw pile:
        // Put: crypto(1), crypto(2), automation(doesn't count), crypto(3), automation
        p.addCardToDrawPile(new CryptocurrencyCard(0, 1));
        p.addCardToDrawPile(new CryptocurrencyCard(3, 2));
        p.addCardToDrawPile(new AutomationCard(2, 1));
        p.addCardToDrawPile(new CryptocurrencyCard(6, 3));
        p.addCardToDrawPile(new AutomationCard(5, 3));

        p.dealHand(5);

        // Should sum the 1+2+3 = 6
        assertEquals(6, p.playCryptocurrencyCards());
    }

    @Test
    public void testPlayerChooseCardToBuyPrioritizesFramework() {
        Supply supply = new Supply();
        Player p = new Player("P", supply);

        // With 8 coins, Framework is affordable and highest priority
        assertEquals("Framework", p.chooseCardToBuy(8, supply));
    }

    @Test(timeout = 5000)
    public void testGameCompletes() {
        Dominion game = new Dominion();
        game.startGame();
    }
}