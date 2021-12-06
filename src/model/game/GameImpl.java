package model.game;

import controller.MyScanner;
import model.rules.Card;
import model.rules.Deck;
import model.rules.DeckImpl;

import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game {

    final int smallBet = 5;
    List<Player> players;
    List<Card> tableCards;
    int numberOfPlayers, activeId, smallBetId;
    Deck deck;

    public GameImpl(int numberOfPlayers,int entryFee) {
        this.numberOfPlayers = numberOfPlayers;
        this.deck = new DeckImpl();

        players = new ArrayList<>();
        tableCards = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player cur = new PlayerImpl(entryFee);
            players.add(cur);
        }
    }

    @Override
    public void StartRound(int dealerId) {
        for (Player player: players) {
            if(!player.isPassed()){
                List<Card> startingHand =new ArrayList<>();
                startingHand.add(deck.drawCard());
                startingHand.add(deck.drawCard());
                player.beginRound(startingHand);
            }
        }

        int betId = getAliveIdToRight(dealerId);
        smallBetId = betId;
        players.get(betId).takeMoney(smallBet);

        betId = getAliveIdToRight(betId);
        players.get(betId).takeMoney(smallBet*2);

        activeId = getAliveIdToRight(betId);
    }

    @Override
    public void flopCards(int amount) {
        for(int i=0;i<amount;i++){
            Card c = deck.drawCard();
            tableCards.add(c);
        }
        MyScanner.showCards(tableCards);
    }

    @Override
    public List<Card> getPlayerCards(int playerId) {
        return players.get(playerId).getHandCards();
    }

    @Override
    public int grabMoney() {
        int bank = 0;
        for (Player player : players)
            bank += player.grabBet();
        return bank;
    }

    @Override
    public int getActiveId(){
        return activeId;
    }

    @Override
    public String getGameState(boolean isPublic){
        StringBuilder builder = new StringBuilder();
        if(isPublic) {
            builder.append("Cards on the table:\n");
            for (Card card : tableCards)
                builder.append(card).append("\n");
            builder.append("Public state of players:\n");

            for (int i = 0; i < numberOfPlayers; i++) {
                builder.append("[Player ").append(i).append(", bet: ").append(players.get(i).getCurrentBet())
                        .append(", Pass= ").append(players.get(i).isPassed()).append("]\t");
            }
        }
        return  builder.toString();
    }

    @Override
    public String getPlayerState(int playerId) {
        return players.get(playerId).toString();
    }

    @Override
    public void passPlayer(int playerId) {
        players.get(playerId).setPassed(true);
    }

    @Override
    public boolean isPlayerPassed(int playerId) {
        return players.get(playerId).isPassed();
    }

    @Override
    public List<Integer> chooseWinners() {
        List<Integer> ans = new ArrayList<>();

        int mx = -1;
        for (int i = 0; i < players.size(); ++i) {
            if (!players.get(i).isPassed()) {
                int val = players.get(i).getHandValue(tableCards);
                if (val > mx) {
                    ans.clear();
                    val = mx;
                }
                if (val == mx) {
                    ans.add(i);
                }
            }
        }
        return ans;
    }

    @Override
    public void giveMoney(List<Integer> ppl, int bank) {
        for (Integer idx : ppl)
            players.get(idx).addMoney(bank);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    private int getAliveIdToRight(int playerId) {
        playerId = (playerId+1)%numberOfPlayers;
        while(players.get(playerId).isPassed())
            playerId = (playerId+1)%numberOfPlayers;
        return playerId;
    }


    public void processPlayer(int playerId, int actualBet) {
        Player p = players.get(playerId);
        if (p.isAllin())
            return;

        MyScanner.PlayerAction act = MyScanner.askRegular(playerId, p, actualBet);
        if (MyScanner.PlayerAction.Check == act) {

        } else
        if (MyScanner.PlayerAction.Fold == act) {
            passPlayer(playerId);
        } else
        if (MyScanner.PlayerAction.Raise == act) {
            Integer bet = MyScanner.askRaise(p, actualBet);
            p.takeMoney(bet + actualBet);
        } else
        if (MyScanner.PlayerAction.Call == act) {
            p.takeMoney(actualBet);
        } else
        if (MyScanner.PlayerAction.Allin == act) {
            p.takeMoney(p.getMoney());
            p.setAllin(true);
        }
    }

    private int getAlivePlayersNumber() {
        int alivePlayers = 0;
        for(Player player : players) {
            if (!player.isPassed())
                alivePlayers++;
        }
        return alivePlayers;
    }

    public void playTurn() {
        int notPassedPlayers = getAlivePlayersNumber();
        if (notPassedPlayers==1)
            return;

        while (getMinBet() != getMaxBet() || notPassedPlayers > 0) {
            notPassedPlayers--;
            processPlayer(activeId, getMaxBet());
            activeId = getAliveIdToRight(activeId);
            if (getAlivePlayersNumber() == 1)
                return;
        }

        if (players.get(smallBetId).isPassed())
            activeId = getAliveIdToRight(smallBetId);
        else
            activeId = smallBetId;
    }


    private int getMinBet() {
        int ans = Integer.MAX_VALUE;
        for (Player player : players)
            if (!player.isPassed())
                ans = Math.min(ans, player.getCurrentBet());
        return ans;
    }

    private int getMaxBet() {
        int ans = Integer.MIN_VALUE;
        for (Player player : players)
            if (!player.isPassed())
                ans = Math.max(ans, player.getCurrentBet());
        return ans;
    }

    /* First round: start next to big blind
     * Second and later: who has small blind
     *
     */
}
