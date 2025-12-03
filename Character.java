public class Character {

    String name;
    int currentHP;
    int maxHP;
    int attackDamage;
    int attackRange;
    Position position;   

    Character(String name, int currentHP, int maxHP, int attackDamage, int attackRange) {
        this.name = name;
        this.currentHP = currentHP;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.position = new Position(0, 0);
    }
}