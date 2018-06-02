package io.zipcoder.casino;

import io.zipcoder.casino.cards.Card;
import io.zipcoder.casino.cards.Rank;
import io.zipcoder.casino.cards.Suit;
import io.zipcoder.casino.games.Blackjack;
import io.zipcoder.casino.people.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlackjackTest {

    private Blackjack blackjack;
    Person player = new Person("test");

    @Before public void setUp() {
        player.getWallet().addChips(500);

    }

    @Test
    public void BlackJackDefaultConstructorTest() {
        /**
         * construct a blackjack game
         * - extends class Game
         * - implements Card interface
         * - implements Gambling interface
         */
        // Given
        String expectedPlayerName = "test";
        // When
        Blackjack blackjack = new Blackjack(player);
        String actualPlayerName = blackjack.getPlayer().getName();
        // Then
        Assert.assertEquals(expectedPlayerName, actualPlayerName);
    }

    @Test
    public void sumOfRanksInHandTest() {
        Blackjack blackjack = new Blackjack(player);
        // Add cards to player1 Hand
        Card notShuffled0 = new Card(Rank.DEUCE, Suit.CLUBS);
        Card notShuffled1 = new Card(Rank.ACE, Suit.DIAMONDS);
        Card notShuffled2 = new Card(Rank.ACE, Suit.HEARTS);
        Card notShuffled3 = new Card(Rank.ACE, Suit.SPADES);
        blackjack.getPlayer().receiveCards(notShuffled0, notShuffled1, notShuffled2, notShuffled3);

        // Add cards to Dealer Hand
        Card dealerCard0 = new Card(Rank.ACE, Suit.CLUBS);
        Card dealerCard1 = new Card(Rank.TEN, Suit.DIAMONDS);
        Card dealerCard2 = new Card(Rank.JACK, Suit.HEARTS);
        Card dealerCard3 = new Card(Rank.FIVE, Suit.SPADES);
        blackjack.getDealer().receiveCards(dealerCard0, dealerCard1, dealerCard2, dealerCard3);

        // Given
        int expectedPersonHandSum = 5; // 5 = 2 + 1 + 1 + 1
        int expectedDealerHandSum = 26; // 26 = 1 + 10 + 10 + 5
        // When
        int actualPersonHandSum = blackjack.rankSum(blackjack.getPlayer());
        int actualDealerHandSum = blackjack.rankSum(blackjack.getDealer());
        // Then
        Assert.assertEquals(expectedPersonHandSum, actualPersonHandSum);
        Assert.assertEquals(expectedDealerHandSum, actualDealerHandSum);
    }

    @Test
    public void countRankRepetitionsInHandTest() {
        Blackjack blackjack = new Blackjack(player);

        // Add cards to player1 Hand
        Card notShuffled0 = new Card(Rank.DEUCE, Suit.CLUBS);
        Card notShuffled1 = new Card(Rank.ACE, Suit.DIAMONDS);
        Card notShuffled2 = new Card(Rank.ACE, Suit.HEARTS);
        Card notShuffled3 = new Card(Rank.ACE, Suit.SPADES);
        blackjack.getPlayer().receiveCards(notShuffled0, notShuffled1, notShuffled2, notShuffled3);

        // Add cards to Dealer Hand
        Card dealerCard0 = new Card(Rank.SIX, Suit.CLUBS);
        Card dealerCard1 = new Card(Rank.SIX, Suit.DIAMONDS);
        Card dealerCard2 = new Card(Rank.EIGHT, Suit.HEARTS);
        //Card dealerCard3 = new Card(Rank.FIVE, Suit.SPADES);
        blackjack.getDealer().receiveCards(dealerCard0, dealerCard1, dealerCard2);

        // Given
        int expectedPersonRankReps = 3; // ACE
        int expectedDealerRankReps = 2; // SIX
        // When
        int actualPersonRankReps = blackjack.rankRepeats(blackjack.getPlayer(), notShuffled1);
        int actualDealerRankReps = blackjack.rankRepeats(blackjack.getDealer(), dealerCard0);
        // Then
        Assert.assertEquals(expectedPersonRankReps, actualPersonRankReps);
        Assert.assertEquals(expectedDealerRankReps, actualDealerRankReps);
    }


    @Test
    public void personDecisionTest() {
        Blackjack blackjack = new Blackjack(player);
        //String playerDecisionString = blackjack.personDecision(blackjack.getPlayer());
    }

    @Test
    public void handToStringTest() {
        // Given
        Blackjack blackjack = new Blackjack(player);
        blackjack.getPlayer().receiveCards(new Card(Rank.DEUCE, Suit.CLUBS), new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.FOUR, Suit.HEARTS), new Card(Rank.FIVE, Suit.SPADES));
        String expectedHandToString = "5♠ 4♥ 3♦ 2♣";
        // When
        String actualHandToString = blackjack.handToString(blackjack.getPlayer().getHandArrayList());
        System.out.println(actualHandToString);
        // Then
        Assert.assertEquals(expectedHandToString, actualHandToString);
    }

    @Test
    public void hitTest() {
        // if playerSum < 21, player can hit
        // if playerDecision = "hit", then dealer draws card and player hand receives card
        Blackjack blackjack = new Blackjack(player);

        // Add cards to player1 Hand
        Card playerCard0 = new Card(Rank.DEUCE, Suit.CLUBS);
        Card playerCard1 = new Card(Rank.THREE, Suit.DIAMONDS);
        blackjack.getPlayer().receiveCards(playerCard0, playerCard1);

        // Add cards to Dealer Hand
        Card dealerCard0 = new Card(Rank.SIX, Suit.CLUBS);
        Card dealerCard1 = new Card(Rank.SEVEN, Suit.DIAMONDS);
        blackjack.getDealer().receiveCards(dealerCard0, dealerCard1);

        // Given
        blackjack.hit(blackjack.getPlayer());
        blackjack.hit(blackjack.getDealer());
        int expectedPlayerHandSize = 3;
        int expectedDealerHandSize = 3;
        // When
        int actualPlayerHandSize = blackjack.getPlayer().getHand().getHandArrayList().size();
        int actualDealerHandSize = blackjack.getDealer().getHand().getHandArrayList().size();
        // Then
        Assert.assertEquals(expectedPlayerHandSize, actualPlayerHandSize);
        Assert.assertEquals(expectedDealerHandSize, actualDealerHandSize);

    }

    // Will keep this for future reference
//    @Test
//    public void askForPlayerNameTest() {
//        // Given
//        String expectedName = "Luis";
//        // When
//        Blackjack blackjack = new Blackjack("Luis", 50);
//        String askedName = blackjack.askForPlayerName();
//        String actualName = blackjack.getPlayer().getName();
//        // Then
//        Assert.assertEquals(expectedName, actualName);
//    }

    // GamblingInterface
    @Test
    public void placeBetTest() {
        // Given
        int betPlaced = 1;
        int chipsToStart = 500;
        int expectedChipsRemaining = chipsToStart - betPlaced; // 499
        // When
        Blackjack blackjack = new Blackjack(player);
        blackjack.placeBet(blackjack.getPlayer(), betPlaced); // will remove 1 chip
        int actualChipsRemaining = blackjack.getPlayer().getWallet().checkChips();
        // Then
        Assert.assertEquals(expectedChipsRemaining,actualChipsRemaining);
    }

    @Test
    public void checkNumberOfCards() {

    }
}
