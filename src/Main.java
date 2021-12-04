import model.game.Game;
import model.game.GameImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Insert number of players:");
        Scanner scanner = new Scanner(System.in);
        int playerNumber = scanner.nextInt();
        Game newGame = new GameImpl(playerNumber);
        newGame.StartRound();
    }
}
