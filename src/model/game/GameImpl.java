package model.game;

import model.rules.Card;
import model.rules.Deck;
import model.rules.DeckImpl;
import model.rules.HandValueEvaluator;

import java.util.ArrayList;
import java.util.List;


public class GameImpl implements Game {

    final int smallBet = 5;
    List<Player> players;
    List<Card> tableCards;
    int numberOfPlayers, activeId, smallBetId;
    Deck deck;
    List<Card> board = new ArrayList<>();
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
            if(!player.isDead()){
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
    public Card showCard() {
        Card c = getCard();
        board.add(c);
        Speaker.showCards(board);
        return c;
    }
    @Override
    public List<Card> getPlayerCards(int playerId) {
        return players.get(playerId).getHandCards();
    }
    private Card getCard() {
        return deck.drawCard();
    }
    @Override
    public int grabMoney() {
        int bank = 0;
        for (int i = 0; i < players.size(); ++i) {
            bank += players.get(i).grabBet();
        }
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
        for (int i = 0; i < players.size(); ++i)
            if (!players.get(i).isPassed()) {
                int val = HandValueEvaluator.evaluate(getPlayerCards(i));
                if (val > mx) {
                    ans = new ArrayList<>();
                    val = mx;
                }
                if (val == mx) {
                    ans.add(i);
                }
            }

        return ans;
    }
    @Override
    public void giveMoney(List<Integer> ppl, int bank) {
        for (Integer idx : ppl)
            players.get(idx).addMoney(bank);;
    }
    @Override
    public List<Player> getPlayers() {
        return players;
    }
    private int getAliveIdToLeft(int playerId) {
        playerId = (playerId-1+numberOfPlayers)%numberOfPlayers;
        while(players.get(playerId).isDead())
            playerId = (playerId-1+numberOfPlayers)%numberOfPlayers;
        return playerId;
    }
    private int getAliveIdToRight(int playerId) {
        playerId = (playerId+1)%numberOfPlayers;
        while(players.get(playerId).isPassed())
            playerId = (playerId+1)%numberOfPlayers;
        return playerId;
    }
    private int getAliveNotAllinIdToLeft(int playerId) {
        playerId = getAliveIdToRight(playerId);
        while (!players.get(playerId).isAllin()) {
            playerId = getAliveIdToRight(playerId);
        }
        return playerId;
    }

    private boolean areAllAliin() {
        if (!players.get(activeId).isAllin()) return false;
        int playerId = getAliveIdToRight(activeId);
        while(activeId != playerId) {
            if (!players.get(playerId).isAllin()) return false;
            playerId = getAliveIdToRight(playerId);
        }
        return true;
    }

    public void processPlayer(int playerId, int actualBet) {
        Player p = players.get(playerId);
        if (p.isAllin()) return;
        Speaker.PlayerAction act = Speaker.askRegular(playerId, p, actualBet);
        if (Speaker.PlayerAction.Check == act) {
        } else
        if (Speaker.PlayerAction.Fold == act) {
            passPlayer(playerId);
        } else
        if (Speaker.PlayerAction.Raise == act) {
            Integer bet = Speaker.askRaise(p, actualBet);
            p.takeMoney(bet + actualBet);
        } else
        if (Speaker.PlayerAction.Call == act) {
            p.takeMoney(actualBet);
        } else
        if (Speaker.PlayerAction.Allin == act) {
            p.takeMoney(p.getMoney());
            p.setAllin(true);
        }
    }

    private int getAlivePlayersNumber() {
        int playerId = activeId;
        if (players.get(playerId).isDead()) return -1; //active player can't be dead
        playerId = getAliveIdToRight(playerId);
        int ans = 1;
        while(playerId != activeId) {
            ++ans;
            playerId = getAliveIdToRight(playerId);
        }
        return ans;
    }

    public void playTurn(boolean firstTurn) {
        int notAskPlayers = getAlivePlayersNumber();
        while(getMinBet() != getMaxBet() || notAskPlayers > 0) {
            --notAskPlayers;
            processPlayer(activeId, getMaxBet());
            activeId = getAliveIdToRight(activeId);
            if (getAlivePlayersNumber() == 1)
                return;
        }
        if (players.get(smallBetId).isPassed())
            activeId = getAliveIdToRight(smallBetId); else
            activeId = smallBetId;
    }


    private int getMinBet() {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < players.size(); ++i)
            if (!players.get(i).isDead())
                ans = Math.min(ans, players.get(i).getCurrentBet());
        return ans;
    }

    private int getMaxBet() {
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < players.size(); ++i)
            if (!players.get(i).isDead())
                ans = Math.max(ans, players.get(i).getCurrentBet());
        return ans;
    }

    /* First round: start next to big blind
     * Second and later: who has small blind
     *
     */
}
