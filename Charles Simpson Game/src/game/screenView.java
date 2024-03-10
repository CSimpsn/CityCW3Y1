package game;

import city.cs.engine.UserView;
import city.cs.engine.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class screenView extends UserView {
    private Image backgroundImage;

    public screenView(World world, int width, int height) {
        super(world, width, height);
        try {
            backgroundImage = ImageIO.read(new File("data/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        if (backgroundImage != null) {
            // Draw the background image to fill the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
