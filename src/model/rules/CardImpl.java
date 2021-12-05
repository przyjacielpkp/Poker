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
        String valueName = switch (cardValue) {
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            case 14 -> "A";
            default -> String.valueOf(cardValue);
        };

        return "["+cardSuit +"," + valueName + "]";
    }

}
