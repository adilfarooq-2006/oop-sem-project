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

            // Update Stats
            String p1Text = "P1 (" + turnOrder.get(0).getName() + "): " + turnOrder.get(0).getCurrentHP() + " HP";
            String p2Text = "P2 (" + turnOrder.get(1).getName() + "): " + turnOrder.get(1).getCurrentHP() + " HP";
            String turnMsg = "Turn: " + currPlayer.getName();
            
            gui.updateStats(p1Text, p2Text, turnMsg);
            gui.refresh(); 

            // Wait for Action
            String action = gui.waitForAction(); 

            try {
                if (action.equals("MOVE")) {
                    handleMove(currPlayer, gui);
                    // If move is successful:
                    gui.showMessage("Move successful!", false);
                }
                
                else if (action.equals("ATTACK")) {
                    // 1. Check Range First (Validation)
                    if (currPlayer.calculateDistance(enemy) > currPlayer.getAttackRange()) {
                        throw new Exception("Target is too far!");
                    }

                    // 2. Perform Attack Logic
                    int dmg = currPlayer.getAttackDamage(); // Get the damage amount
                    currPlayer.attack(enemy);

                    // 3. BUILD THE MESSAGE
                    String combatMsg = enemy.getName() + " took " + dmg + " damage! (HP: " + enemy.getCurrentHP() + ")";

                    // 4. TRIGGER VISUALS
                    gui.showMessage(combatMsg, true); // Show message (Red text for impact)
                    // gui.shakeWindow(); // <--- SHAKE THE SCREEN!

                    // 5. Update Stats Bar immediately so we see the HP drop
                    gui.updateStats(p1Text, p2Text, "ATTACK HIT!");

                    try { Thread.sleep(2000); } catch (InterruptedException ex) {}

                    // 6. Check Death
                    if (!enemy.isAlive()) {
                        gui.updateStats(p1Text, p2Text, "GAME OVER! " + currPlayer.getName() + " Wins!");
                        gameOver = true;
                    }
                }
                
            } catch (Exception e) {
                // --- THE FIX IS HERE ---
                // 1. Show the error on the UI in RED
                gui.showMessage("Error: " + e.getMessage(), true);
                gui.shakeWindow();
                
                // 2. IMPORTANT: Pause so the user can read it!
                // If we don't pause, the loop restarts and overwrites the text immediately.
                System.out.println("UI Message: " + e.getMessage()); // Keep log for debug
                try { Thread.sleep(2000); } catch (InterruptedException ex) {} 
                
                // 3. Continue the loop (don't switch turns if it was an error)
                continue; 
            }

            // Switch Turn (Only if no error occurred)
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
    private void handleAttack(Character attacker, Character enemy) throws Exception {
        System.out.println("Attacking: " + enemy.getName());

        double distance = attacker.calculateDistance(enemy);
        if (distance > attacker.getAttackRange()) {
            throw new Exception("Target is too far to attack!");
        }
        
        attacker.attack(enemy);
    }
} 
