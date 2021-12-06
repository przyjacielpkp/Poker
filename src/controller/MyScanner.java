package controller;

import model.game.Player;
import model.rules.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MyScanner {

    static BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

    public enum PlayerAction {
        Check,
        Call,
        Raise,
        Fold,
        Allin
    }

    public static Integer askRaise(Player player, int actualBet) {
        try {
            System.out.println("How much do you want to raise? (from " + (actualBet - player.getCurrentBet()) + " to " + player.getMoney() + ")");
            while(true) {
                String rd = bufferReader.readLine();
                try {
                    int bet = Integer.parseInt(rd);
                    if (bet > player.getMoney()) {
                        System.out.println("You can't bet more than you have");
                        continue;
                    }
                    return bet;
                } catch (NumberFormatException e) {
                    System.out.println("Bet doesn't look like correct value, try again");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer askNumberOfPlayers() {
        try {
            System.out.println("How much Players do you want?");
            while(true) {
                String rd = bufferReader.readLine();
                try {
                    return Integer.parseInt(rd);
                } catch (NumberFormatException e) {
                    System.out.println("Bet doesn't look like correct value, try again");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getStringCards(List<Card> list) {
        StringBuilder s = new StringBuilder();
        for (Card c : list) {
            s.append(c.toString()).append(" ");
        }
        return s.toString();
    }

    public static void showCards(List<Card> list) {

        System.out.println("Cards are about to be shown, press any key to continue...");
        try{
            bufferReader.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(getStringCards(list));
    }

    public static Integer askEntryFee() {
        try {
            System.out.println("How much is entry fee?");
            while(true) {
                String rd = bufferReader.readLine();
                try {
                    return Integer.parseInt(rd);
                } catch (NumberFormatException e) {
                    System.out.println("Bet doesn't look like correct value, try again");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void listWinners(List<Player> ppl, List<Integer> ind) {
        System.out.println("Congratulations to the winners!!");
        for (int i : ind) {
            System.out.println("Player " + i + "with cards" + getStringCards(ppl.get(i).getHandCards()));
        }
    }

    public static PlayerAction askRegular(int playerId, Player player, int actualBet) {
        try {
            System.out.println("\nPlayer " + playerId + "turn!!!");

            System.out.println("Actual bet is " + actualBet + ". Your bet is " + player.getCurrentBet());
            System.out.println("Press any key to continue...");
            bufferReader.readLine();
            System.out.println("Your cards are: " + player.getHandCards());
            if (player.getMoney() + player.getCurrentBet() <= actualBet) {
                System.out.println("You can either Fold or go All-in");
                while (true) {
                    String rd = bufferReader.readLine();
                    if (rd.equals("Fold")) return PlayerAction.Fold;
                    if (rd.equals("All-In")) return PlayerAction.Allin;
                    System.out.println("Unknown command. Write again");
                }
            } else {
                String check = "";
                if (player.getCurrentBet() == actualBet)
                    check = "Check, ";
                String call = "";
                if (player.getCurrentBet() != actualBet)
                    check = "Call, ";
                System.out.println("You can: " + check + call + "Fold, Raise or go All-In.");
                while (true) {
                    String rd = bufferReader.readLine();
                    if (rd.equals("Check")) {
                        if (player.getCurrentBet() < actualBet) {
                            System.out.println("You can't check when your current bet is too small");
                            continue;
                        }
                        return PlayerAction.Check;
                    }
                    if (rd.equals("Call")) {
                        if (player.getCurrentBet() == actualBet) {
                            System.out.println("You can't call when your current bet is the max (use check instead)");
                            continue;
                        }
                        return PlayerAction.Call;
                    }
                    if (rd.equals("Fold")) return PlayerAction.Fold;
                    if (rd.equals("Raise")) return PlayerAction.Raise;
                    if (rd.equals("All-In")) return PlayerAction.Allin;
                    System.out.println("Unknown command. Write again");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
