package game;

import city.cs.engine.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyChecker extends KeyAdapter {
    private final Map<Integer, Boolean> keyMap = new HashMap<>();

    public KeyChecker() {
        // Initialize keyMap with false for WASD keys
        keyMap.put(KeyEvent.VK_W, false);
        keyMap.put(KeyEvent.VK_A, false);
        keyMap.put(KeyEvent.VK_S, false);
        keyMap.put(KeyEvent.VK_D, false);
        keyMap.put(KeyEvent.VK_E, false);
        keyMap.put(KeyEvent.VK_SPACE, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyMap.containsKey(e.getKeyCode())) {
            keyMap.put(e.getKeyCode(), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyMap.containsKey(e.getKeyCode())) {
            keyMap.put(e.getKeyCode(), false);
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return keyMap.getOrDefault(keyCode, false);
    }
}



