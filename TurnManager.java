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
            System.out.println("Turn: " + currPlayer.getName());
            System.out.println("HP: " + currPlayer.getCurrentHP() + " | Position: " + currPlayer.getPosition());

            System.out.println("Choose your action: \n[1] Move\n[2] Attack");
            int choice = input.nextInt();

            try {
                if (choice == 1) {
                    handleMove(currPlayer);
                } 
                else if (choice == 2) {
                    handleAttack(currPlayer, enemy);

                    if (!enemy.isAlive()) {
                        System.out.println("\nGAME OVER");
                        System.out.println(currPlayer.getName() + " wins the game");
                        gameOver = true;
                    }
                }
                else {
                    System.out.println("Ivalid Input. Turn Skipped.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

            //switch turn
            if (!gameOver) {
                currPlayerIndex = (currPlayerIndex + 1) % 2;
                System.out.println("\n----------------------\n");
            }
        }
        input.close();
    }

    //function to handle movement
    private void handleMove(Character player) throws InvalidMoveException {
        System.out.println("Enter X coordinate: ");
        int x = input.nextInt();
        System.out.println("Enter Y coordinate: ");
        int y = input.nextInt();

        grid.moveCharacter(player, x, y);
    }

    //function to habdle attack logic
    private void handleAttack(Character attacker, Character enemy) {
        System.out.println("Attacking: " + enemy.getName());
        attacker.attack(enemy);
    }
} 
