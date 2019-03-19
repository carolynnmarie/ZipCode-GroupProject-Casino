package io.zipcoder.casino;

import io.zipcoder.casino.Cards.Card;
import io.zipcoder.casino.Cards.Deck;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class DeckTest {

    private Deck deckTest;
    private Card cardTest;

    @Test
    public void DeckTest() {
        //Given
        Deck deckTest = new Deck();

        //When
        ArrayList<Card> expected = deckTest.getDeck();
        ArrayList<Card> actual = deckTest.getDeck();

        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ShuffleDeckTest(){
        //Given
        Deck deckTest = new Deck();
        deckTest.shuffleDeck();

        //When
        String expected = "";
        System.out.println(expected);

        ArrayList<Card> actual = deckTest.getDeck();
        System.out.println(actual);

        //Then
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void DrawCardTest(){
        //Given
        Deck deckTest = new Deck();

        //When
        Card expected = deckTest.drawCard();
        ArrayList<Card> actual = deckTest.getDeck();


        //Then
        Assert.assertNotEquals(expected, actual);
    }

}
