import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel {
    private final int blockSize = 30;
    private final int boardWidth = 10, boardHeight = 19;
    Shape currentShape;
    int pontuacao = 0;
    boolean gameOver = false;
    boolean trigger = false;
    Shape[] shapes = new Shape[7];
    private BufferedImage blocks;
    private int[][] board = new int[boardHeight][boardWidth];
    private Timer timer;
    private int nextShape;

    Board(JLabel label, int firstShape) {
        try {
            blocks = ImageIO.read(new File("tiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int FPS = 60;
        int delay = 1000 / FPS;
        timer = new Timer(delay, e -> {
            update(label);
            repaint();
        });

        timer.start();

        // shapes

        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1, 1} // IShape
        }, this, 1);

        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][]{
                {1, 1, 0},
                {0, 1, 1}   // ZShape
        }, this, 2);

        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), new int[][]{
                {0, 1, 1},
                {1, 1, 0}   // S-Shape
        }, this, 3);

        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {0, 0, 1}   // J-Shape
        }, this, 4);

        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {1, 0, 0}   // L-Shape
        }, this, 5);

        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {0, 1, 0}   // T-Shape
        }, this, 6);

        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), new int[][]{
                {1, 1},
                {1, 1}   // O-Shape
        }, this, 7);

        nextShape = firstShape;

        setNextShape(label);
    }

    private void update(JLabel label) {
        if (!gameOver) {
            currentShape.update(label);
        } else
            timer.stop();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        currentShape.render(g);

        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                if (board[row][col] != 0)
                    g.drawImage(blocks.getSubimage((board[row][col] - 1) * blockSize, 0, blockSize, blockSize),
                            col * blockSize, row * blockSize, null);


        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }
        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }

    }

    private void getNextShapeIndex() {
        Connection.getNextShapeIndex();
    }

    void setNextShapeIndex(int index) {
        nextShape = index;
    }

    void setNextShape(JLabel label) {
        label.setText("Pontuação: " + pontuacao);
        pontuacao += 5;

        currentShape = new Shape(shapes[nextShape].getBlock(), shapes[nextShape].getCoords(),
                this, shapes[nextShape].getColor());

        for (int row = 0; row < currentShape.getCoords().length; row++)
            for (int col = 0; col < currentShape.getCoords()[row].length; col++)
                if (currentShape.getCoords()[row][col] != 0)
                    if (board[row][col + 3] != 0)
                        gameOver = true;

        getNextShapeIndex();
    }


    int getBlockSize() {
        return blockSize;
    }

    int[][] getBoard() {
        return board;
    }
}
