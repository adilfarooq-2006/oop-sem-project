public class PEKKA extends Character {
    //constructors
    //basic constructor
    public PEKKA(int startX, int startY) {
        super("P.E.K.K.A", 200, 75, 1, startX, startY);
    }
    //Overloaded Contructor for higher level troops
    //higher level troops may have more Attack Damage and HP
    public PEKKA(int level, int startX, int startY) {
        super("P.E.K.K.A", 200 + (level * 10), 75 + (level * 2), 1, startX, startY);
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
        //Barbarian Moves 1 Tile only
        this.getPosition().setX(newX);
        this.getPosition().setY(newY);
        return true;
    }

    @Override 
    public void performAction() {
        System.out.println("BUTTERFLY!");
    }

    @Override 
    public String getSymbol() {
        return "P";
    }
}
