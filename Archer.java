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

        if (distance <= 1.5) {
            //allow attack
            System.out.println(this.getName() + " shots arrow at " + target.getName());
            target.receiveDamage(this.getAttackDamage());
        } else {
            System.out.println("Target is too far for " + this.getName() + " to attack!");
        }
    }
    
    public boolean move(int newX, int newY) {
        //Barbarian Moves 1 Tile only
        this.getPosition().setX(newX);
        this.getPosition().setY(newY);
        return true;
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