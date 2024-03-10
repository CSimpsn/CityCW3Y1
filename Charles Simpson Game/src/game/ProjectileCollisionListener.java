package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ProjectileCollisionListener implements CollisionListener {
    private final int damage = 50; // Damage dealt by the projectile

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Enemy) { // Assuming Enemy class exists
            Enemy enemy = (Enemy) e.getOtherBody();
            enemy.takeDamage(damage);
            e.getReportingBody().destroy(); // Optionally destroy the projectile
        }
    }
}

