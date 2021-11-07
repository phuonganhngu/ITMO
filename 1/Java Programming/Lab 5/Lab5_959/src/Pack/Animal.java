package Pack;

public class Animal {
    public String name;
    public int age;
    public int volum;
    public int speed;


    public Animal(String name , int age, int volum, int speed){
        this.name = name;
        this.age = age;
        this.volum = volum;
        this.speed = speed;
        
   
    }
    public String getName(){
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSpeed() {
        return speed;
    }

    public int getVolum() {
        return volum;
    }

    public void setVolum(int volum) {
        this.volum = volum;
    }  
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
