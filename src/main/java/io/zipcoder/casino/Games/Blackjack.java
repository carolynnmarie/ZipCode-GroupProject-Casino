package io.zipcoder.casino.Games;

import io.zipcoder.casino.Cards.*;
import io.zipcoder.casino.People.*;


import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack extends CardGames implements GamblingInterface {

    private Person player;
    private Dealer dealer;
    private Deck deck;


    public Blackjack(Person player) {
        this.player = player;
        this.dealer = new Dealer();
        this.deck = new Deck();
    }


    public Person getPlayer() {
        return player;
    }
    public void start() {
        deck.shuffleDeck();
        if (checkChipAmount(player) <= 0) {
            System.out.println("You don't have anymore chips to play");
            return;
        }
        System.out.println("\n+++ PLAY BLACKJACK +++");
        int betPlaced = placeBet();
        dealCards();
        engine();
        System.out.println(findWinner(betPlaced));
        keepPlaying();
    }

    public int placeBet(){
        String playerInput="";
        Scanner scanner = new Scanner(System.in);
        int betPlaced = 0;
        do {
            System.out.println("How many chips do you want to bet?");
            playerInput = scanner.nextLine();
            try {
                betPlaced = Integer.parseInt(playerInput);
                if (betPlaced <= checkChipAmount(player)) {
                    placeBet(player, betPlaced);
                    break;
                } else {
                    System.out.println("Insufficient funds. You only have " + checkChipAmount(player) + " chip(s)\n");
                }
            } catch (NumberFormatException ne) {
                System.out.println("Please try again.\n");
            }
        } while (true);
        return betPlaced;
    }

    // GamblingInterface
    public void placeBet(Person personPlacingBet, int betAmount) {
        personPlacingBet.getWallet().removeChips(betAmount);
    }

    public void dealCards() {
        player.getHand().receiveCards(deck.dealCards(2));
        dealer.getHand().receiveCards(deck.dealCards(2));
    }

    public void hit(Person person) {
        person.getHand().receiveCards(deck.drawCard());
    }

    public void engine(){
        int playerTotal;
        int dealerTotal;
        Scanner scanner = new Scanner(System.in);
        do {
            playerTotal = handTotal(player);
            dealerTotal = handTotal(dealer);
            String dealerCard = dealer.getHand().toArrayList().get(1).toString();
            if (deck.deckSize() <= 16) {
                deck = new Deck();
                deck.shuffleDeck();
            }
            StringBuilder builder = new StringBuilder("Your cards: \u270B \n" + handToString(player) + "\u270B total = " + playerTotal
                    + "\nDealer: \u270B \uD83C \uDCA0 " + dealerCard + "\u270B");
            if (playerTotal >= 21) {
                break;
            } else {
                builder.append("\nDo you want to \"hit\" or \"stay\"?: ");
                System.out.println(builder.toString());
            }
            String playerInput = scanner.nextLine().toLowerCase();
            if (playerInput.equals("hit")) {
                hit(player);
                ArrayList<Card> hand = player.getHand().toArrayList();
                System.out.println("Your new card is: " + hand.get(hand.size()-1).toString());
                playerTotal = handTotal(player);
                if(playerTotal>=21){
                    break;
                }
            } else if (playerInput.equals("stay")) {
                break;
            }
        } while (playerTotal < 21);
        if (playerTotal < 21 && dealerTotal <= 16) {
            hit(dealer);
        }
    }

    public int handTotal(Person person) {
        ArrayList<Card> handCards = person.getHand().toArrayList();
        int handValue = 0;
        for (Card card : handCards) {
            int cardValue = card.getRankInt();
            handValue += (cardValue == 11 || cardValue == 12 || cardValue == 13) ? 10: cardValue;
        }
        if(countAceDuplicates(handCards) == 1){
            if(handValue<=11){
                handValue+=10;
            }
        }
        return handValue;
    }

    public int countAceDuplicates(ArrayList<Card> cards) {
        int count = 0;
        for (Card card : cards) {
            if (card.getRankInt() == 1) {
                count++;
            }
        }
        return count;
    }

    public String handToString(Person person) {
        ArrayList<Card> handArrayList = person.getHand().toArrayList();
        StringBuilder sb = new StringBuilder();
        for (Card card : handArrayList) {
            sb.append(card.toString() + " ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }



    public int checkChipAmount(Person personToCheck) {
        return personToCheck.getWallet().checkChipAmount();
    }

    public void addChips(Person person, int amount){
        person.getWallet().addChips(amount);
    }

    public String findWinner(int betPlaced){
        StringBuilder builder = new StringBuilder();
        int dealerTotal = handTotal(dealer);
        int playerTotal = handTotal(player);
        if(playerTotal>21) {
            builder.append("Bust! You lost " + betPlaced + " chips");
        } else if(playerTotal==21) {
            builder.append("\nBLACKJACK!\n You won " + betPlaced + " chip(s)\n");
            addChips(player,(2 * betPlaced));
        } else if ((dealerTotal <= 21) && (dealerTotal > playerTotal)) {
            builder.append("Dealer wins!\nYou lost " + betPlaced + " chip(s)");
        } else if ((dealerTotal <= 21) && (dealerTotal == playerTotal)) {
            builder.append("It's a tie!\nYou keep your " + betPlaced + " chip(s)");
            addChips(player, betPlaced);
        } else {
            builder.append("You win!\nYou won " + betPlaced + " chip(s)");
            addChips(player,(2 * betPlaced));
        }
        builder.append("\nYour new chip total is:" + checkChipAmount(player) + "\nFINAL SCORE:\nYou: \u270B " + handToString(player) +
                "\u270B total = " + playerTotal + "\nDealer: \u270B " + handToString(dealer) + "\u270B total = " + dealerTotal);
        return builder.toString();
    }

    public void keepPlaying() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        do {
            System.out.println("Keep playing? (yes/no) ");
            input = scanner.nextLine();
            if (input.equals("yes")) {
                player.getHand().clearHand();
                dealer.getHand().clearHand();
                start();
            } else if (input.equals("no")) {
                end();
            } else {
                System.out.println("Invalid entry");
            }
        } while(!input.equals("yes")||!input.equals("no"));
    }

    // Game Class
    public void end() {
        System.out.print("Thank you for playing\n");
    }

    @Override
    public int checkHandSize(Hand hand) {
        return hand.toArrayList().size();
    }

    // GamblingInterface
    public void bootPlayerFromGame(Person personToBoot) {
    }


    public static void main (String[] args) {
        Person jack = new Person("Jack");
        jack.setWallet(20);
        Blackjack blackjack = new Blackjack(jack);
        blackjack.start();
    }
}
