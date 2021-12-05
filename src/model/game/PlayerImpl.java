package model.game;

import model.rules.Card;
import model.rules.HandValueEvaluator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.min;

public class PlayerImpl implements Player {
    List<Card> hand;

    private int money, currentBet;
    private boolean passed, dead;


    public PlayerImpl(int startingMoney) {
        money = startingMoney;
        passed = false;
        dead = false;
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public void addMoney(int value) {
        money += value;
    }

    @Override
    public int takeMoney(int betValue) {
        int extraMoney = min(betValue - currentBet, money);
        money -= extraMoney;
        currentBet += extraMoney;
        return extraMoney;
    }

    @Override
    public int getCurrentBet() {
        return currentBet;
    }

    @Override
    public boolean isPassed() {
        return passed;
    }

    @Override
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void beginRound(List<Card> list) {
        currentBet = 0;
        hand = list;
        passed = false;
    }

    @Override
    public int getHandValue(List<Card> list) {
        return HandValueEvaluator.evaluate(Stream.concat(list.stream(), hand.stream()).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("model.game.Player{ ").append("money=").append(money).append(" cards=[");
        boolean first = true;
        for (Card card : hand) {
            if (first)
                first = false;
            else
                builder.append(",");
            builder.append(card.toString());
        }
        return builder.append("] }").toString();
    }
}
