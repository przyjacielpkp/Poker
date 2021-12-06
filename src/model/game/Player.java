package model.game;

import model.rules.Card;

import java.util.List;

public interface Player {
    int getMoney();

    void addMoney(int value);

    int takeMoney(int betValue);

    int getCurrentBet();
    int grabBet();

    boolean isPassed();

    void setPassed(boolean passed);

    void setAllin(boolean allin);

    boolean isDead();

    boolean isAllin();

    void setDead(boolean dead);

    void beginRound(List<Card> list);

    int getHandValue(List<Card> list);

    List<Card> getHandCards();
}
