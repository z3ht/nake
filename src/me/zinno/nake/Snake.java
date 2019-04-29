package me.zinno.nake;

import me.zinno.nake.board.Board;
import me.zinno.nake.enums.Direction;
import me.zinno.nake.enums.GameStatus;
import me.zinno.nake.interfaces.Collidable;
import me.zinno.nake.interfaces.Locatable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class Snake implements KeyListener, Locatable, Collidable<Snake>  {
    private Board board;
    private List<Direction> direction;
    private List<Dimension> bodyParts;

    public Snake(Board board) {
        this.board = board;

        this.bodyParts = new LinkedList<>();
        this.bodyParts.add(new Dimension(0, 0));

        this.direction = new LinkedList<>();
        this.direction.add(Direction.EAST);
    }

    public void consumeFood() {
        this.bodyParts.add(this.bodyParts.get(this.bodyParts.size() - 1));
    }

    public void moveSnake() {
        if(direction.size() > 1)
            direction.remove(0);
        this.bodyParts.add(0, this.getFutureLocation());
        this.bodyParts.remove(this.bodyParts.size() - 1);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(Direction.getDirection(e.getExtendedKeyCode()) == null)
            return;

        if(isOppositeDirection(e))
            return;

        this.direction.add(Direction.getDirection(e.getExtendedKeyCode()));
    }

    private boolean isOppositeDirection(KeyEvent e) {
        if(Direction.getDirection(e.getExtendedKeyCode()) == null)
            return false;

        return this.direction.get(direction.size() - 1).ordinal() == (Direction.getDirection(e.getExtendedKeyCode()).ordinal() + 2) % Direction.values().length;
    }

    private Dimension getFutureLocation() {
        return new Dimension((int) this.bodyParts.get(0).getWidth() + this.direction.get(0).getLocation().width,
                (int) this.bodyParts.get(0).getHeight() + this.direction.get(0).getLocation().height);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public List<Dimension> getLocations() {
        return this.bodyParts;
    }

    @Override
    public GameStatus onCollision(Snake snake) {
        return GameStatus.END_GAME;
    }

    @Override
    public boolean isCollided(Snake snake) {
        for(int index = 1; index < snake.getLocations().size(); index += 1) {
            if (this.getLocations().get(0).equals(snake.getLocations().get(index)))
                return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        for(Dimension bodyPart : this.bodyParts)
            board.colorPiece(bodyPart.width, bodyPart.height, Color.GREEN);
    }
}
