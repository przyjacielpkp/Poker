import java.util.ArrayList;
import java.util.Collections;

public class DeckImpl implements Deck{

    private ArrayList<Card> cardList;
    private int listPointer;

    DeckImpl(){
        cardList = new ArrayList<>();
        listPointer=0;
        for(int i =0;i<=13;i++){
            for(Card.Suit suit : Card.Suit.values())
                cardList.add(new CardImpl(suit,i));
        }
    }

    public Card drawCard() {
        if(listPointer == cardList.size())
            return null;
        return cardList.get(listPointer++);
    }

    public void resetDeck() {
        Collections.shuffle(cardList);
    }
}
