package me.zinno.nake;

import me.zinno.nake.board.Board;
import me.zinno.nake.enums.ControlsListener;
import me.zinno.nake.enums.GameStatus;
import me.zinno.nake.interfaces.Collidable;

import java.util.LinkedList;
import java.util.List;

import java.awt.*;

public class App implements Runnable {

    private boolean isPlaying;
    private boolean shouldLoop;
    private boolean hasInitialized;

    private Thread gameThread;
    private int tickRate;
    private Board board;

    private Snake snake;
    private List<Collidable<Snake>> collidables;

    public App(Dimension size, int tickRate) {
        this.gameThread = new Thread(this);
        this.tickRate = tickRate;

        this.collidables = new LinkedList<>();

        this.board = new Board(new Dimension(Math.min(Math.max(size.width, 300), Toolkit.getDefaultToolkit().getScreenSize().width - 200),
                Math.min(Math.max(size.height, 300), Toolkit.getDefaultToolkit().getScreenSize().height - 200)));
    }

    public void initialize() {
        this.hasInitialized = true;

        this.collidables.add(new FoodChain(this.board));
        this.collidables.add(this.board.getBorder());

        this.snake = new Snake(this.board);

        this.board.addKeyListener(this.snake);
        this.board.addKeyListener(new ControlsListener(this));
    }

    public void beginLoop() {
        if(!this.hasInitialized)
            this.initialize();

        this.shouldLoop = false;
        this.isPlaying = true;

        this.gameThread.start();

        try {
            Thread.sleep(2500);
            this.setLooping(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(this.isPlaying) {

            if(this.shouldLoop)
                this.snake.moveSnake();

            try {
                Thread.sleep(this.tickRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Graphics2D g2d = (Graphics2D) this.board.getGraphics().create();

            this.board.draw(g2d);

            if(this.snake.isCollided(this.snake))
                checkGameStatus(this.snake.onCollision(snake));

            for(Collidable<Snake> collidable : this.collidables) {
                collidable.draw(g2d);
                if(collidable.isCollided(this.snake))
                    checkGameStatus(collidable.onCollision(snake));
            }

            this.snake.draw(g2d);
        }
    }

    public void checkGameStatus(GameStatus gameStatus) {
        switch (gameStatus) {
            case CONTINUE:
                return;
            case PAUSE:
                pauseGame();
                return;
            case END_GAME:
                endGame();
                return;
        }
    }

    private void pauseGame() {
        this.setLooping(!this.isLooping());
    }

    private void endGame() {
        this.setPlaying(!this.isPlaying());
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isLooping() {
        return shouldLoop;
    }

    public void setLooping(boolean shouldLoop) {
        this.shouldLoop = shouldLoop;
    }

    public static void main(String[] args) {
        App app = new App(new Dimension(1600, 1000), 100);

        app.initialize();

        app.beginLoop();
    }
}
