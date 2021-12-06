package model.game;

import model.rules.Card;

import java.util.List;

public interface Game {
    void StartRound(int dealerId);
    int getActiveId();
    String getGameState(boolean isPublic);
    String getPlayerState(int playerId);
    List<Card> getPlayerCards(int playerId);
    Card showCard();
    int grabMoney();
    void giveMoney(List<Integer> ppl, int bank);
    List<Integer> chooseWinners();
    List<Player> getPlayers();
    void playTurn(boolean firstTurn);
    void passPlayer(int playerId);
    boolean isPlayerPassed(int playerId);
}
