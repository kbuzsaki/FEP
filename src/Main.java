
import fep.controller.Command;
import static fep.controller.Command.*;
import fep.controller.Cursor;
import fep.controller.KeyMapper;
import fep.view.GameWindow;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main {
    static Command[] commands = { A, UP, UP, LEFT, LEFT, DOWN, LEFT, A};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final Cursor cursor = new Cursor();
        // eventually loaded as part of settings
        final KeyMapper keyMapper = new KeyMapper();
        
        KeyAdapter commandForwarder = new KeyAdapter() {
            private static final int minDelay = 70;
            long lastCommandTime = 0;
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (System.currentTimeMillis() > lastCommandTime + minDelay)
                {
                    cursor.handleCommand(keyMapper.getCommand(e));
                    lastCommandTime = System.currentTimeMillis();
                }
            }
        };
        
        GameWindow window = new GameWindow();
        window.addKeyListener(commandForwarder);
        window.start();
    }

}
