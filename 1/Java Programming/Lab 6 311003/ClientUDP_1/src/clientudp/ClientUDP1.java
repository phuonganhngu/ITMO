package clientudp;

import classes.Human;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class ClientUDP1 {
    DatagramChannel client = null;
    SocketAddress address = new InetSocketAddress((int) Math.random()*9998);
    SocketAddress serverAdd = new InetSocketAddress("localhost", 9999);
 
    public ClientUDP1() throws IOException  {
        client = DatagramChannel.open();
        client.bind(address);
        client.socket().setSoTimeout(5000);
    }
    
    public static void main(String args[]) throws SocketException, UnknownHostException, IOException, ClassNotFoundException{    
        ClientUDP1 client1 = new ClientUDP1();
        client1.StartRequest();
    }
    
    public void makeConnection() throws IOException {
        client.connect(serverAdd);
        client.configureBlocking(true);
    }
    
    public void StartRequest() throws IOException, SocketTimeoutException, ClassNotFoundException{
        makeConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println("HELLO, PLEASE MAKE A REQUEST!");
        String input;
        while (true){ 
            input = sc.nextLine();
            if (input.equals("exit")) {
                client.close();
                System.out.println("CONNECTION CLOSED");
                break;
            }
            sendRequest(input);
            receiveResult();
        }
    }
 
    public void sendRequest(String msg) throws IOException {
        //Datagram Socket Channel UDP
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAdd);
    }
    
    public void receiveResult() throws IOException, SocketTimeoutException, ClassNotFoundException{
        //receive msg
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        try{
            client.receive(buffer);
            buffer.flip();
            int limitBuf = buffer.limit();
            byte[] byteBuf = new byte[limitBuf];
            buffer.get(byteBuf, 0, limitBuf);
            String msg = new String(byteBuf);
            System.out.println(msg);
        } catch (PortUnreachableException ex){
            System.out.println("SERVER IS NOT AVAILABLE AT THE MOMENT, PLEASE TRY AGAIN LATER");
        }
        
        //receive Object
        byte[] incomingData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(incomingData, incomingData.length);
        while (true){
            try {
                client.socket().receive(packet);
                byte[] data = packet.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                Human h = (Human) is.readObject();
                System.out.println(h);
            } catch (SocketTimeoutException e) {
                break;
            }
            catch (ClassNotFoundException e) {
                System.out.println("CLASS HUMAN NOT FOUND");
                break;
            }
        }    
        
    }
 
}
