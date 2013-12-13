/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.view;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Kyle
 */
public class GameWindow extends JFrame {
    
    public GameWindow() {
        super("FEP GameWindow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
    }
    
    public void start() {
        // maybe? dunno, some form of prevention of tiny-ness
        // size preference potentiall stored in savings?
        setMinimumSize(new Dimension(100, 100));
        setLocation(200, 200);
        
        pack();
        setVisible(true);
    }
    
}
