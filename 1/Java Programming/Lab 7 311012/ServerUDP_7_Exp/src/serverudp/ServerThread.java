package serverudp;

import classes.Human;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerThread implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private Set<Human> human;
    private String fileName;

    public ServerThread(DatagramSocket socket, DatagramPacket packet, Set<Human> human, String fileName) {
        this.socket = socket;
        this.packet = packet;
        this.human = human;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        String s = new String(packet.getData());

        InetAddress IPAddress = packet.getAddress();
        int port = packet.getPort();
        System.out.print("CLIENT #" + port + ": " + s + "\n");

        human.stream().forEach(h -> {
            try {
                sendOb(h, IPAddress, port);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void sendOb(Human ob, InetAddress IPAddress, int port) throws IOException {
        System.out.println(ob.toString());
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
