package game;

import city.cs.engine.*;

import java.awt.geom.RectangularShape;

public class Projectile extends DynamicBody {
    public Projectile(World world) {
        super(world);
        // Define the shape of the projectile
        Shape shape = new BoxShape(0.5f, 0.05f); // Example: a small circle
        SolidFixture fixture = new SolidFixture(this, shape);
        this.setBullet(true); // Important for fast-moving objects to prevent tunneling
    }
}


