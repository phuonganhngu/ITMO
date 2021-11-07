package clientudp;

import classes.Human;
import com.sun.pisces.Surface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JComponent;

public class ClientUDP7 extends JPanel {

    DatagramChannel client = null;
    SocketAddress address = new InetSocketAddress((int) Math.random() * 9998);
    SocketAddress serverAdd = new InetSocketAddress("localhost", 9999);
    Set<Circle> circle = new HashSet<>();
    ExpFrame exp;

    public ClientUDP7() throws IOException {
        client = DatagramChannel.open();
        client.bind(address);
        //client.socket().setSoTimeout(5000);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                exp = new ExpFrame();
            }

        });
    }

    public static void main(String args[]) throws SocketException, UnknownHostException, IOException, ClassNotFoundException, SocketTimeoutException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        ClientUDP7 client1 = new ClientUDP7();
        client1.StartRequest();
    }

    public void makeConnection() throws IOException {
        client.connect(serverAdd);
        client.configureBlocking(true);
    }

    public void StartRequest() throws IOException, SocketTimeoutException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        makeConnection();
        sendRequest("Start");

        receiveResult();
    }

    public void sendRequest(String msg) throws IOException {
        //Datagram Socket Channel UDP
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAdd);
    }

    public void receiveResult() throws IOException, SocketTimeoutException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        //receive Object
        byte[] incomingData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(incomingData, incomingData.length);

        while (true) {
            try {
                client.socket().receive(packet);
                byte[] data = packet.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                Human h = (Human) is.readObject();
                System.out.println(h + " " + h.action);
                Circle c = new Circle(h.PosX, h.PosY, h.size,
                        (Color) Class.forName("java.awt.Color").getField(h.color.toLowerCase()).get(null), h.action, exp);
                if (h.action.equals("Add")) circle.add(c);
                if (h.action.equals("Reset")) circle.removeAll(circle);
                if (h.action.equals("Delete")){
                    Iterator<Circle> iter = circle.iterator();

                    while (iter.hasNext()) {
                        Circle ex = iter.next();

                        if (ex.equals(c)) {
                            System.out.println(ex);
                            iter.remove();
                        }
                }
                }
                exp.repaint();
            } catch (SocketTimeoutException e) {
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("CLASS HUMAN NOT FOUND");
                break;
            } catch (PortUnreachableException ex) {
                System.out.println("SERVER IS NOT AVAILABLE AT THE MOMENT, PLEASE TRY AGAIN LATER");
            }  catch (NullPointerException e) {}
            catch (StreamCorruptedException e) {}
        }

    }

    public class ExpPanel extends JPanel {

        JFrame parent;

        public ExpPanel(JFrame parent) {
            this.parent = parent;
        }
        
        @Override
        public String getToolTipText(MouseEvent event) {
            //System.out.println(event.getX()+"  "+this.getX()+ "  "+event.getY()+"  "+this.getY());
            Point p = new Point(event.getX(), event.getY());
            for (Circle c : circle) {
                String t = tooltipForCircle(p, c);
                if (t != null) {
                    return t;
                }
            }
            this.repaint();
            return "";
            //return super.getToolTipText(event);
        }

        public String tooltipForCircle(Point p, Circle c) {
            if (c.contains(p)) {
                return "(" + c.getX() + ", " + c.getY() + ") " + c.getRadius() + " " + c.getColor() + ")";

            }
            return null;
        }

        @Override
        public void paint(Graphics g) {
            
            super.paint(g); //To change body of generated methods, choose Tools | Templates.
                if (circle != null)        circle.forEach((c) -> { if (c.action.equals("Add"))
                c.draw(g);
            });
        }

    }

    public class ExpFrame extends JFrame {

        JComboBox comboBox = new JComboBox<>();
        ExpPanel panel = new ExpPanel(this);
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        private JSlider jSlider1 = new JSlider();

        public ExpFrame() {
            initComponents();

            this.setVisible(true);
            panel.setToolTipText("");
        }

        private void initComponents() {

            int min = 0;
            int max = 100;
            int space = 50;
            JSlider jSlider1 = new JSlider(JSlider.HORIZONTAL, min, max, space);
            jSlider1.setMinorTickSpacing(5);
            jSlider1.setMajorTickSpacing(20);
            jSlider1.setPaintTicks(true);
            jSlider1.setPaintLabels(true);

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"All", "Black", 
                    "Blue", "Yellow", "Gray", "Green", "Orange", "Pink", "Red", "White"}));

            GroupLayout panelLayout = new GroupLayout(panel);
            panel.setLayout(panelLayout);
            panel.setBackground(Color.WHITE);
            panelLayout.setHorizontalGroup(
                    panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGap(0, 745, Short.MAX_VALUE)
            );
            panelLayout.setVerticalGroup(
                    panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGap(0, 473, Short.MAX_VALUE)
            );

            button1.setText("Start");
            button2.setText("Stop");

            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            );

            pack();
            

            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedColor = (String) comboBox.getSelectedItem();
                    int value = jSlider1.getValue();
                    Color colorx;
                    try {
                        colorx = (Color) Class.forName("java.awt.Color").getField(selectedColor.toLowerCase()).get(null);
                    } catch (Exception ew) {
                        colorx = null; // Not defined
                    }

                    
                    //  System.out.println(colorx);
                    for (Circle c : circle) {
                        if (selectedColor.equals("All")) {
                            if ((c.getRadius() == value)) {
                                EventQueue.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new Dissappear(ExpFrame.this.panel,c, button2); 
                                        
                                    }
                                });
                            }
                            continue;
                        }
                        if ((colorx.getRGB() == c.getColor().getRGB()) && (c.getRadius() == value)) {
                            EventQueue.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                        new Dissappear(ExpFrame.this.panel,c, button2);
                                }
                            });
                        }
                    }
                }
            });
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
//            circle.forEach((c) -> {
//                c.draw(panel.getGraphics());
//            });
        }
    }

};
