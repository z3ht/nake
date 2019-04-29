package me.zinno.nake.board;

import me.zinno.nake.interfaces.Drawable;

import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame implements Drawable {

    private Dimension size;
    private Dimension pieceSize;
    private Dimension numPieces;

    private Color boardColor;

    private Border border;

    private List<Dimension> occupiedLocations;

    public Board(Dimension size) {
        this(size, new Dimension(50, 50));
    }

    public Board(Dimension size, Dimension pieceSize) {

        size.setSize(size.width - (size.width%pieceSize.width), size.height - (size.height%pieceSize.height));
        this.size = size;
        this.pieceSize = pieceSize;
        this.numPieces = new Dimension(this.size.width/this.pieceSize.width - 1, this.size.height/this.pieceSize.height - 1);

        this.occupiedLocations = new LinkedList<>();

        this.border = new Border(this, 50, 50);

        this.boardColor = Color.WHITE;

        initialize();
    }

    private void initialize() {
        setIconImage(this.getToolkit().getImage(this.getClass().getResource("/snake.png")));
        setSize((int) this.size.getWidth() + this.border.getWidth(), (int) this.size.getHeight() + this.border.getHeight());

        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Snake");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void colorPiece(int x, int y, Color color) {
        if(color != this.boardColor)
            this.occupiedLocations.add(new Dimension(x, y));

        Graphics2D g2d = (Graphics2D) this.getGraphics().create();
        g2d.setColor(color);

        g2d.fillRect(this.border.getWidth() + (x*this.pieceSize.width), -1*this.border.getHeight() + ((numPieces.height - y + 1)*this.pieceSize.height),
                this.pieceSize.width, this.pieceSize.height);
    }

    public boolean isSpaceOccupied(int x, int y) {
        for(Dimension occupiedLocation : this.occupiedLocations)
            if(occupiedLocation.width == x && occupiedLocation.height == y)
                return true;
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(this.boardColor);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        drawGridLines(g2d);

        this.occupiedLocations.clear();
    }

    private void drawGridLines(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);

        for(int x = this.border.getWidth(); x < this.size.width + this.border.getWidth(); x += this.pieceSize.width) {
            g2d.drawLine(x, this.border.getHeight(), x, this.size.height);
        }

        for(int y = this.border.getHeight(); y < this.size.height + this.border.getHeight(); y += this.pieceSize.height) {
            g2d.drawLine(this.border.getWidth(), y, this.size.width, y);
        }
    }

    public Color getBoardColor() {
        return boardColor;
    }

    public void setBoardColor(Color boardColor) {
        this.boardColor = boardColor;
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    public Dimension getPieceSize() {
        return pieceSize;
    }

    public Dimension getNumPieces() {
        return numPieces;
    }

    public Border getBorder() {
        return border;
    }

    public List<Dimension> getOccupiedLocations() {
        return occupiedLocations;
    }
}
