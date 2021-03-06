package io.zipcoder.casino.Games;

import io.zipcoder.casino.People.Person;

public interface GamblingInterface {
    int checkChipAmount(Person personToCheck);
    void placeBet(Person personPlacingBet, int betAmount);
    void bootPlayerFromGame(Person personToBoot);
}
