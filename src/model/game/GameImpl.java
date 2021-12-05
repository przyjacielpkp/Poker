package model.game;

import model.rules.Card;
import model.rules.Deck;
import model.rules.DeckImpl;

import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game {

    final int smallBet = 5;

    List<Player> players;
    List<Card> tableCards;
    int numberOfPlayers,bigBetId;
    Deck deck;

    public GameImpl(int numberOfPlayers,int entryFee) {
        this.numberOfPlayers = numberOfPlayers;
        this.deck = new DeckImpl();

        players = new ArrayList<>();
        tableCards = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player cur = new PlayerImpl(entryFee);
            players.add(cur);
        }

    }

    @Override
    public void StartRound(int dealerId) {

        for (Player player: players) {
            if(!player.isDead()){
                List<Card> startingHand =new ArrayList<>();
                startingHand.add(deck.drawCard());
                startingHand.add(deck.drawCard());
                player.beginRound(startingHand);
            }
        }

        int smallBetId = getAliveIdToLeft(dealerId);
        players.get(smallBetId).takeMoney(smallBet);

        bigBetId = getAliveIdToLeft(smallBetId);
        players.get(bigBetId).takeMoney(smallBet*2);

    }

    @Override
    public void printGameState(){
        System.out.println("Cards on the table:");
        for(Card card : tableCards)
            System.out.println(card);

        System.out.println("Public state of players");
        for(int i=0;i<numberOfPlayers;i++){
            System.out.println(i+": "+players.get(i).getCurrentBet());
        }
    }

    @Override
    public void printPlayerState(int playerId) {

    }


    private int getAliveIdToLeft(int playerId) {
        playerId = (playerId-1+numberOfPlayers)%numberOfPlayers;
        while(players.get(playerId).isDead())
            playerId-= (playerId-1+numberOfPlayers)%numberOfPlayers;
        return playerId;
    }



/*
    public void playTurn(boolean firstTurn) {
        int idStart = idSmallBet;
        int biggestPlayerId = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            int id = (idStart + i) % numberOfPlayers;
            if (firstTurn && i == 0) {
            }//wez kase small
            else if (firstTurn && i == 1) {
            } //wez big
            else {
                addMoneyOnTable(players.get(i).takeMoney(roundBet));
                //ruchy w turze
                //zakutalizuj biggestPlayerId
            }

        }
        int currentId = idStart;
        while (getMinBet() < getMaxBet() && currentId != biggestPlayerId) {
            //wez kase od wszystkich
            //play turn
            currentId = (currentId + 1) % numberOfPlayers;
        }

    }


    private int getMinBet() {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (!players.get(i).isPassed()) {
                res = Integer.min(res, players.get(i).getCurrentBet());
            }
        }
        return res;
    }
    private int getMaxBet() {
        return roundBet;
    }

    private void addMoneyOnTable(int val) {
        allMoneyOnTable += val;
    }*/

}
