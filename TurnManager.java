import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TurnManager {
    private GameGrid grid;
    private List<Character> turnOrder;
    private int currPlayerIndex;
    private Scanner input;

    //constructor
    public TurnManager() {
        this.turnOrder = new ArrayList<>();
        this.currPlayerIndex = 0; //start with player 1

        this.input = new Scanner(System.in);

        //initialze the gameGrid
        this.grid = new GameGrid(8, 8);

    }
    
    public void addPlayer(Character player) {
        turnOrder.add(player);

        //place charcter on the grid
        grid.placeCharacter(player, player.getPosition().getX(), player.getPosition().getY());
    }

    public void startGame() {
        System.out.println("--- Game Start: CLASH TACTICS ---");
        boolean gameOver = false;

        while (!gameOver) {
            //get the current player
            Character currPlayer = turnOrder.get(currPlayerIndex);
            //get the opponenet player (who is not currPlayer)
            Character enemy = turnOrder.get((currPlayerIndex + 1) % 2);

            //show the board
            grid.printBoard();
        }
    }
} 
