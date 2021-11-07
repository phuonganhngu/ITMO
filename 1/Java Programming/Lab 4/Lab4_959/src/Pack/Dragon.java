package Pack;

public class Dragon extends Animal{
    int degree;
    String city;
    public Dragon(String name, int age, Position position, int volum, int speed, int degree, String city) {
        super(name, age, position, volum, speed);
        this.degree = degree;
        this.city = city;
    }
    
    public void location(){
        System.out.println("*" + name + " IS AT " + city);
    }
    public void skill() {
        System.out.println("SKILL: Fly, Firebreath");
    }
    
    public void fly(){
        System.out.println("FLY WITH " + speed  + "KM/H");
    }

    public void fireBreath() {
        System.out.println("FIRE BREATH OF " + degree + "oC");
    }

    @Override
    public String toString() {
        return "*" + name + ": " + age + " YEARS OLD ";
    }
    
}
