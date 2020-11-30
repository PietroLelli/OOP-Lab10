package it.unibo.oop.lab.reactivegui02;

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

public class ConcurrentGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JLabel display = new JLabel();
    private final JButton btnUp = new JButton("up");
    private final JButton btnDown = new JButton("down");
    private final JButton btnStop = new JButton("stop");
    
    public ConcurrentGUI() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        panel.add(display);
        panel.add(btnUp);
        panel.add(btnDown);
        panel.add(btnStop);
        this.getContentPane().add(panel);
        this.setVisible(true);
        final Agent agent = new Agent();
        new Thread(agent).start();
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                // Agent should be final
                agent.stopCounting();
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
            }
        });
        btnDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent.stat = status.DOWN;
            }
        });
        btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent.stat = status.UP;
            }
        });
        
    }

    public JButton getBtnUp() {
        return btnUp;
    }
    public JButton getBtnDown() {
        return btnDown;
    }
    public JButton getBtnStop() {
        return btnStop;
    }

    enum status {
        UP, DOWN;
    }
    private class Agent implements Runnable {
        private volatile boolean stop;
        private volatile int counter;
        private status stat = status.UP;

        @Override
        public void run() {
            while (!this.stop) {
                try {
                    SwingUtilities.invokeAndWait(() -> ConcurrentGUI.this.display.setText(Integer.toString(Agent.this.counter)));
                    if (this.stat == status.UP) {
                        this.counter++;
                    }
                        
                    else if (this.stat == status.DOWN) {
                        this.counter--;
                    }
                    Thread.sleep(100);

                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         * External command to stop counting.
         */
        public void stopCounting() {
            this.stop = true;
        }
    }
}