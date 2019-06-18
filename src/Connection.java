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

    static void getNextShapeIndex() {
        printStream.println(Action.GET_NEXT_SHAPE);
    }

    static void keyPressed(int direction) {
        printStream.println(Action.KEY_PRESSED);
        printStream.println(direction);
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
                String action = "";
                while(action.isEmpty()) action = scanner.nextLine();
                switch (action) {
                    case "NEXT_SHAPE":
                        Game.player.setNextShapeIndex(scanner.nextInt());
                        break;
                    case "OPPONENT_NEXT_SHAPE":
                        Game.opponent.setNextShapeIndex(scanner.nextInt());
                        break;
                    case "KEY_PRESSED":
                        switch (scanner.nextInt()) {
                            case 1:
                                Game.opponent.currentShape.setDeltaX(-1);
                                break;
                            case 2:
                                Game.opponent.currentShape.setDeltaX(1);
                                break;
                            case 3:
                                Game.opponent.currentShape.speedDown();
                                break;
                            case 4:
                                Game.opponent.currentShape.rotate();
                                break;
                            case 5:
                                Game.opponent.currentShape.normalSpeed();
                                break;
                        }
                        break;
                    case "STOP":
                        break;
                    case "OPPONENT_MATCHED":
                        new Game(name, scanner.nextLine(), scanner.nextInt(), scanner.nextInt());
                        break;
                    default:
                        System.out.println(action + " not recognized");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    private enum Action {
        GET_NEXT_SHAPE,
        KEY_PRESSED
    }
}
