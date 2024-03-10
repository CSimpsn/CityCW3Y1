package game;
import city.cs.engine.*;
import game.Enemy;
import org.jbox2d.common.Vec2;

import javax.swing.*;

public class NPCEnemy extends Enemy {
    private static final float ATTACK_DISTANCE = 5.0f; // Example attack distance
    public DynamicBody player; // Reference to the player character
    private World world;
    private int moveDirection =1;
    private float minX, maxX;
    private float patrolSpeed = 4f;
    private float attackForce = 20; // Example force for attacking

    public NPCEnemy(World world, float minX, float maxX, DynamicBody player) {
        super(world, 50);
        this.player = player;
        this.world = world;
        this.minX = minX;
        this.maxX = maxX;


        // Set up the NPCEnemy body in the world, similar to how we set up the Projectile
        Shape shape = new BoxShape(1, 1);
        SolidFixture fixture = new SolidFixture(this, shape);
        this.addImage(new BodyImage("data/ghostEnemy.gif", 4)); // Example path and scale
    }

    public void update() {
        Vec2 position = this.getPosition();

        // Move in the current direction
        this.setLinearVelocity(new Vec2(patrolSpeed * moveDirection, 0));

        // Check for boundary conditions and reverse direction if needed
        if (position.x <= minX || position.x >= maxX) {
            moveDirection *= -1; // Reverse direction
        }
    }


    private void attackPlayer() {
        // Apply an impulse towards the player to simulate an attack
        Vec2 direction = new Vec2(2, 2);
        direction.normalize();
        this.applyImpulse(direction.mul(attackForce));
    }
}


