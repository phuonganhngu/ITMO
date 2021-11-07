package Pack;

import java.io.*;
import java.util.*;

public class XmlFile {
    public static void read(String fileName, HashSet<WorkMan> workMan)throws FileNotFoundException{
        File text = new File(fileName);
        String s = "";
        try (Scanner scanner = new Scanner(text)) {
            s = scanner.nextLine();
            while(scanner.hasNext()){
                s = scanner.nextLine();
                WorkMan W = new WorkMan(SplitString(scanner.next()), 
                        SplitNum(scanner.next()), 
                        SplitNum(scanner.next()),
                        SplitNum(scanner.next()), 
                        SplitString(scanner.next()), 
                        SplitBoo(scanner.next()));
                workMan.add(W);
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
    
    public static boolean SplitBoo(String s){
        return ("true" == SplitString(s));
    }
    
    public static void write(String fileName, HashSet<WorkMan> workMan)throws FileNotFoundException, UnsupportedEncodingException, IOException{
        File text = new File(fileName);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ "\n");
            for (WorkMan w : workMan){
                writer.write("<WorkMan>"+"\n");
                writer.write("<name>"+w.getName()+"</name>"+"\n");
                writer.write("<age>"+w.getAge()+"</age>"+"\n");
                writer.write("<volum>"+w.getVolum()+"</volum>"+"\n");
                writer.write("<speed>"+w.getSpeed()+"</speed>"+"\n");
                writer.write("<job>"+w.getJob()+"</job>"+"\n");
                writer.write("<love>"+w.getLove()+"</love>"+"\n");
                writer.write("</WorkMan>"+"\n");

            }
            writer.close();
        }
    }
}
