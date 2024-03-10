package game;
import city.cs.engine.*;

public class Enemy extends DynamicBody{
    protected int health;
    protected float xPosition, yPosition;

    public Enemy(World world, int health) {
        super(world);
        this.health = health;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            this.destroy(); // Destroy the enemy if health is depleted
        }
    }
}



