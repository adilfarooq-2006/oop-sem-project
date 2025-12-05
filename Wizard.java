public class Wizard extends Character{
    //constructors
    //basic constructor
    public Wizard(int startX, int startY) {
        super("Wizard", 80, 40, 2, startX, startY);
    }
    //Overloaded Contructor for higher level troops
    //higher level troops may have more Attack Damage and HP
    public Wizard(int level, int startX, int startY) {
        super("Wizard", 80 + (level * 10), 40 + (level * 2), 2, startX, startY);
    }

    @Override
    public void attack(Character target) {
        //get distance from target
        double distance = this.calculateDistance(target);

        if (distance <= 2.0) {
            //allow attack
            System.out.println(this.getName() + " casts the fireball at " + target.getName());
            target.receiveDamage(this.getAttackDamage());
        } else {
            System.out.println("Target is too far for " + this.getName() + " to attack!");
        }
    }
    
    public boolean move(int newX, int newY) {
        // Wizard can only move 1 tile at a time
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        
        // Calculate distance to new position
        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        // Check if move is within 1 tile (horizontally, vertically, or diagonally)
        if (deltaX <= 2 && deltaY <= 2 && (deltaX + deltaY) > 0) {
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
        System.out.println("Wizard throws a fireball!");
    }

    @Override 
    public String getSymbol() {
        return "W";
    }    
}
