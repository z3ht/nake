package me.zinno.nake.enums;

import me.zinno.nake.App;

import java.awt.event.KeyListener;
import java.util.List;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class ControlsListener implements KeyListener {

    public enum Controls {

        PAUSE(Arrays.asList(KeyEvent.VK_SPACE, KeyEvent.VK_ENTER), GameStatus.PAUSE),
        END_GAME(Arrays.asList(KeyEvent.VK_ESCAPE), GameStatus.END_GAME);

        private final List<Integer> keyEvents;
        private final GameStatus gameStatus;

        Controls(List<Integer> keyEvents, GameStatus gameStatus) {
            this.keyEvents = keyEvents;
            this.gameStatus = gameStatus;
        }

        public List<Integer> getKeyEvents() {
            return keyEvents;
        }

        public GameStatus getGameStatus() {
            return gameStatus;
        }
    }

    private App app;

    public ControlsListener(App app) {
        this.app = app;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(Controls control : Controls.values()) {
            for(int keyEvent : control.getKeyEvents())
                if(keyEvent == e.getExtendedKeyCode())
                    app.checkGameStatus(control.getGameStatus());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
