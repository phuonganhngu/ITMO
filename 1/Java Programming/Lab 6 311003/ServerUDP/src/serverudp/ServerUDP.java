package serverudp;

import classes.Human;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

 

public class ServerUDP extends Thread implements Runnable{
    
    @Override
    public void run(){
    }
    
    public static void main(String[] args) throws IOException {
        
        DatagramSocket socket = new DatagramSocket(3000);
        System.out.println(InetAddress.getLocalHost().getHostName());

        
        Set<Human> human = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
        
        String fileName = "D:\\NetBeansProjects\\ServerUDP\\src\\serverudp\\HumanList.xml";
        
        try {XmlFile.read(fileName, human);}
            catch (FileNotFoundException ex) {
                System.out.println("Input file not found \n");
            }
        System.out.println(LocalDateTime.now());
        System.out.print("Server is online \n");
        human.stream().forEach((p) -> System.out.println(p + "\n"));
        
        while (true){
            human.stream().sorted();
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String s = new String(packet.getData());
            System.out.println("From client: " + s);
            
          
            InetAddress IPAddress = packet.getAddress();
            int port = packet.getPort();
            
            String msg = "";
            for (int i = 1; i<1024; i++){
                if (s.substring(0, i).endsWith("}")) {
                    s = s.substring(0, i);
                    break;
                }
            }
            //System.out.println(s + " " + s.length());

            if (s.startsWith("print")) {
                
                for (Human p : human) {
                    msg = msg + p.toString() + "\n";
                }
            }

            if (s.startsWith("add ")) {
                Human p = Human.jsonToObject(s.replaceFirst("add ", ""));
                human.add(p);
                msg = "Added target";
            }
            
            if (s.startsWith("delete ")) {
                Human p = Human.jsonToObject(s.replaceFirst("delete ", ""));
                
                human.stream().filter(h -> h.equal(p)).forEach(h -> System.out.println("Deleting " + h + "\n"));
                
                for (Human findP : human) {
                    if (p.compareTo(findP)<0) human.remove(findP);
                }
                
                msg = "Deleted target";
            }
            
            if (s.startsWith("smaller ")) {
                Human p = Human.jsonToObject(s.replaceFirst("smaller ", ""));
                               
                for (Human findP : human) {
                    if (p.getSize()>findP.getSize()) {
                       msg = msg + findP.toString() + "\n"; 
                    }
                    if (msg == "") msg ="none";
                }
            }
            
            if (s.startsWith("save")) {
                try {
                    XmlFile.write(fileName, human);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
                }
                msg = "Saved list";                
            }
                    
            if (s.startsWith("load")) {
                human = Collections.newSetFromMap(new ConcurrentHashMap<Human, Boolean>());
                try {XmlFile.read(fileName, human);}
                    catch (FileNotFoundException ex) {
                        System.out.println("Input file not found");
                }
                msg = "Loaded list";
            }
            
            System.out.println(msg);
            buf = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, IPAddress, port);
            
            socket.send(sendPacket);                 
        }
    
        }
    }
 