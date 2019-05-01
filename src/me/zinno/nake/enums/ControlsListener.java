package me.zinno.nake.enums;

import me.zinno.nake.App;

import java.awt.event.KeyListener;
import java.util.List;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.function.Function;

public class ControlsListener implements KeyListener {

    public enum Controls {

        PAUSE(Arrays.asList(KeyEvent.VK_SPACE, KeyEvent.VK_ENTER), (app) -> {
            if(!app.isPlaying()) {
                app.restartGame();
                return GameStatus.CONTINUE;
            } else
                return GameStatus.PAUSE;
        }),
        END_GAME(Arrays.asList(KeyEvent.VK_ESCAPE), (app) -> GameStatus.END_GAME);

        private final List<Integer> keyEvents;
        private final Function<App, GameStatus> onPressFunction;

        Controls(List<Integer> keyEvents, Function<App, GameStatus> onPressFunction) {
            this.keyEvents = keyEvents;
            this.onPressFunction = onPressFunction;
        }

        public List<Integer> getKeyEvents() {
            return keyEvents;
        }

        public Function<App, GameStatus> getOnPressFunction() {
            return onPressFunction;
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
                    app.checkGameStatus(control.getOnPressFunction().apply(this.app));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
