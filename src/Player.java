import java.util.List;

public interface Player {
    int getMoney();
    void addMoney(int value);
    int getCurrentBet();
    int takeMoney(int maxBet);
    boolean isPassed();
    boolean isDead();
    void beginRound(List<Card> list);
    int getHandValue(List<Card> list);
}
