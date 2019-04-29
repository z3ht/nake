package me.zinno.nake;

import me.zinno.nake.board.Board;
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
        this.board = new Board(size);
    }

    public void initialize() {
        this.hasInitialized = true;

        this.collidables.add(new FoodChain(this.board));
        this.collidables.add(this.board.getBorder());

        this.snake = new Snake(this.board);

        this.board.addKeyListener(this.snake);
    }

    public void beginLoop() {
        if(!this.hasInitialized)
            this.initialize();

        this.shouldLoop = true;
        this.isPlaying = true;

        this.gameThread.start();
    }

    @Override
    public void run() {

        while(this.isPlaying) {

            if(!this.shouldLoop)
                continue;

            try {
                Thread.sleep(this.tickRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Graphics2D g2d = (Graphics2D) this.board.getGraphics().create();

            this.board.draw(g2d);

            this.snake.draw(g2d);

            if(this.snake.isCollided(this.snake))
                checkCollisionStatus(this.snake.onCollision(snake));

            for(Collidable<Snake> collidable : this.collidables) {
                collidable.draw(g2d);
                if(collidable.isCollided(this.snake))
                    checkCollisionStatus(collidable.onCollision(snake));
            }

            this.snake.moveSnake();
        }
    }

    private void checkCollisionStatus(GameStatus gameStatus) {
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
        System.out.println("Game paused");
        this.setShouldLoop(false);
    }

    private void endGame() {
        System.out.println("Game ended");
        this.setPlaying(false);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean shouldLoop() {
        return shouldLoop;
    }

    public void setShouldLoop(boolean shouldLoop) {
        this.shouldLoop = shouldLoop;
    }

    public static void main(String[] args) {
        App app = new App(new Dimension(1800, 600), 200);

        app.initialize();

        app.beginLoop();
    }
}
