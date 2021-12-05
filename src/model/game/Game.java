package model.game;

public interface Game {
    void StartRound(int dealerId);
    int getActiveId();
    String getGameState(boolean isPublic);
    String getPlayerState(int playerId);

    void passPlayer(int playerId);
    boolean isPlayerPassed(int playerId);
}
