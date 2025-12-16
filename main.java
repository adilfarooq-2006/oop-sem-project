import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        TurnManager game = new TurnManager();
        System.out.println("Starting the game....");

        //p1 selection
        System.out.println("Player 1: Choose your Character ");
        Character p1 = selectPlayer(input, 0, 0);
        game.addPlayer(p1);

        //p2 selection
        System.out.println("\nPlayer 2: Choose your Character");
        Character p2 = selectPlayer(input, 7, 7);
        game.addPlayer(p2);

         System.out.println("\nInitializing User Interface...");
        // Create the window using the data from TurnManager
        GameWindow ui = new GameWindow(game.getBoardData());

        System.out.println("\nInitializing the GAME..." + p1.getName() + " VS " + p2.getName());
        game.startGame(ui);

       
    }

    public static Character selectPlayer(Scanner input, int startX, int startY) {
        while (true) {
            System.out.println("1. Barbarian (Balanced, Melee)");
            System.out.println("2. Archer    (Low HP, Long Range)");
            System.out.println("3. Wizard    (High Dmg, Mid Range)");
            System.out.println("4. PEKKA     (Tank, High Dmg, Melee)");
            System.out.print("Enter choice (1-4): ");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    return new Barbarian(startX, startY);
                case 2:
                    return new Archer(startX, startY);
                case 3:
                    return new Wizard(startX, startY);
                case 4:
                    return new PEKKA(startX, startY);
                default:
                    System.out.println("Invalid choice. Please pick 1-4.");
            }
        }
    }
}      