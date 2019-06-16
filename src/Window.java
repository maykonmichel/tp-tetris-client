import javax.swing.*;
import java.awt.*;

public class Window {
	
	public static final int WIDTH = 307, HEIGHT = 622;
	private JFrame window;
	private Board board;
	public static JLabel label;

	public Window()
	{
		window = new JFrame("Tetris Player X");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);

		label = new JLabel("Pontuação: 0");
		label.setSize(10, 10);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setForeground(Color.BLACK);
		window.add(label, BorderLayout.NORTH);

		board = new Board(label);
		window.add(board);
		window.addKeyListener(board);

		window.setVisible(true);
	}

}
