import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends JFrame {
    private ClientSideConnection csc;
    private int playerId;
    public static Board player1, player2;

    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    private class ClientSideConnection {
        private Socket socket;
        private int port = 44455;
        private Scanner sc;
        private PrintStream ps;

        public ClientSideConnection() {
            System.out.println("---CLIENT---");
            try {
                socket = new Socket("localhost", port);
                sc = new Scanner(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
                playerId = sc.nextInt();
                System.out.println("ID DO JOGADOR: " + playerId);

                new Thread(t).start();
            } catch (IOException e) {
                System.out.println("Erro de IO: " + e);
            }
        }

        private Runnable t = new Runnable()
        {
            @Override
            public void run() {
                try
                {
                    player1 = new Window(1).board;
                    player2 = new Window(2).board;

                    boolean jogo = true;

                    while (jogo)
                    {
                        int escolha = sc.nextInt();

                        switch (escolha)
                        {
                            case 1: //ESQUERDA
                                if(playerId == 1)
                                    player2.currentShape.setDeltaX(-1);
                                else if(playerId == 2)
                                    player1.currentShape.setDeltaX(-1);
                                break;

                            case 2: //DIREITA
                                if(playerId == 1)
                                    player2.currentShape.setDeltaX(1);
                                else if(playerId == 2)
                                    player1.currentShape.setDeltaX(1);
                                break;

                            case 3: //CIMA
                                if(playerId == 1)
                                    player2.currentShape.rotate();
                                else if(playerId == 2)
                                    player1.currentShape.rotate();
                                break;

                            case 4: // BAIXO
                                if(playerId == 1)
                                    player2.currentShape.speedDown();
                                else if(playerId == 2)
                                    player1.currentShape.speedDown();
                                break;

                            case 5: //ESPACO
                                try {
                                    Thread t = new Thread();
                                    if (playerId == 1) {
                                        player2.trigger = true;
                                        t.sleep(10000);
                                        player2.trigger = false;
                                    } else if (playerId == 2) {
                                        player1.trigger = true;
                                        t.sleep(10000);
                                        player1.trigger = false;
                                    }
                                }catch (Exception ex){
                                    System.out.println("-- ERRO NO SERVIDOR");
                                    ex.printStackTrace();
                                }
                                break;

                            case 6: //GAME OVER
                                int winner = playerId;
                                if(playerId == 1)
                                    player2.gameOver = true;
                                else if(playerId == 2)
                                    player1.gameOver = true;
                                JOptionPane.showMessageDialog(null,
                                        "O Jogador "+winner+" ganhou o jogo!");
                                break;

                            case 7: //DECIDIRAM PARAR DE JOGAR
                                jogo = false;
                                break;

                            case 8: //VOLTA NORMAL SPEED
                                if(playerId == 1)
                                    player2.currentShape.normalSpeed();
                                else if(playerId == 2)
                                    player1.currentShape.normalSpeed();
                                break;
                        }
                    }

                    ps.close();
                    sc.close();
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Erro de IO: " + ex);
                }
            }
        };

    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connectToServer();
    }
}