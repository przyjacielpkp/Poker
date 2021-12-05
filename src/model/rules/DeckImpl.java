package model.rules;

import java.util.ArrayList;
import java.util.Collections;

public class DeckImpl implements Deck {

    private final ArrayList<Card> cardList;
    private int listPointer;

    public DeckImpl() {
        cardList = new ArrayList<>();
        listPointer = 0;
        for (int i = 0; i <= 13; i++) {
            for (Card.Suit suit : Card.Suit.values())
                cardList.add(new CardImpl(suit, i));
        }
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
