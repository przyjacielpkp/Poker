import java.util.List;

public interface Player {
    int getMoney();
    void addMoney(int value);
    int takeMoney(int betValue);
    int getCurrentBet();

    void setPassed(boolean passed);
    boolean isPassed();
    void setDead(boolean dead);
    boolean isDead();

    void beginRound(List<Card> list);
    int getHandValue(List<Card> list);
}
