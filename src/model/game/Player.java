package model.game;

import model.rules.Card;

import java.util.List;

public interface Player {
    int getMoney();

    void addMoney(int value);

    int takeMoney(int betValue);

    int getCurrentBet();

    boolean isPassed();

    void setPassed(boolean passed);

    boolean isDead();

    void setDead(boolean dead);

    void beginRound(List<Card> list);

    int getHandValue(List<Card> list);
}
