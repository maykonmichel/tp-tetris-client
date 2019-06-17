import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class Menu extends JFrame {
    private static final int WIDTH = 500, HEIGHT = 200;

    JLabel status;
    JTextField name;
    JButton connect;

    Menu() {
        super("Tetris game - menu");
        setLayout(new BorderLayout());

        status = new JLabel("Status");
        add(status, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new FlowLayout());
        {
            panel.add(new JLabel("Nome: "));

            name = new JTextField(20);
            panel.add(name);

            connect = new JButton("Conectar");

            connect.addActionListener(e -> {
                connect.setEnabled(false);
                name.setEnabled(false);
                status.setText("Aguardando oponente...");
                try {
                    new Connection(name.getText());
                    new Thread(new WaitOpponent()).start();
                } catch (IOException ex) {
                    name.setEnabled(true);
                    connect.setEnabled(true);
                    status.setText("Servidor não disponível.");
                }
//                setVisible(false);
            });
            panel.add(connect);
        }
        add(panel);

        pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width / 2 - WIDTH / 2,
                dimension.height / 2 - HEIGHT / 2,
                WIDTH, HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    class WaitOpponent implements Runnable {
        @Override
        public void run() {
            while(Game.status == Game.Status.AWAITING_OPPONENT) {
                try {
                    System.out.println("Awaiting opponent.");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            name.setEnabled(true);
            connect.setEnabled(true);
            status.setText("");
        }
    }
}
