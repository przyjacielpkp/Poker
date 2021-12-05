package model.game;

public interface Game {
    void StartRound(int dealerId);
    void printGameState();
    void printPlayerState(int playerId);
}
