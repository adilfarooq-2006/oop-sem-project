import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TurnManager {
    private GameGrid grid;
    private GameWindow gui;
    private List<Character> turnOrder;
    private int currPlayerIndex;
    private Scanner input;

    // Allow the Main class to grab the grid data for the UI
    public Character[][] getBoardData() {
        return grid.getGrid();
    }

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

    public void startGame(GameWindow gui) {
        boolean gameOver = false;

        while (!gameOver) {
            Character currPlayer = turnOrder.get(currPlayerIndex);
            Character enemy = turnOrder.get((currPlayerIndex + 1) % 2);

            // 1. UPDATE STATS
            String p1Text = "P1 (" + turnOrder.get(0).getName() + "): " + turnOrder.get(0).getCurrentHP() + " HP";
            String p2Text = "P2 (" + turnOrder.get(1).getName() + "): " + turnOrder.get(1).getCurrentHP() + " HP";
            String turnMsg = "Current Turn: " + currPlayer.getName(); // This goes to the Big Label

            gui.updateStats(p1Text, p2Text, turnMsg);
            gui.refresh(); 

            // 2. WAIT FOR ACTION
            // This will now update the smaller Instruction Label below
            String action = gui.waitForAction(); 

            try {
                if (action.equals("MOVE")) {
                    handleMove(currPlayer, gui);
                } 
                else if (action.equals("ATTACK")) {
                    // If you haven't updated handleAttack yet, 
                    // it will print to console. 
                    // To fix that, use the same logic:
                    System.out.println("Attack selected..."); 
                    handleAttack(currPlayer, enemy); 
                    
                    if (!enemy.isAlive()) {
                        gui.updateStats(p1Text, p2Text, "GAME OVER! " + currPlayer.getName() + " Wins!");
                        gameOver = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

            if (!gameOver) {
                currPlayerIndex = (currPlayerIndex + 1) % 2;
            }
        }
    }

    //function to handle movement
    private void handleMove(Character player, GameWindow gui) throws InvalidMoveException {
        System.out.println(player.getName() + ", click where you want to move...");
    
        // 1. Wait for the user to click a tile
        // The program will PAUSE here until you click!
        Point click = gui.waitForClick();
        
        int x = (int) click.getX();
        int y = (int) click.getY();

        System.out.println("Destination selected: " + x + "," + y);

        // 2. Perform the move
        grid.moveCharacter(player, x, y);
    }

    //function to habdle attack logic
    private void handleAttack(Character attacker, Character enemy) {
        System.out.println("Attacking: " + enemy.getName());
        attacker.attack(enemy);
    }
} 
