import controller.GameManager;
import controller.GameManagerImpl;

public class Main {

    public static void main(String[] args) {

        GameManager gameManager= new GameManagerImpl();
        gameManager.startGame();

        while(!gameManager.isFinished()){
            gameManager.startNewRound();
            System.out.println(gameManager.isFinished());
        }
    }
}
