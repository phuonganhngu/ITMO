package Pack;

import java.io.*;
import java.util.*;

public class Lab5 {
    public static void main(String[] args) throws FileNotFoundException, AgeException, IOException {
        
        UnfamiliarMan Nick = new UnfamiliarMan("Nick", 23,5,25);
        HashSet<WorkMan> workMan = new HashSet<WorkMan>();
        String fileName = "D:\\NetBeansProjects\\Lab5_959\\src\\Pack\\WorkMan.xml";
        
        try {XmlFile.read(fileName, workMan);}
            catch (FileNotFoundException ex) {
                System.out.println("File input not found");
            }
        Scanner scanner = new Scanner(System.in);
        
        while (true){
            String s = scanner.nextLine();
            
            if (s.equals("exit")) {
                break;
            }

            if (s.equals("print")) {
                for (WorkMan w : workMan) {
                    System.out.println(w.toString());
                }
            }

            if (s.startsWith("remove_lower ")) {
                WorkMan w = WorkMan.jsonToObject(s.replaceFirst("remove_lower ", ""));
            
                for (Iterator<WorkMan> i = workMan.iterator(); i.hasNext();)  {
                    WorkMan wm = i.next();
                    if (w.compareTo(wm)>0){
                        i.remove();
                    }
                }
            }
            if (s.startsWith("add_if_max ")) {
                WorkMan w = WorkMan.jsonToObject(s.replaceFirst("add_if_max ", ""));
                boolean ok = true;
                for (Iterator<WorkMan> i = workMan.iterator(); i.hasNext();)  {
                    WorkMan wm = i.next();
                    if (w.compareTo(wm)<0) ok = false;
                }
                if (ok) workMan.add(w);
            }
            if (s.startsWith("save")) 
                    XmlFile.write(fileName, workMan);
            
            if (s.startsWith("load")) {
                workMan = new HashSet<WorkMan>();
        
                try {XmlFile.read(fileName, workMan);}
                    catch (FileNotFoundException ex) {
                        System.out.println("File input not found");
                }
            }
        }   
    }
}
