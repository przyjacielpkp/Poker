package model.rules;

public class CardImpl implements Card {

    private final int cardValue;
    private final Suit cardSuit;
    CardImpl(Suit suit, int value) {
        cardValue = value;
        cardSuit = suit;
    }

    public int getValue() {
        return cardValue;
    }

    public Suit getSuit() {
        return cardSuit;
    }

    @Override
    public String toString() {
        return "["+cardSuit +"," + cardValue + "]";
    }

}
