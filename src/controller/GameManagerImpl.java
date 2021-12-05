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
    private Scanner scanner;
    @Override
    public void startGame(){
        scanner = new Scanner(System.in);

        System.out.println("Insert number of players:");
        playerNumber = scanner.nextInt();

        System.out.println("Insert entry fee:");
        int entryFee = scanner.nextInt();
        newGame = new GameImpl(playerNumber, entryFee);

        dealerId = new Random().nextInt(playerNumber);
        System.out.println("The starting dealer is a player number: " +dealerId);
    }

    @Override
    public void startNewRound(){

        //1st turn -> blind bets and
        newGame.StartRound(dealerId);

        for(int i=0;i<playerNumber;i++){

            System.out.println(newGame.getGameState(true));
            scanner.nextLine(); //scanner flushing

            System.out.print("Player number "+i+", get ready, press any key to continue . . . ");
            scanner.nextLine();
            System.out.println(newGame.getPlayerState(i));
            System.out.println("Choose moves: [Bet], [Fold], [Call], [Raise], [Check], [All in]");

            boolean moved = false;
            while(!moved){
                moved = true;
                switch (scanner.nextLine()){
                    case "Bet":
                        break;
                    case "Fold":
                        break;
                    case "Call":
                        break;
                    case "Raise":
                        break;
                    case "Check":
                        break;
                    default:
                        System.out.println("Incorrect Command!");
                        moved = false;
                        break;
                }
            }


        }



    }

    @Override
    public boolean isFinished(){
        return false;
    }


}
