public class GameGrid {
    private int rows;
    private int cols;
    private Character[][] board; //this would be 2D map

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
    public void moveCharacter(Character character, int x, int y) throws InvalidMoveException {
        //check if the position is valid (empty)
        if (!isValidCoordinate(x, y)) {
            throw new InvalidMoveException("Move is out of bounds");
        }

        //check if a character is already on the requested coordinates
        if (board[y][x] != null) {
            throw new InvalidMoveException("Tile is already occupied by: " + board[y][x].getName());
        }

        //move is valid
        Position currPos = character.getPosition();

        //remove the old pos
        board[currPos.getY()][currPos.getY()] = null;

        //update the characters state
        character.move(x, y);

        //place character at new spot
        board[y][x] = character;

        System.out.println(character.getName() + " is moved to (" + x + "," + y + ")");
    }
    
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    //method to print the board on console
    public void printBoard() {
        System.out.println("---Game Board---");
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == null) {
                    System.out.print(". "); // Empty spot
                } else {
                    // Print first letter of the character (B for Barbarian, A for Archer)
                    System.out.print(board[i][j].getName().charAt(0) + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("------------------");
    }
}
