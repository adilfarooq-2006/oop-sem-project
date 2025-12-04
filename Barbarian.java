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
        //Barbarian Moves 1 Tile only
        this.getPosition().setX(newX);
        this.getPosition().setY(newY);
        return true;
    }

    @Override 
    public void performAction() {
        System.out.println("Barbaian shouts: 'Grrw!!' ");
    }


}
