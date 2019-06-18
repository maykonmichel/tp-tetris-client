import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Board implements KeyListener {
    Player(JLabel label, int firstShape) {
        super(label, firstShape);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!trigger) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                currentShape.setDeltaX(-1);
                Connection.keyPressed(1);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                currentShape.setDeltaX(1);
                Connection.keyPressed(2);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                currentShape.speedDown();
                Connection.keyPressed(3);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                currentShape.rotate();
                Connection.keyPressed(4);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
                System.out.println("-- ENVIA PRO SERVIDOR O PODER");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.normalSpeed();
            Connection.keyPressed(5);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
