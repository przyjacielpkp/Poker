package model.game;

public interface Game {
    void StartRound(int dealerId);
    String getGameState(boolean isPublic);
    String getPlayerState(int playerId);
}
