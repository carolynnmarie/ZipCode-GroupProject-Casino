package io.zipcoder.casino.Games;

import io.zipcoder.casino.People.Hand;

public interface CardGameInterface {

    int checkNumberOfCards(Hand hand);
    void dealCards();
}