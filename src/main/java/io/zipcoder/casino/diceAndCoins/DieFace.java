package io.zipcoder.casino.diceAndCoins;

public enum DieFace {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private final int faceValue;

    DieFace(int faceValue) {
        this.faceValue = faceValue;
    }

    public boolean equalsFaceValue(int faceValue) {
        return this.faceValue == faceValue? true: false;
    }

    public int getFaceValue() {
        return this.faceValue;
    }
}
