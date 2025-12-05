public class Barbarian extends Character {
    //constructors
    //basic constructor
    public Barbarian(int startX, int startY) {
        super("Barbarain", 100, 20, 1, startX, startY);
    }
    //Overloaded Contructor for higher level troops
    //higher level troops may have more Attack Damage and HP
    public Barbarian(int level, int startX, int startY) {
        super("Barbarain", 100 + (level * 10), 20 + (level * 2), 1, startX, startY);
    }

    @Override
    public void attack(Character target) {
        //get distance from target
        double distance = this.calculateDistance(target);

        if (distance <= 1.5) {
            //allow attack
            System.out.println(this.getName() + " swings sword at " + target.getName());
            target.receiveDamage(this.getAttackDamage());
        } else {
            System.out.println("Target is too far for " + this.getName() + " to attack!");
        }
    }
    
    public boolean move(int newX, int newY) {
        // Barbarian can only move 1 tile at a time
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        
        // Calculate distance to new position
        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        // Check if move is within 1 tile (horizontally, vertically, or diagonally)
        if (deltaX <= 1 && deltaY <= 1 && (deltaX + deltaY) > 0) {
            this.getPosition().setX(newX);
            this.getPosition().setY(newY);
            return true;
        } 
        else {
            System.out.println("Invalid move! Barbarian can only move 1 tile.");
            return false;
        }
    }

    @Override 
    public void performAction() {
        System.out.println("Barbaian shouts: 'Grrw!!' ");
    }

    @Override 
    public String getSymbol() {
        return "B";
    }


}
