package serverudp;

import classes.Human;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;

public class ServerUDP1 extends Thread{
    
    public static void main(String[] args) throws IOException {
        
        DatagramSocket socket = new DatagramSocket(9999);
        //System.out.println(InetAddress.getLocalHost().getHostName());

        Set<Human> human = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
        
        String fileName = "D:\\NetBeansProjects\\ServerUDP\\src\\serverudp\\HumanList.xml";
        
        try {XmlFile.read(fileName, human);}
            catch (FileNotFoundException ex) {
                System.out.println("FILE INPUT NOT FOUND \n");
            }
        
        System.out.println(LocalDateTime.now());
        System.out.print("SERVER IS ONLINE \n");
        human.stream().forEach((p) -> System.out.println(p));
        
        while (true){
            human.stream().sorted();
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
        
            ServerThread serThread = new ServerThread(socket, packet, human, fileName);
            new Thread(serThread).start();   
        }
    }
}
 
class ServerThread implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private Set<Human> human;
    private String fileName;
    
    public ServerThread( DatagramSocket socket, DatagramPacket packet, Set<Human> human, String fileName){
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
        System.out.print("FROM CLIENT #" + port + ": " + s +"\n");
        String msg = "";
        for (int i = 1; i<1024; i++){
            if (s.substring(0, i).endsWith("}")) {
                s = s.substring(0, i);
                break;
            }
        }
        
        Set<Human> list = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
       
        if (s.startsWith("print")) {
            list = human;
            msg = "HUMAN LIST< NAME, SIZE, POSX, POSY, TIME>:";
        }
        
        if (s.startsWith("add ")) {
            Human p = Human.jsonToObject(s.replaceFirst("add ", ""));
            human.add(p);
            msg = "ADDED TARGET";
        }
        
        if (s.startsWith("delete ")) {
            Human p = Human.jsonToObject(s.replaceFirst("delete ", ""));
            final Set<Human> delList = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
            human.stream().filter(h -> h.equal(p)).forEach((Human h) -> 
                    {System.out.println("Deleting " + h);
                    delList.add(h);
                     human.remove(h);
                    });
            list = delList;
            if (list.isEmpty()){
                msg = "TARGET IS NOT IN THE LIST";
            } else msg = "DELETED LIST< NAME, SIZE, POSX, POSY, TIME>:";
        }
        
        if (s.startsWith("smaller ")) {
            Human p = Human.jsonToObject(s.replaceFirst("smaller ", ""));
            human.stream().filter((findP) -> (p.getSize()>findP.getSize())).forEachOrdered(list::add);
            if (list.isEmpty()) {
                msg ="NO ONE IS SMALLER THAN " + p.getSize() ;
            } else msg = "RESULT LIST< NAME, SIZE, POSX, POSY, TIME>:";
        }
        
        if (s.startsWith("save")) {
            try {
                XmlFile.write(fileName, human);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ServerUDP1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerUDP1.class.getName()).log(Level.SEVERE, null, ex);                
            }
            msg = "SAVED LIST";
        }
        
        if (s.startsWith("load")) {
            human = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
            try {XmlFile.read(fileName, human);}
            catch (FileNotFoundException ex) {
                System.out.println("INPUT FILE NOT FOUND");
            }
            msg = "LOADED NEW LIST";
        }
        
        try {
            sendStr(msg, IPAddress, port);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.stream().forEach(h -> {
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
        os.writeObject(ob);
        os.flush();
        byte[] data = outputStream.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
        socket.send(sendPacket);
    }
      
    public void sendStr(String msg, InetAddress IPAddress, int port) throws IOException {
        byte[] buf = new byte[1024];
        buf = msg.getBytes();
        System.out.println(msg);
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, IPAddress, port);
        socket.send(sendPacket);
    }
}

