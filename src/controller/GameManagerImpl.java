package controller;

import model.game.Game;
import model.game.GameImpl;

import java.util.Random;
import java.util.Scanner;

public class GameManagerImpl implements GameManager{

    //tura bez kart
    //3 karty
    //tura
    //karta
    //tura
    //karta
    //tura
    //sprawdz wynik
    private Game newGame;
    private int playerNumber;
    private int dealerId;

    @Override
    public void startGame(){
        System.out.println("Insert number of players:");
        Scanner scanner = new Scanner(System.in);
        playerNumber = scanner.nextInt();

        System.out.println("Insert entry fee:");
        int entryFee = scanner.nextInt();
        newGame = new GameImpl(playerNumber, entryFee);

        dealerId = new Random().nextInt(playerNumber)+1;
        System.out.println("The starting dealer is a player number: " +dealerId);
    }

    @Override
    public void startNewRound(){
        newGame.StartRound(dealerId);
        newGame.printGameState();

    }

    @Override
    public boolean isFinished(){
        return false;
    }


}
