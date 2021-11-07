package Pack;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;

public class WorkMan extends Human implements WorkSkill, SpeakSkill, Comparable<WorkMan>{
        String job;
        boolean love;
    public WorkMan(String name, int age, int volum, int speed, String job, boolean love) {
        super(name, age, volum, speed);
        this.job = job;
        this.love = love;
    }
    public String getJob(){
        return job;
    }
       
    public boolean getLove(){
        return love;
    }
    public void setJob(String job){
        this.job = job;
    }
    
    public void setLove(boolean love){
        this.love = love;
    }

    @Override
    public void skill() {
        System.out.println("SKILL: Punch, Run, Jump");
    }
    
    @Override
    public void punch(){
        System.out.println("PUNCH WITH " + speed  + "N");
    }

    @Override
    public void jump() {
        System.out.println("JUMP TO " + speed*0.4 + "M");
    }

    @Override
    public void run() {
        System.out.println("RUN WITH THE SPEED OF " + speed*0.8 + "KM/H" );
    }
    
    @Override
    public void WorkAs(){
        System.out.println(name + " IS WORKING AS " + job);
    }
    
    @Override
    public void Speak(){
        if (love)  System.out.println(name + " LOVES WORKING AS " + job); else
            System.out.println(name + " HATES WORKING AS " + job);
    }
    @Override
    public String toString() {
        return name + " " + age + " " + volum + " " + speed + " " + job + " " + love;
    } 
    @Override
    public int compareTo( WorkMan w){
        return (this.getName()+this.getAge()).compareTo(w.getName()+w.getAge());
    }
    public static WorkMan jsonToObject(String s) throws AgeException{
        WorkMan w = new WorkMan("", 0, 0, 0, "", false);
        try {
         // 1. Create a JSONParser
            JSONParser jsP = new JSONParser();
            // 2. Parser string JSON into a JSONObject
            JSONObject jsO = (JSONObject) jsP.parse(s);
            // 3. Get values from jsonObject through Key
             
            String name = (String) jsO.get("name");
            if (name != null) w.setName(name);
            
            try{
            int age = Integer.parseInt((String) jsO.get("age"));
            try {w.setAge(age);
            } catch (AgeException ex){
                System.out.println("Age exception");
            }
            } catch (NumberFormatException ex) {}
            
            try{
            int volum = Integer.parseInt((String) jsO.get("volum"));
            w.setVolum(volum);
            } catch (NumberFormatException ex) {}
            
            try{
            int speed = Integer.parseInt((String) jsO.get("speed"));
            w.setSpeed(speed);
            } catch (NumberFormatException ex) {}
            
            String job = (String) jsO.get("job");
            if (job != null) w.setJob(job);
            
            String l = (String) jsO.get("love");
            if (l != null) {
                boolean love;
                love = ("true".equals(l));
                w.setLove(love);
            }
            System.out.println(w.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    return w;    
    }
}
