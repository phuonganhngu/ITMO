package serverudp;

import classes.Human;
import java.io.*;
import java.util.*;

public class XmlFile {
    public static void read(String fileName, Set<Human> human)throws FileNotFoundException{
        File text = new File(fileName);
        String s = "";
        try (Scanner scanner = new Scanner(text)) {
            s = scanner.nextLine();
            while(scanner.hasNext()){
                s = scanner.nextLine();
                Human p = new Human(0, SplitString(scanner.next()),
                        SplitString(scanner.next()),
                        SplitNum(scanner.next()), 
                        SplitNum(scanner.next()),
                        SplitNum(scanner.next()),
                        SplitString(scanner.next()));
                human.add(p);
                s = scanner.nextLine();
                s = scanner.nextLine();
            }
            scanner.close();
        }
    }
    public static String SplitString(String s){
        while (false==s.startsWith(">")){
            s = s.substring(1,s.length()-1);
        }
        s = s.substring(1,s.length()-2);
        return s;
    }
    
    public static int SplitNum(String s){
        return Integer.parseInt(SplitString(s));
    }
    
    public static void write(String fileName, Set<Human> human)throws FileNotFoundException, UnsupportedEncodingException, IOException{
        File text = new File(fileName);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ "\n");
            for (Human w : human){
                writer.write("<Human>"+"\n");
                writer.write("<name>"+w.getName()+"</name>"+"\n");
                writer.write("<color>"+w.getColor()+"</color>"+"\n");
                writer.write("<size>"+w.getSize()+"</size>"+"\n");
                writer.write("<PosX>"+w.getPosX()+"</PosX>"+"\n");
                writer.write("<PosY>"+w.getPosY()+"</PosY>"+"\n");
                writer.write("<time>"+w.getTime()+"</time>"+"\n");
                writer.write("</Human>"+"\n");
            }
            writer.close();
        }
    }
}
