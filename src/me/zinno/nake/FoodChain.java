package me.zinno.nake;

import me.zinno.nake.board.Board;
import me.zinno.nake.enums.GameStatus;
import me.zinno.nake.interfaces.Collidable;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FoodChain implements Collidable<Snake> {

    private Dimension foodLocation;
    private Board board;

    public FoodChain(Board board) {
        this.board = board;
        this.generateFood();
    }

    public void generateFood() {
        int randX = new Random().nextInt(board.getNumPieces().width);
        int randY = new Random().nextInt(board.getNumPieces().height);

        this.foodLocation = new Dimension(randX, randY);
    }

    @Override
    public List<Dimension> getLocations() {
        return Arrays.asList(this.foodLocation);
    }

    @Override
    public GameStatus onCollision(Snake snake) {
        this.generateFood();
        snake.consumeFood();
        return GameStatus.CONTINUE;
    }

    @Override
    public void draw(Graphics2D g2d) {
        this.board.colorPiece(this.foodLocation.width, this.foodLocation.height, Color.RED);
    }
}
