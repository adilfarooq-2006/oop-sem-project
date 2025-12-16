public class Archer extends Character {//constructors
    //basic constructor
    public Archer(int startX, int startY) {
        super("Archer", 60, 50, 3, startX, startY);
    }
    //Overloaded Contructor for higher level troops
    //higher level troops may have more Attack Damage and HP
    public Archer(int level, int startX, int startY) {
        super("Archer", 60 + (level * 10), 50 + (level * 2), 3, startX, startY);
    }

    @Override
    public void attack(Character target) {
        //get distance from target
        double distance = this.calculateDistance(target);

        if (distance <= 3) {
            //allow attack
            System.out.println(this.getName() + " shots arrow at " + target.getName());
            target.receiveDamage(this.getAttackDamage());
        } else {
            System.out.println("Target is too far for " + this.getName() + " to attack!");
        }
    }
    
    public boolean move(int newX, int newY) {
        // Archer can only move 3 tile at a time
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        
        // Calculate distance to new position
        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        int range = 3;

        // Check if move is within 3 tile (horizontally, vertically, or diagonally)
        if ((deltaX > 0 || deltaY > 0) && deltaX <= range && deltaY <= range) {
            this.getPosition().setX(newX);
            this.getPosition().setY(newY);
            return true;
        } 
        else {
            System.out.println("Invalid move! Archer can only move max 3 tiles.");
            return false;
        }
    }

    @Override 
    public void performAction() {
        System.out.println("Archer is scouting the area...");
    }

    @Override 
    public String getSymbol() {
        return "A";
    }

}