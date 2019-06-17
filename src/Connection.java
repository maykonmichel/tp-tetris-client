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

    private String name;

    Connection(String name) throws IOException {
        try {
            this.name = name;
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
                Action action = Action.fromInteger(scanner.nextInt());
                switch (action) {
                    case KEY_PRESSED:
                    case STOP:
                    case OPPONENT_MATCHED:
                        new Game(name, scanner.nextLine());
                        break;
                }
            } catch (Exception e) {
//                disconnect();
            }
        }
    }

    private enum Action {
        KEY_PRESSED,
        STOP,
        OPPONENT_MATCHED;

        public static Action fromInteger(int value) {
            switch(value) {
                default:
                case 0:
                    return KEY_PRESSED;
                case 1:
                    return STOP;
                case 2:
                    return OPPONENT_MATCHED;
            }
        }
    }
}
