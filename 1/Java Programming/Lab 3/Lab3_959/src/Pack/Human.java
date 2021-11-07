package Pack;

public abstract class Human extends Animal{
    public Human(String name, int age, Position position, int volum, int speed) {
        super(name, age, position, volum, speed);
    }
    
    abstract void skill();
    abstract void run();
    abstract void jump();
    abstract void punch();

    @Override
    public String toString() {
        return "*" + name + " " + age + " YEARS OLD ";
    }
   
}


