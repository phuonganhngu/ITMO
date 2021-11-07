
package serverudp;

import classes.Human;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerUpdateThread implements Runnable {

    private Human h;
    private ArrayList<DatagramPacket> packetList;
    private DatagramSocket socket;

    public ServerUpdateThread(DatagramSocket socket, ArrayList<DatagramPacket> packetList, Human h) {
        this.packetList = packetList;
        this.h = h;
        this.socket = socket;
    }

    @Override
    public void run() {
        String s;
        InetAddress IPAddress;
        //System.out.println(packetList);
        System.out.println("update " + h);
        packetList.stream().forEach( p -> {
            try {
                //System.out.println("b " +p.getAddress() + " " + p.getPort());
                sendOb(h, p.getAddress(), p.getPort());
                //System.out.println(h);
            } catch (IOException ex) {
                Logger.getLogger(ServerUpdateThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }

    public void sendOb(Human ob, InetAddress IPAddress, int port) throws IOException {
        //System.out.println(ob.toString());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.reset();
        os.writeObject(ob);
        os.flush();
        byte[] data = outputStream.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
        socket.send(sendPacket);
        
    }
}
