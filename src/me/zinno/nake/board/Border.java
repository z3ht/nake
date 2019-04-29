package me.zinno.nake.board;

import me.zinno.nake.Snake;
import me.zinno.nake.enums.GameStatus;
import me.zinno.nake.interfaces.Collidable;

import java.awt.*;
import java.util.List;

public class Border implements Collidable<Snake> {

    private Board board;
    private int width;
    private int height;


    public Border(Board board, int width, int height) {
        this.board = board;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public GameStatus onCollision(Snake snake) {
        return GameStatus.END_GAME;
    }

    @Override
    public boolean isCollided(Snake snake) {
        Dimension futureLoc = snake.getFutureLocation();
        return futureLoc.width < 0 || futureLoc.width >= board.getNumPieces().width ||
                futureLoc.height < 0 || futureLoc.height >= board.getNumPieces().height;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);

        g2d.fillRect(0, 0, this.board.getWidth(), this.height);
        g2d.fillRect(0, this.board.getHeight() - this.getHeight(), this.board.getWidth(), this.board.getHeight());

        g2d.fillRect(0, 0, this.width, this.board.getHeight());
        g2d.fillRect(this.board.getWidth() - this.getWidth(), 0, this.board.getWidth(), this.board.getHeight());
    }

    @Override
    public List<Dimension> getLocations() {
        return null;
    }

}
