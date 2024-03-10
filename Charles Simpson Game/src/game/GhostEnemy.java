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



public class GhostEnemy extends DynamicBody {
    public enum State { PATROLLING, CHASING }
    //public State currentState;
    private State currentState;
    private DynamicBody target;
    private Vec2 patrolStart;
    private Vec2 patrolEnd;
    private float speed = 2.0f;

    public GhostEnemy(World world, DynamicBody target, Vec2 patrolStart, Vec2 patrolEnd) {
        super(world);
        this.target = target;
        this.patrolStart = patrolStart;
        this.patrolEnd = patrolEnd;

        Shape shape = new CircleShape(1); // Example: a simple circle
        GhostlyFixture fixture = new GhostlyFixture(this, shape); // Use GhostlyFixture


        addImage(new BodyImage("data/ghostEnemy.gif", 2)); // Adjust path and size as needed
        //currentState = State.PATROLLING;
    }

    public void updateGhost() {
        switch (currentState) {
            case PATROLLING:
                updatePatrolling();
                break;
            case CHASING:
                updateChasing();
                break;
        }
    }

    private void updatePatrolling() {
        Vec2 currentPosition = this.getPosition();
        Vec2 targetPosition = currentPosition.equals(patrolStart) ? patrolEnd : patrolStart;

        Vec2 toTarget = targetPosition.sub(currentPosition);
        if (toTarget.lengthSquared() < 0.1f) {

            targetPosition = targetPosition.equals(patrolStart) ? patrolEnd : patrolStart;
            toTarget = targetPosition.sub(currentPosition);
        }

        toTarget.normalize();
        this.setLinearVelocity(toTarget.mul(speed));


        if (playerDetected()) {
            currentState = State.CHASING;
        }
    }

    private void updateChasing() {
        Vec2 toTarget = target.getPosition().sub(this.getPosition());
        toTarget.normalize();
        this.setLinearVelocity(toTarget.mul(speed));


    }

    private boolean playerDetected() {
        return this.getPosition().sub(target.getPosition()).length() < 10.0f; // Example detection radius
    }
}