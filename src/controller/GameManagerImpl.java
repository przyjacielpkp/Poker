package controller;

import model.game.Game;
import model.game.GameImpl;
import model.game.Player;
import model.game.Speaker;

import java.util.List;
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
        //scanner = new Scanner(System.in);

        System.out.println("Insert number of players:");
        playerNumber = Speaker.askNumberOfPlayers();

        System.out.println("Insert entry fee:");
        int entryFee = Speaker.askEntryFee();
        newGame = new GameImpl(playerNumber, entryFee);
        dealerId = new Random().nextInt(playerNumber);
    }

    @Override
    public void startNewRound(){
        //1st turn -> blind bets and giving 2 cards to every player
        int bank = 0;
        newGame.StartRound(dealerId);
        //1st bidding turn
        dropCards();
        newGame.playTurn(true);
        bank += newGame.grabMoney();
        show3Card();
        newGame.playTurn(false);
        bank += newGame.grabMoney();
        showCard();
        newGame.playTurn(false);
        bank += newGame.grabMoney();
        showCard();
        newGame.playTurn(false);

        List<Integer> winnersIdx = newGame.chooseWinners();
        newGame.giveMoney(winnersIdx, bank / winnersIdx.size());
        Speaker.listWinners(newGame.getPlayers(), winnersIdx, bank);
    }
    private void showCard() {
        newGame.showCard();
    }
    private void show3Card() {
        showCard();
        showCard();
        showCard();
    }
    private void dropCards() {
        for (int i = 0; i < playerNumber; ++i) {
            Speaker.showPlayerCards(i, newGame.getPlayerCards(i));
        }
    }
    /*private void startBidding(){
        int startingId = newGame.getActiveId();
        scanner.nextLine(); //scanner flushing
        for(int j=playerNumber;j>0;j--){

            int i = (startingId+j)%playerNumber;
            if(newGame.isPlayerPassed(i))
                continue;

            System.out.println(newGame.getGameState(true));



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
                        newGame.passPlayer(i);
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
    }*/


    @Override
    public boolean isFinished(){
        return false;
    }


}
