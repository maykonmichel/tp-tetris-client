import javax.swing.*;
import java.awt.*;

class Game {
    private static final int WIDTH = 307, HEIGHT = 600;
    Board board;
    private int x, y;
    static Status status = Status.AWAITING_OPPONENT;


    Game(String player, String opponent) {
        status = Status.RUNNING;
        System.out.println(player + " x " + opponent);
        JFrame window = new JFrame("Tetris game - " + player + " x " + opponent);
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocation(x, y);

        JLabel label = new JLabel("Player " + player + " Pontuação: 0");
        label.setSize(10, 10);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
        window.add(label, BorderLayout.NORTH);

        board = new Board(label);
        window.add(board);

        window.addKeyListener(board);
        window.setVisible(true);
    }

    enum Status {
        STOP,
        AWAITING_OPPONENT,
        RUNNING,
        WON,
        LOST
    }
}
