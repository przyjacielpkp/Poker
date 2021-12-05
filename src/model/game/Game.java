package model.game;

public interface Game {
    void StartRound(int dealerId);
    int getActiveId();
    String getGameState(boolean isPublic);
    String getPlayerState(int playerId);
}
