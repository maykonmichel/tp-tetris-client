import javax.swing.*;
import java.awt.*;

public class Window {

	public static final int WIDTH = 307, HEIGHT = 600;
	private JFrame window;
	public Board board;
	public static JLabel label;
	private int x, y;

	public Window(int playerID)
	{
		if(playerID == 1)
		{
			x = 320;
			y = 50;
		}
		else
		{
			x = 650;
			y = 50;
		}

		if(playerID > 2 || playerID < 1)
		{
			System.out.println("-- JOGADOR INVÁLIDO");
			return;
		}

		window = new JFrame("Tetris Player X");
		window.setUndecorated(true);
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocation(x, y);

		label = new JLabel("Player "+playerID+" Pontuação: 0");
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
