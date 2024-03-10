package game;

import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class PlatfromClass {
    public PlatfromClass(World world, float width, float height,float xPos, float yPos){
        Shape platformShape = new BoxShape(width/2, height/2);
        StaticBody platform1 = new StaticBody(world, platformShape);
        platform1.setPosition(new Vec2(xPos, yPos));
    }
}
