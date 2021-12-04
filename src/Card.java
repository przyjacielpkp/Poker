public interface Card {
    enum Suit{
        DIAMOND,
        SPADE,
        CLUB,
        HEART
    }
    int getValue();
    Suit getSuit();
}
