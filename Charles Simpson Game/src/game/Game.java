package game;
import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import java.awt.*;
import javax.swing.*;
import javax.swing.JProgressBar;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


public class Game {
    private World world;
    public DynamicBody character;
    private KeyChecker keyChecker;
    private JFrame frame;
    private boolean onGround = true;
    private NPCEnemy npcEnemy;
    private long lastShotTime = 0;
    private float characterHealth = 100;
    private JProgressBar healthBar;

    public Game() {
        world = new World();
        screenView view = new screenView(world, 1500, 800);

        frame = new JFrame("City Game");
        keyChecker = new KeyChecker();
        javax.swing.Timer gameTimer = new javax.swing.Timer(20, e -> {
            try {
                npcEnemy.update();
                updateMovement(view);
                //GhostEnemy.updateGhost();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gameTimer.start();

        Shape shape = new BoxShape(50, 0.5f);
        StaticBody ground = new StaticBody(world, shape);
        ground.setPosition(new Vec2(-0f, -15.5f));


        new PlatfromClass(world, 17, 0.1f, 2.5f, -9f);
        new PlatfromClass(world, 16.5f, 0.1f, 21.5f, 0.5f);
        new PlatfromClass(world, 11, 0.1f, 10.5f, -13f);
        new PlatfromClass(world, 7, 0.1f, -24f, -9f);
        new PlatfromClass(world, 12, 0.1f, -33.5f, 0.5f);

        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100); // Initial health value
        healthBar.setStringPainted(true); // Show health percentage as a string
        frame.add(healthBar, BorderLayout.NORTH); // Add health bar to the top of the frame

        Shape characterShape = new BoxShape(1, 1);
        character = new DynamicBody(world, characterShape);
        character.setPosition(new Vec2(7, -9));
        character.addImage(new BodyImage("data/character idle.gif", 2f));
        character.addCollisionListener(new GroundListener(this));


        //npcEnemy = new NPCEnemy(world, -10f, 0f, character);
        npcEnemy = new NPCEnemy(world,-35,35,character);
        GhostEnemy ghostEnemy = new GhostEnemy(world,character,(new Vec2(10,10)), (new Vec2 (-10,-10)));
        ghostEnemy.setPosition(new Vec2(10, -10));

        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(keyChecker);
        frame.setLayout(new BorderLayout());

        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100); // Start with full health
        healthBar.setStringPainted(true); // Show health percentage

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point mousePosition = frame.getMousePosition(true);
                if (mousePosition != null) {
                    shoot(mousePosition,view);
                }
            }
        });

        world.setGravity(150);
        world.start();
    }

    public void reduceHealth(int damage) {
        characterHealth -= damage;
        if (characterHealth < 0) characterHealth = 0; // Prevent health from going below 0
        healthBar.setValue((int)characterHealth); // Update the health bar

        // Additional logic for what happens when health reaches 0
        if (characterHealth <= 0) {

        }
    }

    private void updateMovement(screenView view) {
        Vec2 velocity = character.getLinearVelocity();

        // Check left movement
        if (keyChecker.isKeyPressed(KeyEvent.VK_A)) {
            velocity.x = -10; // Move left
        }

        // Check right movement
        if (keyChecker.isKeyPressed(KeyEvent.VK_D)) {
            velocity.x = 10; // Move right
        }

        // Update velocity to the character
        character.setLinearVelocity(velocity);

        // Jumping
        if (keyChecker.isKeyPressed(KeyEvent.VK_SPACE) && onGround) {
            character.applyImpulse(new Vec2(0, 250));
            onGround = false; // Assume off ground after jumping
        }

        if (keyChecker.isKeyPressed(KeyEvent.VK_E)) {
            Point mousePosition = getMousePositionRelativeToFrame();
            if (mousePosition != null) {
                shoot(mousePosition, view);

            }
        }
    }


    private Point getMousePositionRelativeToFrame() {
        return frame.getMousePosition(true);


    }


    private void shoot(Point mousePosition,screenView view) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime < 500) {
            // Not enough time has passed, so don't shoot
            return;
        }
        Vec2 characterPosition = character.getPosition();
        Vec2 mouseVec = view.viewToWorld(new Point2D.Float(mousePosition.x, mousePosition.y));

        Vec2 direction = mouseVec.sub(characterPosition);
        direction.normalize(); // Normalize to get the direction vector

        // Now use this direction for shooting
        Vec2 impulse = direction.mul(10.0f); // Adjust the multiplier  for the force
        Projectile projectile = new Projectile(world);
        projectile.setPosition(characterPosition.add(direction)); // Start the projectile at the character's position
        projectile.applyImpulse(impulse);
        projectile.addCollisionListener(new ProjectileCollisionListener());
        lastShotTime = currentTime;
        System.out.println("char"+characterPosition);
        System.out.println("mouse" +mouseVec);
    }

    public void setOnGround(boolean b) {
        this.onGround = b;
    }


    static class GroundListener implements CollisionListener {
        private Game game;

        public GroundListener(Game game) {
            this.game = game;
        }

        @Override
        public void collide(CollisionEvent e) {
            if (e.getOtherBody() instanceof StaticBody) {
                game.setOnGround(true);
            }
        }

    }

    public static void main(String[] args) {

        new Game();

    }
}



