package Pack;

public class Animal {
    final String name;
    public int age;
    public int volum;
    public int speed;
    public Position position;

    public Animal(String name , int age, Position position, int volum, int speed){
        this.name = name;
        this.age = age;
        this.position = position;
        this.volum = volum;
        this.speed = speed;
        
   
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

    public Position getPosition() {
        return position;
    }

    public void setVolum(int volum) {
        this.volum = volum;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
}
