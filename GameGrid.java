public class GameGrid {
    private int rows;
    private int cols;
    private Character[][] board; //this would be 2D map

    public Character[][] getGrid() {
        return this.board; // Returns the reference to the actual board array
    }

    public GameGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Character[rows][cols];
    }

    //fucntion to place character for first time on board
    public void placeCharacter(Character character, int x, int y) {
        //check if the position is valid (empty)
        if (isValidCoordinate(x, y) && board[y][x] == null) {
            board[y][x] = character;

            //update character coordinates now also
            character.getPosition().setX(x);
            character.getPosition().setY(y);
        } else {
            System.out.println("Cannot place the character at " + "(" + x + "," + y + ")");
        }
    }
    
    //fucntion that moves the character
    public void moveCharacter(Character character, int newX, int newY) throws InvalidMoveException {
    
    // 1. Check if the Tile is valid
    if (!isValidCoordinate(newX, newY)) {
        throw new InvalidMoveException("Out of bounds!");
    }
    
    // 2. Check for collision
    if (board[newY][newX] != null) {
        throw new InvalidMoveException("Tile occupied!");
    }

    // 3. CAPTURE OLD POSITION (Must be Integers!)
    // We do this BEFORE calling move(), so we remember where we started.
    int oldX = character.getPosition().getX();
    int oldY = character.getPosition().getY();

    // 4. ATTEMPT MOVE
    // If this fails (returns false), we throw exception and STOP.
    // The board is never touched.
    if (!character.move(newX, newY)) {
        throw new InvalidMoveException("Invalid move for this character!");
    }

    // 5. CLEAR OLD SPOT
    // We use the SAVED integers (oldX, oldY), not current character position
    board[oldY][oldX] = null;

    // 6. UPDATE NEW SPOT
    board[newY][newX] = character;

    System.out.println(character.getName() + " moved to (" + newX + "," + newY + ")");
}
    
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    //method to print the board on console
    public void printBoard() {
    System.out.println("---Game Board---");

    // 1. Print Column Headers (X Coordinates)
    System.out.print("    "); // 4 spaces to align with the row label | start
    for (int j = 0; j < cols; j++) {
        System.out.print(j + " ");
    }
    System.out.println(); // Move to next line after printing headers

    // Main Board Loop
    for (int i = 0; i < rows; i++) {
        
        // 2. Print Row Label (Y Coordinate) followed by the border
        System.out.print(i + " | "); 

        for (int j = 0; j < cols; j++) {
            if (board[i][j] == null) {
                System.out.print(". "); 
            } else {
                System.out.print(board[i][j].getName().charAt(0) + " ");
            }
        }
        System.out.println("|");
    }
    System.out.println("------------------");
}
}
