import javax.swing.*;
import java.awt.*;

class Game extends JFrame {
    private static final int WIDTH = 315, HEIGHT = 650;
    static Player player;
    static Board opponent;
    static Status status = Status.AWAITING_OPPONENT;

    Game(String player, String opponent, int firstShape, int firstShapeOpponent) {
        super("Tetris game - " + player + " x " + opponent);

        System.out.println("Tetris game - " + player + " x " + opponent);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(3 * WIDTH, HEIGHT));
        setLayout(new FlowLayout());

        status = Status.RUNNING;

        JPanel pnlPlayer = new JPanel(new BorderLayout());
        pnlPlayer.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JLabel lblPlayer = new JLabel("Player " + player + " Pontuação: 0");
        lblPlayer.setFont(new Font("Arial", Font.BOLD, 18));
        lblPlayer.setForeground(Color.BLACK);
        pnlPlayer.add(lblPlayer, BorderLayout.NORTH);

        Game.player = new Player(lblPlayer, firstShape);
        pnlPlayer.add(Game.player);

        add(pnlPlayer);

        addKeyListener(Game.player);

        JPanel pnlOpponent = new JPanel(new BorderLayout());
        pnlOpponent.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JLabel lblOpponent = new JLabel("Player " + opponent + " Pontuação: 0");
        lblOpponent.setFont(new Font("Arial", Font.BOLD, 18));
        lblOpponent.setForeground(Color.BLACK);
        pnlOpponent.add(lblOpponent, BorderLayout.NORTH);

        Game.opponent = new Board(lblOpponent, firstShapeOpponent);
        pnlOpponent.add(Game.opponent);

        add(pnlOpponent);

        pack();

        setVisible(true);
    }

    enum Status {
        STOP,
        AWAITING_OPPONENT,
        RUNNING
    }
}
