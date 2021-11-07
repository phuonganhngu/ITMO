package Pack;

import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.Scanner;

public class Lab4_959 {

    public static void main(String[] args) {
        
        File text = new File("C:/Users/phany/Desktop/ITMO/Первый курс- 2-ый семестр/Lập trình/WorkMan.xml");
        
        //Nick
        Position pstNick = new Position(0 , 5);
        UnfamiliarMan Nick = new UnfamiliarMan("Nick", 23, pstNick,5,25);
        
       
        try{
            Nick.setAge(-1);
        }catch (AgeException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        try{
            Nick.setSpeed(-10);
        }catch (SpeedException e){
            System.out.println("Error: " + e.getMessage());
        }
   
        try{
            Nick.decSpeed(0);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Ignore...");
 
        }
        
        House houseN = new House(HouseType.BEAUTIFULL);
        City city3 = new City(Nick, houseN);
        System.out.println(city3);
        
        Nick.skill();
        Nick.run();
        Nick.punch();
        Nick.jump();
        Nick.Speak();
        
        //Phil
        Position pstPhil = new Position(0 , 5);
        WorkMan Phil = new WorkMan("Phil", 35, pstPhil, 5, 15, "BUSINESSMAN", true);
        House houseP = new House(HouseType.BIG);
        City city1 = new City(Phil, houseP);
        System.out.println(city1);
        Phil.skill();
        Phil.run();
        Phil.punch();
        Phil.jump();
        Phil.WorkAs();
        Phil.Speak();

        //Carnation
        Position pstCarnation = new Position(0 , 14);
        WorkMan Carnation = new WorkMan("Carnation", 27, pstCarnation, 5, 15, "BAKER", false);
        House houseCar = new House(HouseType.SMALL);
        City city2 = new City(Carnation, houseCar);
        System.out.println(city2);
        Carnation.skill();
        Carnation.run();
        Carnation.punch();
        Carnation.jump();
        Carnation.WorkAs();
        Carnation.Speak();

        //equal() and hashCode()
        Check check = new Check();
        check.checkLocation(Nick, Carnation);
        check.checkLocation(Nick, Phil);

        
        Position pstDraco = new Position(0 , 14);
        Dragon Draco = new Dragon("Draco", 27, pstCarnation, 5, 15, 300, "Green City");
        Draco.location();
        Draco.skill();
        Draco.fly();
        Draco.fireBreath();
        
        Group goodPeople = new Group();
        Group badPeople = new Group();
        
        goodPeople.add(Nick);
        goodPeople.add(Phil);
        
        badPeople.add(Carnation);      
        
        
        //Reflection
         Method[] methods = Human.class.getMethods();
         for (Method method : methods) {
           System.out.println("Method " + method.getName());
         }
        
        try {
            Class c = Nick.getClass();
            Class cSuper = c.getSuperclass();
            
            Method method = c.getMethod("skill");
            method.invoke(Nick);
            
            Method methodSuper = cSuper.getMethod("decSpeed", int.class);
            methodSuper.invoke(Nick, 4);
            
            Field field = c.getField("speed");
            System.out.println("speed Type: " + field.getType());
            field.set(Nick, 100);
            Nick.run();
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(Lab4_959.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
