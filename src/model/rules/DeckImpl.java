package model.rules;

import java.util.ArrayList;
import java.util.Collections;

public class DeckImpl implements Deck {

    private final ArrayList<Card> cardList;
    private int listPointer;

    public DeckImpl() {
        cardList = new ArrayList<>();
        listPointer = 0;
        for (int i = 2; i <= 14; i++) {
            for (Card.Suit suit : Card.Suit.values())
                cardList.add(new CardImpl(suit, i));
        }
        resetDeck();
    }

    @Override
    public Card drawCard() {
        if (listPointer == cardList.size())
            return null;
        return cardList.get(listPointer++);
    }

    @Override
    public void resetDeck() {
        Collections.shuffle(cardList);
    }
}
