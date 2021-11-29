import java.util.List;

public class Game {

    final int STARTMONEY = 1000;
    final int SMALLBETSTART = 5;
    final int BIGBETSTART = 10;
    final int BETINCREMENT = 5;

    List<Player> players;
    int numberOfPlayers;
    int idBigBet;
    int idSmallBet;
    int allMoneyOnTable;
    int roundBet;
    int smallBet;
    int bigBet;
    Deck deck;

    public Game(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        this.deck = new Deck();
        for(int i=0; i< numberOfPlayers; i++){
            Player cur = new Player();
            cur.addMoney(STARTMONEY);
            players.add(cur);
        }
        roundBet = 0;
        idSmallBet = 0;
        idBigBet = 1;
        allMoneyOnTable = 0;
        smallBet = SMALLBETSTART;
        bigBet = BIGBETSTART;
    }

    //tura bez kart
    //3 karty
    //tura
    //karta
    //tura
    //karta
    //tura
    //sprawdz wynik


    public int getMinBet(){
        int res = Integer.MAX_VALUE;
        for(int i=0; i<numberOfPlayers; i++){
            if(!players.get(i).isPassed()){
                res = Integer.min(res, players.get(i).getCurrentBet());
            }
        }
        return res;
    }
    public void StartRound(){
        roundBet = 0;
        playTurn(true);
        //add 3 card
        playTurn(false);
        //add 1 card
        playTurn(false);
    }

    public void playTurn(boolean firstTurn){
        int idStart=idSmallBet;
        int biggestPlayerId = 0;
        for(int i=0; i<numberOfPlayers; i++){
            int id = (idStart + i)%numberOfPlayers;
            if(firstTurn && i==0) {}//wez kase small
            else if(firstTurn && i==1) {} //wez big
            else{
               addMoneyOnTable(players.get(i).takeMoney(roundBet));
               //ruchy w turze
                //zakutalizuj biggestPlayerId
            }

        }
        int currentId = idStart;
        while(getMinBet()<getMaxBet() && currentId != biggestPlayerId){
            //wez kase od wszystkich
            //play turn
            currentId = (currentId +1)%numberOfPlayers;
        }

    }

    public int getMaxBet(){
        return roundBet;
    }
    public void addMoneyOnTable(int val){
        allMoneyOnTable +=val;
    }

}
