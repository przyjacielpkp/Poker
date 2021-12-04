package model.rules;

public interface Card {
    int getValue();

    Suit getSuit();

    enum Suit {
        DIAMOND,
        SPADE,
        CLUB,
        HEART
    }
}
