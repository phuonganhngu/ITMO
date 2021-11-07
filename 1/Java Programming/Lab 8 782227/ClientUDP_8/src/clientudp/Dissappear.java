package clientudp;

import com.sun.pisces.Surface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

class Dissappear implements Runnable {

        
        private Circle c;
        private JPanel parent;
        private boolean ok;
        public Dissappear(JPanel parent,Circle c, JButton button2) {
            
            this.parent = parent;
            this.c = c;
            this.ok = true;
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ok = false;
                }
            });
            new Thread(this).start();
        }

        @Override
        public void run() {
            //System.out.println(c + " " + c.alpha_ellipse + " " + ok);
            while ((c.alpha_ellipse >= 0) && (ok)){
                
                parent.repaint();
                c.alpha_ellipse = c.alpha_ellipse-0.01f;
                //System.out.println(c + "  a  " + c.alpha_ellipse + " " + ok);
                if (c.alpha_ellipse <= 0) {
                    while ((c.alpha_ellipse <= 1f) && (ok)) {
                        parent.repaint();
                        c.alpha_ellipse += 0.01f;
                        if (c.alpha_ellipse > 1f) {
                            c.alpha_ellipse = 1f;
                            ok = false;
                        }
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Surface.class.getName()).log(Level.SEVERE,
                            null, ex);
                        }
                    }
                }
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Surface.class.getName()).log(Level.SEVERE,
                            null, ex);
                }  
            }
            
        }
    }
