package io.zipcoder.casino.People;


import io.zipcoder.casino.Money.Wallet;

import java.util.Scanner;

public class Person {
    private String name = "";
    private Hand hand = null;
    private Wallet wallet = null;


    protected Person(){ }

    public Person(String name) {
        this.name = name;
        this.hand = new Hand();
        this.wallet = new Wallet();
    }


    public String getName() {
        return this.name;
    }

    public Hand getHand() {
        return this.hand;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setWallet(int chips){
        this.wallet = new Wallet();
        wallet.addChips(chips);
    }

    //has Scanner and Wilhelm told me not to test methods with Scanner
    public static Person createNewPlayerFromUserInput() {
        String playerName;
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to our casino! Please enter your name");
        playerName = in.nextLine();
        return new Person(playerName);
    }



}
