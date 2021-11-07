package clientudp;

import java.io.*;
import java.net.*;
import java.util.*;


public class ClientUDP {
      private DatagramSocket socket;  
 
    public ClientUDP() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
    }
    
    public static void main(String args[]) throws SocketException, UnknownHostException, IOException{
        ClientUDP client1 = new ClientUDP();
        client1.StartRequest();
    }
    
    public void StartRequest() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Please make a request!");
        String input;
        while (true){
            input = sc.nextLine();
            if (input.equals("exit")) {
                socket.close();
                break;
            }
            sendRequest(input);
        }
    }
 
    public void sendRequest(String msg) throws IOException {
        InetAddress add = InetAddress.getByName("localhost");
        byte[] buf = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        buf = msg.getBytes();
        DatagramPacket packet  = new DatagramPacket(buf, buf.length, add, 3000);
        socket.send(packet);
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String received = new String(receivePacket.getData());
        System.out.println(received);
    }
 
    public void close() {
        socket.close();
    }
}
