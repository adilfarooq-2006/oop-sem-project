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
            System.out.println(this.getName() + " swings sword at " + target.getName());
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
        System.out.println("Wizard throws a fireball!");
    }

    @Override 
    public String getSymbol() {
        return "W";
    }    
}
