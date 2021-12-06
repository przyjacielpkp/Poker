package controller;

import model.game.Game;
import model.game.GameImpl;

import java.util.List;
import java.util.Random;

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
        playerNumber = MyScanner.askNumberOfPlayers();

        System.out.println("Insert entry fee:");
        int entryFee = MyScanner.askEntryFee();
        newGame = new GameImpl(playerNumber, entryFee);
        dealerId = new Random().nextInt(playerNumber);
    }


    @Override
    public void startNewRound(){
        //1st turn -> blind bets and giving 2 cards to every player
        int bank = 0;
        newGame.StartRound(dealerId);

        //1st bidding turn
        newGame.playTurn();
        newGame.flopCards(3);

        newGame.playTurn();

        newGame.flopCards(1);
        newGame.playTurn();
        newGame.flopCards(1);
        newGame.playTurn();

        bank += newGame.grabMoney();

        List<Integer> winnersIdx = newGame.chooseWinners();
        newGame.giveMoney(winnersIdx, bank / winnersIdx.size());
        MyScanner.listWinners(newGame.getPlayers(), winnersIdx);
    }


    /*
    private void startBidding(){
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
    }
*/

    @Override
    public boolean isFinished(){
        return false;
    }


}
