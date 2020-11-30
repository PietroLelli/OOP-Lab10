package it.unibo.oop.lab.reactivegui03;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;
import it.unibo.oop.lab.reactivegui02.ConcurrentGUI.Agent;
import it.unibo.oop.lab.reactivegui02.ConcurrentGUI.status;

public class AnotherConcurrentGUI extends ConcurrentGUI {

    public AnotherConcurrentGUI() {
        super();
    }
    private class Agent2 implements Runnable {
        private volatile int counter2;
        private volatile boolean stop2;
        
        @Override
        public void run() {
            while (!this.stop2) {
                try {
                    Thread.sleep(100);
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
        
    }
}
