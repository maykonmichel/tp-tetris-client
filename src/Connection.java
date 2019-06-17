import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Connection implements Runnable {
    private final static String HOST = "localhost";
    private final static int PORT = 44455;

    private static Socket socket;
    private static Scanner scanner;
    private static PrintStream printStream;
    private static boolean running;

    Connection(String name) throws IOException {
        try {
            socket = new Socket(HOST, PORT);
            scanner = new Scanner(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(name);
            System.out.println("Player connected.");
            new Thread(this).start();
        } catch (IOException e) {
            System.out.println("Something went wrong connecting player: " + e.getMessage());
            throw e;
        }
    }

    static void triggerAction(int action) {
        printStream.println(action);
    }

    private void disconnect() {
        Game.status = Game.Status.STOP;
        running = false;
        scanner.close();
        printStream.close();
        try {
            socket.close();
            System.out.println("Player disconnected.");
        } catch (IOException e) {
            System.out.println("Something went wrong disconnecting player: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                int action = scanner.nextInt();
                if(action == Action.STOP.getValue()) {
                    Game.status = Game.Status.values()[scanner.nextInt()];
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                disconnect();
            }
        }
    }

    private enum Action {
        GAME_OVER(5),
        STOP(6),
        WAITING_OPPONENT(7);

        private int value;

        Action(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
