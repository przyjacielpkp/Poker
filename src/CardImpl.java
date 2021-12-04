public class CardImpl implements Card{

    CardImpl(Suit suit, int value){
        cardValue= value;
        cardSuit = suit;
    }

    private int cardValue;
    private Suit cardSuit;

    public int getValue() {
        return cardValue;
    }

    public Suit getSuit() {
        return cardSuit;
    }

}
