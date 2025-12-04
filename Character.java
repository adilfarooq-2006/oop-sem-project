public abstract class Character implements Moveable, Attacker{

    private String name;
    private int currentHP;
    private int maxHP;
    private int attackDamage;
    private int attackRange;
    protected Position position;

    //constructor
    Character(String name, int currentHP, int maxHP, int attackDamage, int attackRange, int x, int y) {
        this.name = name;
        this.currentHP = currentHP;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.position = new Position(x, y);
    }


    //setters
    public void setPosition(Position p) {
        this.position = p;
    }


    //getters
    public String getName() {
        return name;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public Position getPosition() {
        return position;
    }


    //methods
    public double calculateDistance(Character target) {
        //we call the pythagoras distance calculating method made in Position Class
        return this.position.getDistance(target.getPosition());
    }

    public void receiveDamage(int damageAmount) {
        this.currentHP -= damageAmount;

        System.out.println(this.name + " took " + damageAmount + " damage! Current HP: " + this.currentHP);

        if (this.currentHP <= 0) {
            this.currentHP = 0;
            System.out.println(this.name + " has been defeated!");
        }
    }
    
    public boolean isAlive() {
        return currentHP > 0;
    }

    @Override
    public String toString() {
        return name + " [HP: " + currentHP + ", Pos: " + position + "]";
    }

    //abstract Method
    public abstract void performAction();

}