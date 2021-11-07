package serverudp;

import classes.Human;
import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;
import javax.swing.*;
import myPack.Humandb;

public class ServerUDP7_exp extends Thread {

    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame login = new LoginFrame();
                login.loginBut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        if ((login.field2.getText().equals("password"))) {
                            login.login.dispose();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        createList();
                                    } catch (IOException ex) {
                                        Logger.getLogger(ServerUDP7_exp.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }).start();

                        }
                    }
                });
            }
        });
    }

    static void createList() throws SocketException, IOException {
        DatagramSocket socket = new DatagramSocket(9999);
        Set<Human> human = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
        ArrayList<DatagramPacket> packetList = new ArrayList<DatagramPacket>();

        HumanORM db = new HumanORM();
        List <Humandb> humanDB = db.read();
        humanDB.stream().forEach(h -> human.add(new Human(h.getHumanid(), h.getName(),
                h.getColor(), h.getSize(), h.getPosx(),h.getPosy(), h.getTime(), h.getCreated())));
        System.out.println(human);
        System.out.println(LocalDateTime.now());
        System.out.print("SERVER IS ONLINE \n");

        JTreeList treeList = new JTreeList(human, packetList, socket);
        treeList.creatFrame();
        human.stream().forEach((p) -> {
            System.out.println(p);
            p.setAction("Add");
            treeList.addNode(p);
        });

        while (true) {
            human.stream().sorted();
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            packetList.add(packet);
            System.out.println("a " + packet.getAddress() + " " + packet.getPort());
            //System.out.println(packetList);

            ServerThread serThread = new ServerThread(socket, packet, human);
            new Thread(serThread).start();
        }
    }
}




